package com.loitp.core.utilities

import android.text.Html
import android.text.Spanned
import android.text.TextUtils

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LStringUtil {
    companion object {
        @Suppress("DEPRECATION")
        fun convertHTMLTextToPlainText(htmlText: String): String {
            val spanned: Spanned =
                Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
            val chars = CharArray(spanned.length)
            TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
            return String(chars)
        }
    }
}
