package com.loitpcore.views.textView.color

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.loitpcore.R
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LColorTextView : AppCompatTextView {
    private val mColorTexts: MutableList<String> = ArrayList()
    private val mColors: MutableList<String> = ArrayList()
    private var size = 0
    private var mCurrentText: String? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.LColorTextView, 0, 0)
        val colorTexts = attrsArray.getString(R.styleable.LColorTextView_color_texts)
        val colors = attrsArray.getString(R.styleable.LColorTextView_color_arrays)
        attrsArray.recycle()
        initData(colorTexts, colors)
        setHtmlText()
    }

    private fun initData(colorTexts: String?, colors: String?) {
        try {
            if (!TextUtils.isEmpty(colorTexts)) {
                val texts = colorTexts?.split("\\|".toRegex())?.toTypedArray()
                texts?.let {
                    for (i in it.indices) {
                        mColorTexts.add(it[i])
                    }
                }
            }
            if (!TextUtils.isEmpty(colors)) {
                val texts = colors?.split("\\|".toRegex())?.toTypedArray()
                texts?.let {
                    for (i in it.indices) {
                        mColors.add(it[i])
                    }
                }
            }
            size = min(mColors.size, mColorTexts.size)
            mCurrentText = text.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setHtmlText() {
        try {
            if (!TextUtils.isEmpty(mCurrentText)) {
                for (i in 0 until size) {
                    mCurrentText =
                        mCurrentText?.replace(mColorTexts[i], color(mColors[i], mColorTexts[i]))
                }
            }
            if (!TextUtils.isEmpty(mCurrentText)) {
                text = Html.fromHtml(mCurrentText)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Suppress("unused")
    fun findAndSetStrColor(str: String, color: String): LColorTextView {
        try {
            if (!TextUtils.isEmpty(mCurrentText)) {
                mCurrentText = mCurrentText?.replace(str.toRegex(), color(color, str))
            }
            if (!TextUtils.isEmpty(mCurrentText)) {
                text = Html.fromHtml(mCurrentText)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    fun color(colorCode: String, str: String): String {
        return "<font color=\"$colorCode\">$str</font>"
    }
}
