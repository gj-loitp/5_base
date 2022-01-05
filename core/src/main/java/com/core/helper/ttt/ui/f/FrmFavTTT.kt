package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment

@LogTag("FrmFavTTT")
class FrmFavTTT : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_ttt_comic_fav
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    fun setupViews() {
    }
}
