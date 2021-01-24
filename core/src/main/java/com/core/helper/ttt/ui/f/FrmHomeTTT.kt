package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.ttt.viewmodel.TTTViewModel
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_frm_ttt_comic_home.*

@LogTag("FrmHomeTTT")
class FrmHomeTTT : BaseFragment() {
    private var tTTViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_ttt_comic_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModels()

    }

    private fun setupViews() {
        btSelectType.setSafeOnClickListener {
            val bottomSheetSelectTypeTTTFragment = BottomSheetSelectTypeTTTFragment()
            bottomSheetSelectTypeTTTFragment.show(childFragmentManager, bottomSheetSelectTypeTTTFragment.tag)
        }
    }

    private fun setupViewModels() {

    }


}
