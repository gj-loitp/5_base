package com.loitp.core.ext

import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import java.util.*

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun getRandomString(maxLength: Int): String {
    val generator = Random()
    val randomStringBuilder = StringBuilder()
    val randomLength = generator.nextInt(maxLength)
    var tempChar: Char
    for (i in 0 until randomLength) {
        tempChar = (generator.nextInt(96) + 32).toChar()
        randomStringBuilder.append(tempChar)
    }
    return randomStringBuilder.toString()
}

fun String.convertHTMLTextToPlainText(): String {
    val spanned: Spanned =
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    val chars = CharArray(spanned.length)
    TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
    return String(chars)
}
