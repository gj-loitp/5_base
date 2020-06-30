package com.core.utilities

import android.app.Activity
import android.view.inputmethod.InputMethodManager

/**
 * Created by Loitp on 17/7/2019.
 */

class LKeyBoardUtil {
    companion object {
        fun show(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY) // show
        }

        fun hide(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0) // hide
        }

        fun toggle(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                hide(activity = activity)
            } else {
                show(activity = activity)
            }
        }
    }
}
