package com.loitpcore.core.helper.ttt.ui.f

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.loitpcore.R
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseBottomSheetFragment
import com.loitpcore.core.utilities.LDialogUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.l_bottom_sheet_ttt_setting_fragment.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("BottomSheetSettingTTTFragment")
class BottomSheetSettingTTTFragment : BaseBottomSheetFragment(
    layoutId = R.layout.l_bottom_sheet_ttt_setting_fragment,
    height = WindowManager.LayoutParams.WRAP_CONTENT,
    isDraggable = true,
    firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
) {

    private var dialog: AlertDialog? = null
    var onSwitchTheme: ((Unit) -> Unit)? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
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

            dialog = LDialogUtil.showDialog2(
                context = c,
                title = getString(R.string.warning_vn),
                msg = getString(R.string.app_will_be_restarted_vn),
                button1 = getString(R.string.cancel),
                button2 = getString(R.string.ok),
                onClickButton1 = {
                    sw?.isChecked = LUIUtil.isDarkTheme()
                },
                onClickButton2 = {
                    if (isChecked) {
                        LUIUtil.setDarkTheme(isDarkTheme = true)
                    } else {
                        LUIUtil.setDarkTheme(isDarkTheme = false)
                    }
                    dialog?.dismiss()
                    this@BottomSheetSettingTTTFragment.dismiss()
                    onSwitchTheme?.invoke(Unit)
                }
            )
            dialog?.setOnCancelListener {
                sw?.isChecked = LUIUtil.isDarkTheme()
            }
        }
    }
}
