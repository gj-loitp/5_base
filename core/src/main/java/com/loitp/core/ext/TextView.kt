package com.loitp.core.ext

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.core.widget.TextViewCompat

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//set mau drawable cua text view
fun TextView.setDrawableTint(
    color: Int
) {
    TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(color))
}