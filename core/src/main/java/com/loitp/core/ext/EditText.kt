package com.loitp.core.ext

import android.widget.EditText

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
