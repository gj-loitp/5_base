package com.loitpcore.views.textView.selectable

import android.text.Spannable
import android.text.Spanned
import android.text.style.CharacterStyle
import com.loitpcore.BuildConfig
import kotlin.math.max
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class CustomInfo {

    override fun toString(): String {
        return "CustomInfo{" +
                "mSpan=" + span +
                ", mStart=" + mStart +
                ", mEnd=" + mEnd +
                ", mSpannable=" + spannable +
                '}'
    }

    private var span: Any? = null
    private var mStart = 0
    private var mEnd = 0
    private var spannable: Spannable? = null

    constructor() {
        clear()
    }

    constructor(text: CharSequence?, span: Any?, start: Int, end: Int) {
        set(text, span, start, end)
    }

    @JvmOverloads
    fun select(text: Spannable? = spannable) {
        text?.let {
            it.removeSpan(span)
            it.setSpan(
                span,
                min(mStart, mEnd),
                max(mStart, mEnd),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    @JvmOverloads
    fun remove(text: Spannable? = spannable) {
        text?.removeSpan(span)
    }

    fun clear() {
        span = null
        spannable = null
        mStart = 0
        mEnd = 0
    }

    operator fun set(span: Any?, start: Int, end: Int) {
        this.span = span
        mStart = start
        mEnd = end
    }

    operator fun set(text: CharSequence?, span: Any?, start: Int, end: Int) {
        if (text is Spannable) {
            spannable = text
        }
        set(span = span, start = start, end = end)
    }

    val selectedText: String
        get() {
            var text = ""
            spannable?.let {
                val start = min(a = mStart, b = mEnd)
                val end = max(a = mStart, b = mEnd)
                if (start >= 0 && end <= it.length) {
                    text = it.subSequence(startIndex = start, endIndex = end).toString()
                }
            }
            return text
        }

    @Suppress("unused")
    fun setSpan(span: CharacterStyle?) {
        this.span = span
    }

    var start: Int
        get() = mStart
        set(start) {
            if (BuildConfig.DEBUG && start < 0) {
                error("Assertion failed")
            }
            mStart = start
        }
    var end: Int
        get() = mEnd
        set(end) {
            if (BuildConfig.DEBUG && end < 0) {
                error("Assertion failed")
            }
            mEnd = end
        }

    @Suppress("unused")
    fun offsetInSelection(offset: Int): Boolean {
        return offset in mStart..mEnd || offset in mEnd..mStart
    }
}
