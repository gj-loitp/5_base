package com.core.helper.mup.comic.ui.frm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.R
import com.annotation.LogTag
import com.core.helper.mup.comic.ui.activity.ComicActivity
import com.core.utilities.LUIUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.views.bottomsheet.LBottomSheetFragment
import kotlinx.android.synthetic.main.l_bottom_sheet_setting_fragment.*

@LogTag("BottomSheetSettingFragment")
class BottomSheetSettingFragment : LBottomSheetFragment(
        layoutId = R.layout.l_bottom_sheet_setting_fragment,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val isDarkTheme = LUIUtil.isDarkTheme()
        sw.isChecked = isDarkTheme

        sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                LUIUtil.setDarkTheme(isDarkTheme = true)
            } else {
                LUIUtil.setDarkTheme(isDarkTheme = false)
            }
            activity?.let { a ->
                a.finish()
                startActivity(Intent(a, ComicActivity::class.java))
                a.overridePendingTransition(0, 0)
            }
        }
    }
}
