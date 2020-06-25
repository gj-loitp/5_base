package com.core.utilities

import android.text.Html
import android.text.Spanned
import android.text.TextUtils

/**
 * Created by Loitp on 5/2/2017.
 */

class LStringUtil {
    companion object {
        @Suppress("DEPRECATION")
        fun convertHTMLTextToPlainText(htmlText: String): String {
            val spanned: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlText)
            }
            val chars = CharArray(spanned.length)
            TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
            return String(chars)
        }
    }
}
