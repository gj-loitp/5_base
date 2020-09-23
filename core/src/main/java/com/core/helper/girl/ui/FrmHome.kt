package com.core.helper.girl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.girl.viewmodel.TestViewModel

@LogTag("loitppFrmHome")
class FrmHome : BaseFragment() {

    private var testViewModel: TestViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_girl_home, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()
        testViewModel?.getUserTestListByPage(0, true)
    }

    private fun setupViewModels() {
        testViewModel = getViewModel(TestViewModel::class.java)
        testViewModel?.let { tvm ->
            tvm.userActionLiveData.observe(viewLifecycleOwner, Observer { actionData ->
                logD("userActionLiveData " + actionData.data)
            })
        }
    }
}
