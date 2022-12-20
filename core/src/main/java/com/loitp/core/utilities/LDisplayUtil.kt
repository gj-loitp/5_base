package com.loitp.core.utilities

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LDisplayUtil {
    companion object {
        fun dip2px(dpValue: Float): Int {
            val scale = LAppResource.application.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun px2dip(pxValue: Float): Int {
            val scale = LAppResource.application.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun px2sp(pxValue: Float): Int {
            val fontScale = LAppResource.application.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        fun sp2px(spValue: Float): Int {
            val fontScale = LAppResource.application.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        fun getDialogW(activity: Activity): Int {
            val dm = activity.resources.displayMetrics
            return dm.widthPixels - 100
        }

        fun getScreenW(activity: Activity): Int {
            val dm = activity.resources.displayMetrics
            return dm.widthPixels
        }

        fun getScreenH(activity: Activity): Int {
            val dm = activity.resources.displayMetrics
            return dm.heightPixels
        }

        fun toggleKeyboard() {
            val imm =
                LAppResource.application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.toggleSoftInput(
                    InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }
}
