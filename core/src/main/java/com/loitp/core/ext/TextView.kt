package com.loitp.core.ext

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.*
import android.text.Html
import android.text.TextUtils
import android.util.TypedValue
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.google.gson.GsonBuilder

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
        ?: if (context.isDarkTheme()) {
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

fun TextView.setTextBold() {
    this.setTypeface(null, Typeface.BOLD)
}

fun TextView.printBeautyJson(
    o: Any,
) {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson(o)
    this.text = json
}

@SuppressLint("SetTextI18n")
fun TextView.printBeautyJson(
    o: Any?,
    tag: String
) {
    o?.let {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val json = gson.toJson(it)
        this.text = "$tag :\n$json"
    }
}

fun TextView?.setTextSizePx(
    size: Float
) {
    if (size < 0 || this == null) {
        return
    }
    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
}

fun TextView.setDrawableTintColor(
    color: Int
) {
    for (drawable in this.compoundDrawables) {
        if (drawable != null) {
            drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}

@Suppress("unused")
fun TextView.setTypeface(
    pathFontAsset: String
) {
    val type = Typeface.createFromAsset(this.context.assets, pathFontAsset)
    this.typeface = type
}

fun TextView.setTextUnderline() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}
