package com.loitp.core.ext

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun EditText?.setLastCursorEditText() {
    this?.apply {
        if (text.toString().isNotEmpty()) {
            setSelection(text.length)
        }
    }
}

// it.imeOptions = EditorInfo.IME_ACTION_SEARCH
fun EditText.setImeiActionEditText(
    imeOptions: Int,
    runnable: Runnable? = null
) {
    this.imeOptions = imeOptions
    this.setOnEditorActionListener(
        TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == imeOptions) {
                runnable?.run()
                return@OnEditorActionListener true
            }
            false
        }
    )
}

fun EditText.setImeiActionSearch(
    actionSearch: Runnable? = null
) {
    imeOptions = EditorInfo.IME_ACTION_SEARCH
    setOnEditorActionListener(
        TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                actionSearch?.run()
                return@OnEditorActionListener true
            }
            false
        }
    )
}