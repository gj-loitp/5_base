package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.ttt.viewmodel.TTTViewModel

@LogTag("FrmHomeTTT")
class FrmHomeTTT : BaseFragment() {
    private var tTTViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_comic_home_ttt
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()

//        tTTViewModel?.postCategorySelected(Category.getCategoryAll())
    }

    private fun setupViews() {

    }

    private fun setupViewModels() {

    }


}
