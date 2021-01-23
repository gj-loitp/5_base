package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment

@LogTag("FrmHomeTTT")
class FrmHomeTTT : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_comic_home_ttt
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    fun setupViews() {

    }


}
