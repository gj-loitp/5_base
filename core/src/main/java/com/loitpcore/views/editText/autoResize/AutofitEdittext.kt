package com.loitpcore.views.editText.autoResize

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatEditText
import com.loitpcore.views.editText.autoResize.AutofitHelper.Companion.create

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AutofitEdittext : AppCompatEditText, AutofitHelper.OnTextSizeChangeListener {
    /**
     * Returns the [AutofitHelper] for this View.
     */
    var autofitHelper: AutofitHelper? = null
        private set

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

    @Suppress("unused")
    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        autofitHelper = create(this, attrs, defStyle)
            .addOnTextSizeChangeListener(this)
    }
    // Getters and Setters
    /**
     * {@inheritDoc}
     */
    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)

        autofitHelper?.setTextSize(unit = unit, size = size)
    }

    /**
     * {@inheritDoc}
     */
    override fun setLines(lines: Int) {
        super.setLines(lines)

        autofitHelper?.setMaxLines(lines = lines)
    }

    /**
     * {@inheritDoc}
     */
    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)

        autofitHelper?.setMaxLines(lines = maxLines)
    }
    /**
     * Returns whether or not the text will be automatically re-sized to fit its constraints.
     */
    /**
     * If true, the text will automatically be re-sized to fit its constraints; if false, it will
     * act like a normal TextView.
     *
     * param sizeToFit
     */
    var isSizeToFit: Boolean?
        get() = autofitHelper?.isEnabled
        set(sizeToFit) {
            autofitHelper?.setEnabled(enabled = sizeToFit)
        }

    /**
     * Sets the property of this field (sizeToFit), to automatically resize the text to fit its
     * constraints.
     */
    @Suppress("unused")
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
     * param size The scaled pixel size.
     * @attr ref android.R.styleable#TextView_textSize
     */
    @Suppress("unused")
    var maxTextSize: Float?
        get() = autofitHelper?.maxTextSize
        set(size) {
            size?.let {
                autofitHelper?.setMaxTextSize(size = it)
            }
        }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     * @attr ref android.R.styleable#TextView_textSize
     */
    @Suppress("unused")
    fun setMaxTextSize(unit: Int, size: Float) {
        autofitHelper?.setMaxTextSize(unit = unit, size = size)
    }

    /**
     * Returns the minimum size (in pixels) of the text in this View.
     */
    val minTextSize: Float?
        get() = autofitHelper?.minTextSize

    /**
     * Set the minimum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param minSize The scaled pixel size.
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    @Suppress("unused")
    fun setMinTextSize(minSize: Int) {
        autofitHelper?.setMinTextSize(unit = TypedValue.COMPLEX_UNIT_SP, size = minSize.toFloat())
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit    The desired dimension unit.
     * @param minSize The desired size in the given units.
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    @Suppress("unused")
    fun setMinTextSize(unit: Int, minSize: Float) {
        autofitHelper?.setMinTextSize(unit = unit, size = minSize)
    }
    /**
     * Returns the amount of precision used to calculate the correct text size to fit within its
     * bounds.
     */
    /**
     * Set the amount of precision used to calculate the correct text size to fit within its
     * bounds. Lower precision is more precise and takes more time.
     *
     * param precision The amount of precision.
     */
    @Suppress("unused")
    var precision: Float?
        get() = autofitHelper?.precision
        set(precision) {
            precision?.let {
                autofitHelper?.setPrecision(it)
            }
        }

    override fun onTextSizeChange(textSize: Float, oldTextSize: Float) {
        // do nothing
    }
}
