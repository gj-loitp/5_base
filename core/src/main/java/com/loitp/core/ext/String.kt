package com.loitp.core.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import com.loitp.core.utils.Utils
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
    val spanned: Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    val chars = CharArray(spanned.length)
    TextUtils.getChars(spanned, 0, spanned.length, chars, 0)
    return String(chars)
}

@Suppress("unused")
fun Context.copyText(text: CharSequence?) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    clipboard?.setPrimaryClip(ClipData.newPlainText("text", text))
}

val text: CharSequence?
    get() {
        val clipboard =
            Utils.getContext()?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = clipboard?.primaryClip
        return if (clip != null && clip.itemCount > 0) {
            clip.getItemAt(0).coerceToText(Utils.getContext())
        } else {
            null
        }
    }

@Suppress("unused")
fun Context.copyUri(
    uri: Uri?
) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    clipboard?.setPrimaryClip(
        ClipData.newUri(
            Utils.getContext()?.contentResolver, "uri", uri
        )
    )
}

val uri: Uri?
    get() {
        val clipboard =
            Utils.getContext()?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = clipboard?.primaryClip
        return if (clip != null && clip.itemCount > 0) {
            clip.getItemAt(0).uri
        } else {
            null
        }
    }

@Suppress("unused")
fun Context.copyIntent(intent: Intent?) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    clipboard?.setPrimaryClip(ClipData.newIntent("intent", intent))
}

val intent: Intent?
    get() {
        val clipboard =
            Utils.getContext()?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = clipboard?.primaryClip
        return if (clip != null && clip.itemCount > 0) {
            clip.getItemAt(0).intent
        } else {
            null
        }
    }
