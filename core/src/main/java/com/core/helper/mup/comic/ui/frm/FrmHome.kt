package com.core.helper.mup.comic.ui.frm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFragment
import com.core.helper.mup.comic.adapter.ComicAdapter
import com.core.helper.mup.comic.adapter.ComicHeaderAdapter
import com.core.helper.mup.comic.adapter.ComicProgressAdapter
import com.core.helper.mup.comic.model.Category
import com.core.helper.mup.comic.model.Comic
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.core.utilities.LDeviceUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.interfaces.CallbackRecyclerView
import com.utils.util.KeyboardUtils
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_frm_comic_home.*

@LogTag("loitppFrmHome")
class FrmHome : BaseFragment() {
    private var comicViewModel: ComicViewModel? = null
    private var concatAdapter: ConcatAdapter? = null
    private var comicHeaderAdapter: ComicHeaderAdapter? = null
    private var comicAdapter: ComicAdapter? = null
    private var comicProgressAdapter: ComicProgressAdapter? = null
    private var currentPageIndex = 0
    private var totalPage = Int.MAX_VALUE
    private var currentKeyword: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_comic_home, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()

        comicViewModel?.postCategorySelected(Category.getCategoryAll())
        getListComic(isSwipeToRefresh = false)
    }

    private fun getListComic(isSwipeToRefresh: Boolean) {
        //TODO get comic by category
        logD("getPage isSwipeToRefresh $isSwipeToRefresh, currentPageIndex $currentPageIndex")
        comicViewModel?.getListComic(pageIndex = currentPageIndex, keyword = currentKeyword, isSwipeToRefresh = isSwipeToRefresh)
    }

    private fun setupViews() {
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
        LUIUtil.setProgressViewOffset(swipeRefreshLayout = swipeRefreshLayout, topMargin = LScreenUtil.screenHeight / 6)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            currentPageIndex = 0
            getListComic(isSwipeToRefresh = true)
        }
        comicHeaderAdapter = ComicHeaderAdapter()
        comicAdapter = ComicAdapter()
        comicProgressAdapter = ComicProgressAdapter()

        comicHeaderAdapter?.let { comicHeaderA ->
            comicHeaderA.onClickRoot = { comic ->
                handleClickComicHeader(comic = comic)
            }

            comicAdapter?.let { comicA ->
                comicA.onClickRoot = { comic ->
                    handleClickComic(comic = comic)
                }

                comicProgressAdapter?.let { comicProgressA ->
                    val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(comicHeaderA, comicA, comicProgressA)
                    concatAdapter = ConcatAdapter(listOfAdapters)
                }
            }
        }
        val spanCount = if (LDeviceUtil.isTablet()) {
            3
        } else {
            2
        }
        val gridLayoutManager = GridLayoutManager(context, spanCount)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> {
                        spanCount
                    }
                    (comicAdapter?.itemCount?.plus(1) ?: 0) -> {
                        spanCount
                    }
                    else -> {
                        1
                    }
                }
            }
        }
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = concatAdapter

        LUIUtil.setScrollChange(
                recyclerView = recyclerView,
                callbackRecyclerView = object : CallbackRecyclerView {
                    override fun onTop() {
                    }

                    override fun onBottom() {
                        val isExistComicProgressAdapter = concatAdapter?.adapters?.firstOrNull { adapter ->
                            adapter.javaClass.simpleName == ComicProgressAdapter::class.java.simpleName
                        }
//                        logD("onBottom isExistComicProgressAdapter $isExistComicProgressAdapter")
                        if (isExistComicProgressAdapter == null) {
                            logD("onBottom $currentPageIndex/$totalPage")
                            if (currentPageIndex < totalPage) {
                                currentPageIndex++
                                comicProgressAdapter?.let { gpa ->
                                    concatAdapter?.let { ma ->
                                        ma.addAdapter(gpa)
                                        recyclerView.smoothScrollToPosition(ma.itemCount - 1)
                                    }
                                }
                                getListComic(isSwipeToRefresh = false)
                            }
                        }
                    }

                    override fun onScrolled(isScrollDown: Boolean) {
                        if (isScrollDown) {
                            fabCategory.shrink()
                        } else {
                            fabCategory.extend()
                        }
                    }
                })

        ivSearch.setSafeOnClickListener {
            handleSearch(isAutoSearch = false)
        }
        LUIUtil.addTextChangedListener(editText = etSearch, delayInMls = 2000, afterTextChanged = {
            handleSearch(isAutoSearch = true)
        })
        LUIUtil.setImeiActionSearch(editText = etSearch, actionSearch = Runnable {
            ivSearch.performClick()
        })
        fabCategory.setOnClickListener {
            handleClickCategory()
        }
    }

    private fun setupViewModels() {
        comicViewModel = getViewModel(ComicViewModel::class.java)
        comicViewModel?.let { vm ->
            vm.listComicActionLiveData.observe(viewLifecycleOwner, Observer { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess
                val isSwipeToRefresh = actionData.isSwipeToRefresh

//                if (currentPageIndex == 0) {
//                    if (isDoing == true) {
//                        indicatorView.smoothToShow()
//                    } else {
//                        indicatorView.smoothToHide()
//                    }
//                }

                if (isDoing == false && isSuccess == true) {
                    val listComic = actionData.data
                    if (listComic.isNullOrEmpty()) {
                        if (currentPageIndex == 0) {
                            tvNoData.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                        comicProgressAdapter?.let {
                            concatAdapter?.removeAdapter(it)
                        }
                    } else {
                        totalPage = actionData.totalPages ?: 0

                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        comicProgressAdapter?.let {
                            concatAdapter?.removeAdapter(it)
                        }
                        comicHeaderAdapter?.setData(comic = listComic.random())
                        comicAdapter?.setData(listComic = listComic, isSwipeToRefresh = isSwipeToRefresh)
                    }
                }
            })
            vm.categorySelected.observe(viewLifecycleOwner, Observer { category ->
                logD("<<<categorySelected observe " + BaseApplication.gson.toJson(category))
                fabCategory.text = category.title
            })
        }
    }

    private fun handleSearch(isAutoSearch: Boolean) {
        if (isAutoSearch) {
            //do nothing
        } else {
            KeyboardUtils.hideSoftInput(context, etSearch)
        }
        etSearch.apply {
            currentKeyword = this.text.toString().trim()
        }
        getListComic(isSwipeToRefresh = true)
    }

    private fun handleClickComicHeader(comic: Comic) {
        //TODO
    }

    private fun handleClickComic(comic: Comic) {
        //TODO
    }

    private fun handleClickCategory() {
        val bottomSheetCategoryFragment = BottomSheetCategoryFragment()
        bottomSheetCategoryFragment.show(childFragmentManager, bottomSheetCategoryFragment.tag)
    }
}
