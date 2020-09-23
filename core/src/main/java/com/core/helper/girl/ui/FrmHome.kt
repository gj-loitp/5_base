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
import com.core.helper.girl.adapter.GirlHeaderAdapter
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_girl_home, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()
        girlViewModel?.getPage(pageIndex = 0, keyWord = null)
    }

    private fun setupViews() {
//        LUIUtil.setTextShadow(textView = tvToday, color = Color.WHITE)
//        LUIUtil.setTextShadow(textView = tvHottestShot, color = Color.WHITE)
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            //TODO
        }
        girlHeaderAdapter = GirlHeaderAdapter()
        girlHeaderAdapter?.let { gha ->
            val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(gha)
            mergeAdapter = MergeAdapter(listOfAdapters)
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
                        logD("onBottom")
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

                if (isDoing == false) {
                    val listGirlPage = actionData.data
                    logD("listGirlPage " + Gson().toJson(listGirlPage))
                    if (listGirlPage.isNullOrEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        girlHeaderAdapter?.setData(girlPage = listGirlPage.random())
                    }
                }
            })
        }
    }
}
