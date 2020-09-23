package com.core.helper.girl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.girl.viewmodel.GirlViewModel
import com.core.utilities.LUIUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.l_frm_girl_home.*

@LogTag("loitppFrmHome")
class FrmHome : BaseFragment() {

    private var girlViewModel: GirlViewModel? = null

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
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            //TODO
        }
    }

    private fun setupViewModels() {
        girlViewModel = getViewModel(GirlViewModel::class.java)
        girlViewModel?.let { tvm ->
            tvm.userActionLiveData.observe(viewLifecycleOwner, Observer { actionData ->
                logD("userActionLiveData $actionData")
                val isDoing = actionData.isDoing
                swipeRefreshLayout.isRefreshing = isDoing == true

                val listGirlPage = actionData.data
                logD("listGirlPage " + Gson().toJson(listGirlPage))
            })
        }
    }
}
