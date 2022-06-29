package com.loitpcore.views.textview.autofit

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

/**
 * A [TextView] that re-sizes its text to be no larger than the width of the view.
 *
 * @attr ref R.styleable.AutofitTextView_sizeToFit
 * @attr ref R.styleable.AutofitTextView_minTextSize
 * @attr ref R.styleable.AutofitTextView_precision
 */
class LAutofitTextView : AppCompatTextView, AutofitHelper.OnTextSizeChangeListener {

    private var autofitHelper: AutofitHelper? = null

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        autofitHelper = AutofitHelper.create(this, attrs, defStyle)
            .addOnTextSizeChangeListener(this)
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        autofitHelper?.setTextSize(unit, size)
    }

    override fun setLines(lines: Int) {
        super.setLines(lines)
        autofitHelper?.maxLines = lines
    }

    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)
        autofitHelper?.maxLines = maxLines
    }

    private var isSizeToFit: Boolean
        get() = autofitHelper?.isEnabled ?: false
        set(sizeToFit) {
            autofitHelper?.isEnabled = sizeToFit
        }

    /**
     * Sets the property of this field (sizeToFit), to automatically resize the text to fit its
     * constraints.
     */
    fun setSizeToFit() {
        isSizeToFit = true
    }
    /**
     * Returns the maximum size (in pixels) of the text in this View.
     */
    /**
     * Set the maximum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param size The scaled pixel size.
     * @attr ref android.R.styleable#TextView_textSize
     */
    var maxTextSize: Float
        get() = autofitHelper?.maxTextSize ?: 0f
        set(size) {
            autofitHelper?.maxTextSize = size
        }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     * @attr ref android.R.styleable#TextView_textSize
     */
    fun setMaxTextSize(unit: Int, size: Float) {
        autofitHelper?.setMaxTextSize(unit, size)
    }

    /**
     * Returns the minimum size (in pixels) of the text in this View.
     */
    val minTextSize: Float
        get() = autofitHelper?.minTextSize ?: 0f

    /**
     * Set the minimum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param minSize The scaled pixel size.
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    fun setMinTextSize(minSize: Int) {
        autofitHelper?.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, minSize.toFloat())
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit    The desired dimension unit.
     * @param minSize The desired size in the given units.
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    fun setMinTextSize(unit: Int, minSize: Float) {
        autofitHelper?.setMinTextSize(unit, minSize)
    }
    /**
     * Returns the amount of precision used to calculate the correct text size to fit within its
     * bounds.
     */
    /**
     * Set the amount of precision used to calculate the correct text size to fit within its
     * bounds. Lower precision is more precise and takes more time.
     *
     * @param precision The amount of precision.
     */
    var precision: Float?
        get() = autofitHelper?.precision
        set(precision) {
            autofitHelper?.precision = precision ?: 0f
        }

    override fun onTextSizeChange(textSize: Float, oldTextSize: Float) {
        // do nothing
    }
}
