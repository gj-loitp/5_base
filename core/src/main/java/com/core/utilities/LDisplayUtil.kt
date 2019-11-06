package com.core.utilities


import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class LDisplayUtil private constructor() {
    init {
        throw Error("Do not need instantiate!")
    }

    companion object {

        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
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

        fun toggleKeyboard(context: Context) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

}