package com.loitp.core.ext

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import com.loitp.itf.OnKeyboardVisibilityListener

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun Context.showKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY) // show
}

fun Context.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0) // hide
}

fun Context.toggle() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) {
        this.hideKeyboard()
    } else {
        this.showKeyboard()
    }
}

fun Activity.setKeyboardVisibilityListener(onKeyboardVisibilityListener: OnKeyboardVisibilityListener) {
    val parentView: View = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    parentView.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        private var alreadyOpen = false
        private val defaultKeyboardHeightDP = 100
        private val EstimatedKeyboardDP = defaultKeyboardHeightDP + 48
        private val rect: Rect = Rect()
        override fun onGlobalLayout() {
            val estimatedKeyboardHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                EstimatedKeyboardDP.toFloat(),
                parentView.resources.displayMetrics
            ).toInt()
            parentView.getWindowVisibleDisplayFrame(rect)
            val heightDiff: Int = parentView.rootView.height - (rect.bottom - rect.top)
            val isShown = heightDiff >= estimatedKeyboardHeight
            if (isShown == alreadyOpen) {
                return
            }
            alreadyOpen = isShown
            onKeyboardVisibilityListener.onVisibilityChanged(isShown)
        }
    })
}

