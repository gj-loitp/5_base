package com.core.helper.mup.comic.ui.frm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import kotlinx.android.synthetic.main.l_frm_comic_favourite.*

@LogTag("FrmFavourite")
class FrmFavourite : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_comic_favourite, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        //TODO loitpp iplm
        tvNoData.visibility = View.VISIBLE
        tvNoData.text = getString(R.string.coming_soon)
        indicatorView.smoothToHide()
    }

    private fun setupViewModels() {

    }

}
