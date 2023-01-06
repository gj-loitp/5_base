package com.loitp.core.ext

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Html
import android.text.TextUtils
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.loitp.core.utilities.LUIUtil

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

fun TextView?.setMarquee(
    text: String?
) {
    this?.let { t ->
        t.text = text
        this.setMarquee()
    }
}

fun TextView?.setMarquee() {
    this?.let {
        it.isSelected = true
        it.ellipsize = TextUtils.TruncateAt.MARQUEE
        it.isSingleLine = true
        it.marqueeRepeatLimit = -1 // no limit loop
    }
}

fun TextView?.setTextFromHTML(
    bodyData: String
) {
    this?.let {
        it.text = Html.fromHtml(bodyData, Html.FROM_HTML_MODE_LEGACY)
    }
}

fun TextView.setTextShadow(
    color: Int?
) {
    val mColor: Int = color
        ?: if (LUIUtil.isDarkTheme()) {
            Color.BLACK
        } else {
            Color.WHITE
        }
    this.setShadowLayer(
        /* radius = */ 1f, // radius
        /* dx = */ 1f, // dx
        /* dy = */ 1f, // dy
        /* color = */ mColor // shadow color
    )
}
