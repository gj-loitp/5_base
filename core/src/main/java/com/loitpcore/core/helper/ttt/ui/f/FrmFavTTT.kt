package com.loitpcore.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import com.loitpcore.R
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment

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
