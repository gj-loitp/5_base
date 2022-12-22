package com.loitp.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("FrmFavTTT")
class FrmFavTTT : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_f_ttt_comic_fav
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    fun setupViews() {
    }
}
