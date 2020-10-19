package com.core.helper.mup.comic.ui.frm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.mup.comic.adapter.ComicHeaderAdapter
import com.core.helper.mup.comic.adapter.ComicProgressAdapter
import com.core.helper.mup.comic.viewmodel.ComicViewModel
import com.core.utilities.LScreenUtil
import com.core.utilities.LUIUtil
import com.interfaces.CallbackRecyclerView
import com.utils.util.KeyboardUtils
import kotlinx.android.synthetic.main.l_frm_girl_home.*
import kotlinx.android.synthetic.main.l_frm_girl_home.recyclerView

@LogTag("FrmHome")
class FrmHome : BaseFragment() {
    private var comicViewModel: ComicViewModel? = null
    private var mergeAdapter: ConcatAdapter? = null
    private var comicHeaderAdapter: ComicHeaderAdapter? = null
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
    }

    private fun getPage(isSwipeToRefresh: Boolean) {
        logD("getPage isSwipeToRefresh $isSwipeToRefresh")
        //girlViewModel?.getPage(pageIndex = currentPageIndex, keyWord = currentKeyword, isSwipeToRefresh = isSwipeToRefresh)
    }

    private fun setupViews() {
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
        LUIUtil.setProgressViewOffset(swipeRefreshLayout = swipeRefreshLayout, topMargin = LScreenUtil.screenHeight / 6)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            currentPageIndex = 0
            getPage(isSwipeToRefresh = true)
        }
        comicHeaderAdapter = ComicHeaderAdapter()
        comicProgressAdapter = ComicProgressAdapter()

        comicHeaderAdapter?.let { comicHeader ->
            comicProgressAdapter?.let { comicProgress ->
                val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(comicHeader, comicProgress)
                mergeAdapter = ConcatAdapter(listOfAdapters)
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mergeAdapter

        LUIUtil.setScrollChange(
                recyclerView = recyclerView,
                callbackRecyclerView = object : CallbackRecyclerView {
                    override fun onTop() {
                    }

                    override fun onBottom() {
                        logD("onBottom $currentPageIndex/$totalPage")
                        if (currentPageIndex < totalPage) {
                            currentPageIndex++
                            comicProgressAdapter?.let { gpa ->
                                mergeAdapter?.let { ma ->
                                    ma.addAdapter(gpa)
                                    recyclerView.smoothScrollToPosition(ma.itemCount - 1)
                                }
                            }
                            getPage(isSwipeToRefresh = false)
                        }
                    }
                })

        ivSearch.setOnClickListener {
            handleSearch(isAutoSearch = false)
        }
        LUIUtil.addTextChangedListener(editText = etSearch, delayInMls = 2000, afterTextChanged = {
            handleSearch(isAutoSearch = true)
        })
        LUIUtil.setImeiActionSearch(editText = etSearch, actionSearch = Runnable {
            ivSearch.performClick()
        })
    }

    private fun setupViewModels() {
        comicViewModel = getViewModel(ComicViewModel::class.java)
        comicViewModel?.let { vm ->
//            vm.pageActionLiveData.observe(viewLifecycleOwner, Observer { actionData ->
//                val isDoing = actionData.isDoing
//
//                if (isDoing == true) {
//                    indicatorView.smoothToShow()
//                } else {
//                    indicatorView.smoothToHide()
//                }
//
//                if (isDoing == false && actionData.isSuccess == true) {
//                    val listGirlPage = actionData.data
//                    if (listGirlPage.isNullOrEmpty()) {
//                        tvNoData.visibility = View.VISIBLE
//                        recyclerView.visibility = View.GONE
//                    } else {
//                        totalPage = actionData.totalPages ?: 0
//
//                        tvNoData.visibility = View.GONE
//                        recyclerView.visibility = View.VISIBLE
//                        comicProgressAdapter?.let {
//                            mergeAdapter?.removeAdapter(it)
//                        }
//                        comicHeaderAdapter?.setData(girlPage = listGirlPage.random())
//
//                    }
//                }
//            })
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
        getPage(isSwipeToRefresh = true)
    }
}
