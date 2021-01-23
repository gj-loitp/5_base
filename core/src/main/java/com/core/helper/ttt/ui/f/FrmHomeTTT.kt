package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.mup.comic.model.Comic

@LogTag("FrmHomeTTT")
class FrmHomeTTT : BaseFragment() {
//    private var comicViewModel: ComicViewModel? = null
//    private var concatAdapter: ConcatAdapter? = null
//    private var comicHeaderAdapter: ComicHeaderAdapter? = null
//    private var comicAdapter: ComicAdapter? = null
//    private var comicProgressAdapter: ComicProgressAdapter? = null
//    private var currentPageIndex = 0
//    private var totalPage = Int.MAX_VALUE
//    private var currentKeyword: String = ""

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_comic_home_ttt
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()

//        comicViewModel?.postCategorySelected(Category.getCategoryAll())
    }

    private fun getListComic(isSwipeToRefresh: Boolean) {
//        logD("getPage isSwipeToRefresh $isSwipeToRefresh, currentPageIndex $currentPageIndex, currentKeyword $currentKeyword")
//
//        val categorySelected = comicViewModel?.categorySelected?.value
//        if (categorySelected == null || Category.isCategoryAll(categorySelected)) {
//            logD(">>>getListComic !by category")
//            comicViewModel?.getListComic(
//                    pageIndex = currentPageIndex,
//                    keyword = currentKeyword,
//                    isSwipeToRefresh = isSwipeToRefresh)
//        } else {
//            logD(">>>getListComic by category")
//            comicViewModel?.getListComicByCategory(
//                    categoryId = categorySelected.id,
//                    pageIndex = currentPageIndex,
//                    keyword = currentKeyword,
//                    isSwipeToRefresh = isSwipeToRefresh)
//        }
    }

    private fun setupViews() {
//        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
//        LUIUtil.setProgressViewOffset(swipeRefreshLayout = swipeRefreshLayout, topMargin = LScreenUtil.screenHeight / 6)
//        swipeRefreshLayout.setOnRefreshListener {
//            swipeRefreshLayout.isRefreshing = false
//            currentPageIndex = 0
//            getListComic(isSwipeToRefresh = true)
//        }
//        comicHeaderAdapter = ComicHeaderAdapter()
//        comicAdapter = ComicAdapter()
//        comicProgressAdapter = ComicProgressAdapter()
//
//        comicHeaderAdapter?.let { comicHeaderA ->
//            comicHeaderA.onClickRoot = { comic ->
//                handleClickComicHeader(comic = comic)
//            }
//
//            comicAdapter?.let { comicA ->
//                comicA.onClickRoot = { comic ->
//                    handleClickComic(comic = comic)
//                }
//
//                comicProgressAdapter?.let { comicProgressA ->
//                    val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(comicHeaderA, comicA, comicProgressA)
//                    concatAdapter = ConcatAdapter(listOfAdapters)
//                }
//            }
//        }
//        val spanCount = if (LDeviceUtil.isTablet()) {
//            3
//        } else {
//            2
//        }
//        val gridLayoutManager = GridLayoutManager(context, spanCount)
//        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return when (position) {
//                    0 -> {
//                        spanCount
//                    }
//                    (comicAdapter?.itemCount?.plus(1) ?: 0) -> {
//                        spanCount
//                    }
//                    else -> {
//                        1
//                    }
//                }
//            }
//        }
//        recyclerView.layoutManager = gridLayoutManager
//        recyclerView.adapter = concatAdapter
//
//        LUIUtil.setScrollChange(
//                recyclerView = recyclerView,
//                onBottom = {
//                    val isExistComicProgressAdapter = concatAdapter?.adapters?.firstOrNull { adapter ->
//                        adapter.javaClass.simpleName == ComicProgressAdapter::class.java.simpleName
//                    }
//                    logD("onBottom isExistComicProgressAdapter $isExistComicProgressAdapter")
//                    if (isExistComicProgressAdapter == null) {
//                        logD("onBottom $currentPageIndex/$totalPage")
//                        if (currentPageIndex < totalPage) {
//                            currentPageIndex++
//                            comicProgressAdapter?.let { gpa ->
//                                concatAdapter?.let { ma ->
//                                    ma.addAdapter(gpa)
//                                    recyclerView.smoothScrollToPosition(ma.itemCount - 1)
//                                }
//                            }
//                            getListComic(isSwipeToRefresh = false)
//                        }
//                    }
//                },
//                onScrolled = { isScrollDown ->
//                    if (isScrollDown) {
//                        fabCategory.shrink()
//                    } else {
//                        fabCategory.extend()
//                    }
//                }
//        )
//
//        ivSearch.setSafeOnClickListener {
//            handleSearch(isAutoSearch = false)
//        }
//        LUIUtil.addTextChangedListener(editText = etSearch, delayInMls = 2000, afterTextChanged = {
//            handleSearch(isAutoSearch = true)
//        })
//        LUIUtil.setImeiActionSearch(editText = etSearch, actionSearch = {
//            ivSearch.performClick()
//        })
//        fabCategory.setOnClickListener {
//            handleClickCategory()
//        }
    }

    private fun setupViewModels() {
//        comicViewModel = getViewModel(ComicViewModel::class.java)
//        comicViewModel?.let { vm ->
//            vm.listComicActionLiveData.observe(viewLifecycleOwner, { actionData ->
//                val isDoing = actionData.isDoing
//                val isSuccess = actionData.isSuccess
//                val isSwipeToRefresh = actionData.isSwipeToRefresh
//
////                if (currentPageIndex == 0) {
////                    if (isDoing == true) {
////                        indicatorView.smoothToShow()
////                    } else {
////                        indicatorView.smoothToHide()
////                    }
////                }
//
//                logD("listComicActionLiveData observe isDoing $isDoing, isSuccess $isSuccess")
//
//                if (isDoing == false && isSuccess == true) {
//                    val listComic = actionData.data
//                    if (listComic.isNullOrEmpty()) {
//                        if (currentPageIndex == 0) {
//                            tvNoData.visibility = View.VISIBLE
//                            recyclerView.visibility = View.GONE
//                        }
//                        comicProgressAdapter?.let {
//                            concatAdapter?.removeAdapter(it)
//                        }
//                    } else {
//                        totalPage = actionData.totalPages ?: 0
//
//                        tvNoData.visibility = View.GONE
//                        recyclerView.visibility = View.VISIBLE
//                        comicProgressAdapter?.let {
//                            concatAdapter?.removeAdapter(it)
//                        }
//                        comicHeaderAdapter?.setData(comic = listComic.random())
//                        comicAdapter?.setData(listComic = listComic, isSwipeToRefresh = isSwipeToRefresh)
//                    }
//                }
//            })
//            vm.categorySelected.observe(viewLifecycleOwner, { category ->
//                logD("<<<categorySelected observe " + BaseApplication.gson.toJson(category))
//                etSearch.hint = getString(R.string.search_in_category) + " " + category.title
//                fabCategory.text = category.title
//                handleSearch(isAutoSearch = false)
//            })
//        }
    }

    private fun handleSearch(isAutoSearch: Boolean) {
//        if (isAutoSearch) {
//            //do nothing
//        } else {
//            KeyboardUtils.hideSoftInput(context, etSearch)
//        }
//        currentKeyword = etSearch.text.toString().trim()
//        getListComic(isSwipeToRefresh = true)
    }

    private fun handleClickComicHeader(comic: Comic) {
        goToComicChapScreen(comic = comic)
    }

    private fun handleClickComic(comic: Comic) {
        goToComicChapScreen(comic = comic)
    }

    private fun goToComicChapScreen(comic: Comic) {
//        val intent = Intent(context, ComicChapActivity::class.java)
//        intent.putExtra(ComicChapActivity.KEY_COMIC, comic)
//        startActivity(intent)
//        LActivityUtil.tranIn(context)
    }

    private fun handleClickCategory() {
//        val bottomSheetCategoryFragment = BottomSheetCategoryFragment()
//        bottomSheetCategoryFragment.show(childFragmentManager, bottomSheetCategoryFragment.tag)
    }

}
