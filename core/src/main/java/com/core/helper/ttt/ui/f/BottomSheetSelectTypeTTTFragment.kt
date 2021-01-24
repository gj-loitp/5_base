package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.R
import com.annotation.LogTag
import com.core.base.BaseBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.l_bottom_sheet_ttt_select_type_fragment.*

@LogTag("BottomSheetSelectTypeTTTFragment")
class BottomSheetSelectTypeTTTFragment : BaseBottomSheetFragment(
        layoutId = R.layout.l_bottom_sheet_ttt_select_type_fragment,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btSelectType1.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        activity?.let {
            when (v) {
                btSelectType1 -> {

                }
            }
        }
    }
}
