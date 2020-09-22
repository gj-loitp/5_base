package com.core.helper.girl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment

@LogTag("FrmGirl")
class FrmGirl : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflater.inflate(R.layout.l_frm_girl, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}
