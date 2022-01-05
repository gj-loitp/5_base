package com.core.helper.mup.comic.ui.frm

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.utilities.LDialogUtil
import kotlinx.android.synthetic.main.l_frm_mup_comic_favourite.*

@LogTag("FrmFavourite")
class FrmFavourite : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_mup_comic_favourite
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        // TODO iplm fav view
        tvNoData.visibility = View.VISIBLE
        tvNoData.text = getString(R.string.coming_soon)
        LDialogUtil.hideProgress(progressBar)
    }

    private fun setupViewModels() {
    }
}
