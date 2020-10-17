package com.core.helper.mup.comic.ui.frm

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.R
import com.annotation.LogTag
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.interfaces.Callback2
import com.views.bottomsheet.LBottomSheetFragment
import kotlinx.android.synthetic.main.l_bottom_sheet_setting_fragment.*

@LogTag("BottomSheetSettingFragment")
class BottomSheetSettingFragment : LBottomSheetFragment(
        layoutId = R.layout.l_bottom_sheet_setting_fragment,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
) {

    private var dialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    override fun onDestroy() {
        dialog?.dismiss()
        super.onDestroy()
    }

    private fun setupViews() {
        val isDarkTheme = LUIUtil.isDarkTheme()
        sw.isChecked = isDarkTheme

        sw.setOnCheckedChangeListener { _, isChecked ->
            handleSwitchDarkTheme(isChecked)
        }
    }

    private fun handleSwitchDarkTheme(isChecked: Boolean) {
        context?.let { c ->
            val isDarkTheme = LUIUtil.isDarkTheme()
            if (isDarkTheme == isChecked) {
                return@let
            }

            dialog = LDialogUtil.showDialog2(context = c,
                    title = getString(R.string.warning_vn),
                    msg = getString(R.string.app_will_be_restarted_vn),
                    button1 = getString(R.string.cancel),
                    button2 = getString(R.string.ok),
                    callback2 = object : Callback2 {
                        override fun onClick1() {
                            sw?.isChecked = LUIUtil.isDarkTheme()
                        }

                        override fun onClick2() {
                            if (isChecked) {
                                LUIUtil.setDarkTheme(isDarkTheme = true)
                            } else {
                                LUIUtil.setDarkTheme(isDarkTheme = false)
                            }
                            dialog?.dismiss()
                            this@BottomSheetSettingFragment.dismiss()
                            activity?.recreate()
                        }
                    })
            dialog?.setOnCancelListener {
                sw?.isChecked = LUIUtil.isDarkTheme()
            }
        }
    }
}
