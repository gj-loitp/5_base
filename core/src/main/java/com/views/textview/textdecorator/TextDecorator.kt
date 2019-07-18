package com.views.textview.textdecorator

/**
 * Created by www.muathu@gmail.com on 12/20/2017.
 */

import android.content.res.ColorStateList
import android.graphics.BlurMaskFilter
import android.graphics.EmbossMaskFilter
import android.text.Layout
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

class TextDecorator private constructor(private val textView: TextView, private val content: String) {
    private val decoratedContent: SpannableString = SpannableString(content)
    private var flags: Int = 0

    init {
        this.flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    }

    fun setFlags(flags: Int): TextDecorator {
        this.flags = flags

        return this
    }

    fun underline(start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(UnderlineSpan(), start, end, flags)

        return this
    }

    fun underline(vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(UnderlineSpan(), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setTextColor(@ColorRes resColorId: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(ForegroundColorSpan(ContextCompat.getColor(textView.context, resColorId)), start, end,
                flags)

        return this
    }

    fun setTextColor(@ColorRes resColorId: Int, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(ForegroundColorSpan(ContextCompat.getColor(textView.context, resColorId)), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setBackgroundColor(@ColorRes colorResId: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(BackgroundColorSpan(ContextCompat.getColor(textView.context, colorResId)), start, end, 0)

        return this
    }

    fun setBackgroundColor(@ColorRes colorResId: Int, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(BackgroundColorSpan(ContextCompat.getColor(textView.context, colorResId)), index, index + text.length, flags)
            }
        }

        return this
    }

    fun insertBullet(start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(BulletSpan(), start, end, flags)

        return this
    }

    fun insertBullet(gapWidth: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(BulletSpan(gapWidth), start, end, flags)

        return this
    }

    fun insertBullet(gapWidth: Int, @ColorRes colorResId: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(BulletSpan(gapWidth, ContextCompat.getColor(textView.context, colorResId)), start, end,
                flags)

        return this
    }

    fun makeTextClickable(listener: OnTextClickListener, start: Int, end: Int, underlineText: Boolean): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                listener.onClick(view, content.substring(start, end))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = underlineText
            }
        }, start, end, flags)
        textView.movementMethod = LinkMovementMethod.getInstance()

        return this
    }

    fun makeTextClickable(listener: OnTextClickListener, underlineText: Boolean, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)

                decoratedContent.setSpan(object : ClickableSpan() {
                    override fun onClick(view: View) {
                        listener.onClick(view, text)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = underlineText
                    }
                }, index, index + text.length, flags)
            }
        }

        textView.movementMethod = LinkMovementMethod.getInstance()

        return this
    }

    fun makeTextClickable(clickableSpan: ClickableSpan, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(clickableSpan, start, end, flags)
        textView.movementMethod = LinkMovementMethod.getInstance()

        return this
    }

    fun makeTextClickable(clickableSpan: ClickableSpan, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(clickableSpan, index, index + text.length, flags)
            }
        }

        textView.movementMethod = LinkMovementMethod.getInstance()

        return this
    }

    fun insertImage(@DrawableRes resId: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(ImageSpan(textView.context, resId), start, end, flags)

        return this
    }

    fun quote(start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(QuoteSpan(), start, end, flags)

        return this
    }

    fun quote(vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(QuoteSpan(), index, index + text.length, flags)
            }
        }

        return this
    }

    fun quote(@ColorRes colorResId: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(QuoteSpan(ContextCompat.getColor(textView.context, colorResId)), start, end,
                flags)

        return this
    }

    fun quote(@ColorRes colorResId: Int, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(QuoteSpan(ContextCompat.getColor(textView.context, colorResId)), index, index + text.length, flags)
            }
        }

        return this
    }

    fun strikethrough(start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(StrikethroughSpan(), start, end, flags)

        return this
    }

    fun strikethrough(vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(StrikethroughSpan(), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setTextStyle(style: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(StyleSpan(style), start, end, flags)

        return this
    }

    fun setTextStyle(style: Int, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(StyleSpan(style), index, index + text.length, flags)
            }
        }

        return this
    }

    fun alignText(alignment: Layout.Alignment, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(AlignmentSpan.Standard(alignment), start, end, flags)

        return this
    }

    fun alignText(alignment: Layout.Alignment, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(AlignmentSpan.Standard(alignment), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setSubscript(start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(SubscriptSpan(), start, end, flags)

        return this
    }

    fun setSubscript(vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(SubscriptSpan(), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setSuperscript(start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(SuperscriptSpan(), start, end, flags)

        return this
    }

    fun setSuperscript(vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(SuperscriptSpan(), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setTypeface(family: String, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(TypefaceSpan(family), start, end, flags)

        return this
    }

    fun setTypeface(family: String, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(TypefaceSpan(family), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setTextAppearance(appearance: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(TextAppearanceSpan(textView.context, appearance), start, end,
                flags)

        return this
    }

    fun setTextAppearance(appearance: Int, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(TextAppearanceSpan(textView.context, appearance), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setTextAppearance(appearance: Int, colorList: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(TextAppearanceSpan(textView.context, appearance, colorList), start, end,
                flags)

        return this
    }

    fun setTextAppearance(appearance: Int, colorList: Int, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(TextAppearanceSpan(textView.context, appearance, colorList), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setTextAppearance(family: String, style: Int, size: Int, color: ColorStateList, linkColor: ColorStateList, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(TextAppearanceSpan(family, style, size, color, linkColor), start, end,
                flags)

        return this
    }

    fun setTextAppearance(family: String, style: Int, size: Int, color: ColorStateList, linkColor: ColorStateList, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(TextAppearanceSpan(family, style, size, color, linkColor), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setAbsoluteSize(size: Int, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(AbsoluteSizeSpan(size), start, end, flags)

        return this
    }

    fun setAbsoluteSize(size: Int, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(AbsoluteSizeSpan(size), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setAbsoluteSize(size: Int, dip: Boolean, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(AbsoluteSizeSpan(size, dip), start, end, flags)

        return this
    }

    fun setAbsoluteSize(size: Int, dip: Boolean, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(AbsoluteSizeSpan(size, dip), index, index + text.length, flags)
            }
        }

        return this
    }

    fun setRelativeSize(proportion: Float, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(RelativeSizeSpan(proportion), start, end, flags)

        return this
    }

    fun setRelativeSize(proportion: Float, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(RelativeSizeSpan(proportion), index, index + text.length, flags)
            }
        }

        return this
    }

    fun scaleX(proportion: Float, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(ScaleXSpan(proportion), start, end, flags)

        return this
    }

    fun scaleX(proportion: Float, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(ScaleXSpan(proportion), index, index + text.length, flags)
            }
        }

        return this
    }

    fun blur(radius: Float, style: BlurMaskFilter.Blur, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(MaskFilterSpan(BlurMaskFilter(radius, style)), start, end, flags)

        return this
    }

    fun blur(radius: Float, style: BlurMaskFilter.Blur, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(MaskFilterSpan(BlurMaskFilter(radius, style)), index, index + text.length, flags)
            }
        }

        return this
    }

    fun emboss(direction: FloatArray, ambient: Float, specular: Float, blurRadius: Float, start: Int, end: Int): TextDecorator {
        checkIndexOutOfBoundsException(start, end)
        decoratedContent.setSpan(MaskFilterSpan(EmbossMaskFilter(direction, ambient, specular, blurRadius)), start, end,
                flags)

        return this
    }

    fun emboss(direction: FloatArray, ambient: Float, specular: Float, blurRadius: Float, vararg texts: String): TextDecorator {
        var index: Int

        for (text in texts) {
            if (content.contains(text)) {
                index = content.indexOf(text)
                decoratedContent.setSpan(MaskFilterSpan(EmbossMaskFilter(direction, ambient, specular, blurRadius)), index, index + text.length, flags)
            }
        }

        return this
    }

    fun build() {
        textView.text = decoratedContent
    }

    private fun checkIndexOutOfBoundsException(start: Int, end: Int) {
        when {
            start < 0 -> throw IndexOutOfBoundsException("start is less than 0")
            end > content.length -> throw IndexOutOfBoundsException("end is greater than content length " + content.length)
            start > end -> throw IndexOutOfBoundsException("start is greater than end")
        }
    }

    companion object {

        fun decorate(textView: TextView, content: String): TextDecorator {
            return TextDecorator(textView, content)
        }
    }
}