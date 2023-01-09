package com.loitp.core.ext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import java.util.*

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

fun EditText.addTextChangedDelayListener(
    delayInMls: Long,
    afterTextChanged: (String) -> Unit
) {
    if (delayInMls > 0) {
        this.addTextChangedListener(object : TextWatcher {
            private var timer = Timer()
            override fun afterTextChanged(editable: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            editable?.let { e ->
                                this@addTextChangedDelayListener.post {
                                    afterTextChanged.invoke(e.toString())
                                }
                            }
                        }
                    }, delayInMls
                )
            }

            override fun beforeTextChanged(
                charSequence: CharSequence?, p1: Int, p2: Int, p3: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?, p1: Int, p2: Int, p3: Int
            ) {
            }
        })
    }
}

@Suppress("unused")
fun EditText.showSoftInput() {
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(this, 0)
}
