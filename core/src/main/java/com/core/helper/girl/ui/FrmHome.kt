package com.core.helper.girl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.girl.adapter.GirlAlbumAdapter
import com.core.helper.girl.adapter.GirlHeaderAdapter
import com.core.helper.girl.adapter.GirlProgressAdapter
import com.core.helper.girl.adapter.GirlTitleAdapter
import com.core.helper.girl.viewmodel.GirlViewModel
import com.core.utilities.LUIUtil
import com.google.gson.Gson
import com.interfaces.CallbackRecyclerView
import kotlinx.android.synthetic.main.l_frm_girl_home.*

@LogTag("loitppFrmHome")
class FrmHome : BaseFragment() {

    private var girlViewModel: GirlViewModel? = null
    private var mergeAdapter: MergeAdapter? = null
    private var girlHeaderAdapter: GirlHeaderAdapter? = null
    private var girlAlbumAdapter: GirlAlbumAdapter? = null
    private var girlProgressAdapter: GirlProgressAdapter? = null
    private var currentPageIndex = 0
    private var totalPage = Int.MAX_VALUE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_girl_home, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()
        getPage(isSwipeToRefresh = false)
    }

    private fun getPage(isSwipeToRefresh: Boolean) {
        val keyword = ""//TODO
        girlViewModel?.getPage(pageIndex = currentPageIndex, keyWord = keyword, isSwipeToRefresh = isSwipeToRefresh)
    }

    private fun setupViews() {
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
        LUIUtil.setProgressViewOffset(swipeRefreshLayout = swipeRefreshLayout, topMargin = 120)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            currentPageIndex = 0
            getPage(isSwipeToRefresh = true)
        }
        girlHeaderAdapter = GirlHeaderAdapter()
        val girlTitleAdapterTopUser = GirlTitleAdapter()
        girlTitleAdapterTopUser.setTitle(getString(R.string.top_user))
        val girlTitleAdapterAlbum = GirlTitleAdapter()
        girlTitleAdapterAlbum.setTitle(getString(R.string.album))
        girlAlbumAdapter = GirlAlbumAdapter()
        girlProgressAdapter = GirlProgressAdapter()

        girlAlbumAdapter?.onClickRootListener = { girlPage, position ->
            logD("onClickRootListener girlAlbumAdapter $position -> " + Gson().toJson(girlPage))
            //TODO
        }

        girlHeaderAdapter?.let { gha ->
            girlTitleAdapterTopUser.let { gtatu ->
                girlTitleAdapterAlbum.let { gtaa ->
                    girlAlbumAdapter?.let { gaa ->
                        val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(gha, gtatu, gtaa, gaa)
                        mergeAdapter = MergeAdapter(listOfAdapters)
                    }
                }
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mergeAdapter

        LUIUtil.setScrollChange(
                recyclerView = recyclerView,
                callbackRecyclerView = object : CallbackRecyclerView {
                    override fun onTop() {
                        logD("onTop")
                    }

                    override fun onBottom() {
                        logD("onBottom $currentPageIndex/$totalPage")
                        if (currentPageIndex < totalPage) {
                            currentPageIndex++
                            girlProgressAdapter?.let { gpa ->
                                mergeAdapter?.let { ma ->
                                    ma.addAdapter(gpa)
                                    recyclerView.smoothScrollToPosition(ma.itemCount - 1)
                                }
                            }
                            getPage(isSwipeToRefresh = false)
                        }
                    }
                })
    }

    private fun setupViewModels() {
        girlViewModel = getViewModel(GirlViewModel::class.java)
        girlViewModel?.let { tvm ->
            tvm.userActionLiveData.observe(viewLifecycleOwner, Observer { actionData ->
//                logD("userActionLiveData $actionData")
                val isDoing = actionData.isDoing
                swipeRefreshLayout.isRefreshing = isDoing == true

                if (isDoing == false && actionData.isSuccess == true) {
                    val listGirlPage = actionData.data
//                    logD("listGirlPage " + Gson().toJson(listGirlPage))
                    if (listGirlPage.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        totalPage = actionData.totalPages ?: 0

                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        girlProgressAdapter?.let {
                            mergeAdapter?.removeAdapter(it)
                        }
                        girlHeaderAdapter?.setData(girlPage = listGirlPage.random())
                        girlAlbumAdapter?.setData(listGirlPage = listGirlPage, isSwipeToRefresh = actionData.isSwipeToRefresh
                                ?: false)

                    }
                }
            })
        }
    }
}
