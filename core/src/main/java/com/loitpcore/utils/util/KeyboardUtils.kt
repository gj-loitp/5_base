package com.loitpcore.utils.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.loitpcore.utils.util.Utils.Companion.getContext

class KeyboardUtils {

    companion object {
        fun hideSoftInput(activity: Activity?) {
            activity?.let {
                var view = it.currentFocus
                if (view == null) view = View(it)
                val imm = it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        fun hideSoftInput(context: Context?, view: View) {
            context?.let {
                val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        fun showSoftInput(edit: EditText) {
            edit.isFocusable = true
            edit.isFocusableInTouchMode = true
            edit.requestFocus()
            val imm =
                getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(edit, 0)
        }

        fun toggleSoftInput() {
            val imm =
                getContext()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
}
