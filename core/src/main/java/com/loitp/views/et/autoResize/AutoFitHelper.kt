package com.loitp.views.et.autoResize

import android.content.res.Resources
import android.text.*
import android.text.method.SingleLineTransformationMethod
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.loitp.R
import com.loitp.core.ext.d

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AutoFitHelper private constructor(view: TextView) {

    companion object {
        private const val TAG = "AutoFitTextHelper"
        private const val SPEW = false

        // Minimum size of the text in pixels
        private const val DEFAULT_MIN_TEXT_SIZE = 8 // sp

        // How precise we want to be when reaching the target textWidth size
        private const val DEFAULT_PRECISION = 0.5f
        /**
         * Creates a new instance of `AutofitHelper` that wraps a [TextView] and enables
         * automatically sizing the text to fit.
         */
        /**
         * Creates a new instance of `AutofitHelper` that wraps a [TextView] and enables
         * automatically sizing the text to fit.
         */
        /**
         * Creates a new instance of `AutofitHelper` that wraps a [TextView] and enables
         * automatically sizing the text to fit.
         */
        @JvmStatic
        @JvmOverloads
        fun create(view: TextView, attrs: AttributeSet? = null, defStyle: Int = 0): AutoFitHelper {
            val helper = AutoFitHelper(view)
            var sizeToFit = true
            if (attrs != null) {
                val context = view.context
                var minTextSize = helper.minTextSize.toInt()
                var precision = helper.precision
                val typedArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AutoFitEditText,
                    defStyle,
                    0
                )
                sizeToFit = typedArray.getBoolean(R.styleable.AutoFitEditText_sizeToFit, sizeToFit)
                minTextSize = typedArray.getDimensionPixelSize(
                    R.styleable.AutoFitEditText_minTextSize,
                    minTextSize
                )
                precision = typedArray.getFloat(R.styleable.AutoFitEditText_precision, precision)
                typedArray.recycle()
                helper.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, minTextSize.toFloat())
                    .setPrecision(precision)
            }
            helper.setEnabled(sizeToFit)
            return helper
        }

        /**
         * Re-sizes the textSize of the TextView so that the text fits within the bounds of the View.
         */
        private fun autoFit(
            view: TextView,
            paint: TextPaint,
            minTextSize: Float,
            maxTextSize: Float,
            maxLines: Int,
            precision: Float
        ) {
            if (maxLines <= 0 || maxLines == Int.MAX_VALUE) {
                // Don't auto-size since there's no limit on lines.
                return
            }
            val targetWidth = view.width - view.paddingLeft - view.paddingRight
            if (targetWidth <= 0) {
                return
            }
            var text = view.text
            val method = view.transformationMethod
            if (method != null) {
                text = method.getTransformation(text, view)
            }
            val context = view.context
            var r = Resources.getSystem()
            val displayMetrics: DisplayMetrics
            var size = maxTextSize
            val high = size
            val low = 0f
            if (context != null) {
                r = context.resources
            }
            displayMetrics = r.displayMetrics
            paint.set(view.paint)
            paint.textSize = size
            if (maxLines == 1 && paint.measureText(text, 0, text.length) > targetWidth ||
                getLineCount(text, paint, size, targetWidth.toFloat(), displayMetrics) > maxLines
            ) {
                size = getAutofitTextSize(
                    text, paint, targetWidth.toFloat(), maxLines, low, high, precision,
                    displayMetrics
                )
            }
            if (size < minTextSize) {
                size = minTextSize
            }
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }

        /**
         * Recursive binary search to find the best size for the text.
         */
        private fun getAutofitTextSize(
            text: CharSequence,
            paint: TextPaint,
            targetWidth: Float,
            maxLines: Int,
            low: Float,
            high: Float,
            precision: Float,
            displayMetrics: DisplayMetrics
        ): Float {
            val mid = (low + high) / 2.0f
            var lineCount = 1
            var layout: StaticLayout? = null
            paint.textSize =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid, displayMetrics)
            if (maxLines != 1) {
                layout = StaticLayout(
                    /* source = */ text,
                    /* paint = */ paint,
                    /* width = */ targetWidth.toInt(),
                    /* align = */ Layout.Alignment.ALIGN_NORMAL,
                    /* spacingmult = */ 1.0f,
                    /* spacingadd = */ 0.0f,
                    /* includepad = */ true
                )
                lineCount = layout.lineCount
            }
            if (SPEW) {
                d(
                    TAG,
                    "low=$low high=$high mid=$mid target=$targetWidth maxLines=$maxLines lineCount=$lineCount"
                )
            }
            return if (lineCount > maxLines) {
                // For the case that `text` has more newline characters than `maxLines`.
                if (high - low < precision) {
                    low
                } else {
                    getAutofitTextSize(
                        text = text,
                        paint = paint,
                        targetWidth = targetWidth,
                        maxLines = maxLines,
                        low = low,
                        high = mid,
                        precision = precision,
                        displayMetrics = displayMetrics
                    )
                }
            } else if (lineCount < maxLines) {
                getAutofitTextSize(
                    text = text,
                    paint = paint,
                    targetWidth = targetWidth,
                    maxLines = maxLines,
                    low = mid,
                    high = high,
                    precision = precision,
                    displayMetrics = displayMetrics
                )
            } else {
                var maxLineWidth = 0f
                if (maxLines == 1) {
                    maxLineWidth = paint.measureText(text, 0, text.length)
                } else {
                    for (i in 0 until lineCount) {
                        layout?.let {
                            if (it.getLineWidth(i) > maxLineWidth) {
                                maxLineWidth = layout.getLineWidth(i)
                            }
                        }
                    }
                }
                when {
                    high - low < precision -> {
                        low
                    }
                    maxLineWidth > targetWidth -> {
                        getAutofitTextSize(
                            text, paint, targetWidth, maxLines, low, mid, precision,
                            displayMetrics
                        )
                    }
                    maxLineWidth < targetWidth -> {
                        getAutofitTextSize(
                            text, paint, targetWidth, maxLines, mid, high, precision,
                            displayMetrics
                        )
                    }
                    else -> {
                        mid
                    }
                }
            }
        }

        private fun getLineCount(
            text: CharSequence,
            paint: TextPaint,
            size: Float,
            width: Float,
            displayMetrics: DisplayMetrics
        ): Int {
            paint.textSize =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size, displayMetrics)
            val layout = StaticLayout(
                text,
                paint,
                width.toInt(),
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0.0f,
                true
            )
            return layout.lineCount
        }

        private fun getMaxLines(view: TextView): Int {
            val maxLines: Int // No limit (Integer.MAX_VALUE also means no limit)
            val method = view.transformationMethod
            maxLines = if (method != null && method is SingleLineTransformationMethod) {
                1
            } else {
                view.maxLines
            }
            return maxLines
        }
    }

    private val mTextView: TextView
    private val mPaint: TextPaint

    /**
     * Original textSize of the TextView.
     */
    private var mTextSize = 0f

    /**
     * @see TextView.getMaxLines
     */
    var maxLines: Int
        private set

    /**
     * Returns the minimum size (in pixels) of the text.
     */
    var minTextSize: Float
        private set

    /**
     * Returns the maximum size (in pixels) of the text.
     */
    var maxTextSize: Float
        private set

    /**
     * Returns the amount of precision used to calculate the correct text size to fit within its
     * bounds.
     */
    var precision: Float

    /**
     * Returns whether or not automatically resizing text is enabled.
     */
    var isEnabled = false
        private set
    private var mIsAutoFitting = false
    private var mListeners: ArrayList<OnTextSizeChangeListener>? = null
    private val mTextWatcher: TextWatcher = AutoFitTextWatcher()
    private val mOnLayoutChangeListener: View.OnLayoutChangeListener =
        AutoFitOnLayoutChangeListener()

    init {
        val context = view.context
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        mTextView = view
        mPaint = TextPaint()
        setRawTextSize(view.textSize)
        maxLines = getMaxLines(view)
        minTextSize = scaledDensity * DEFAULT_MIN_TEXT_SIZE
        maxTextSize = mTextSize
        precision = DEFAULT_PRECISION
    }

    /**
     * Adds an [OnTextSizeChangeListener] to the list of those whose methods are called
     * whenever the [TextView]'s `textSize` changes.
     */
    fun addOnTextSizeChangeListener(listener: OnTextSizeChangeListener): AutoFitHelper {
        if (mListeners == null) {
            mListeners = ArrayList()
        }
        mListeners?.add(listener)
        return this
    }

    /**
     * Removes the specified [OnTextSizeChangeListener] from the list of those whose methods
     * are called whenever the [TextView]'s `textSize` changes.
     */
    @Suppress("unused")
    fun removeOnTextSizeChangeListener(listener: OnTextSizeChangeListener): AutoFitHelper {
        mListeners?.remove(listener)
        return this
    }

    /**
     * Set the amount of precision used to calculate the correct text size to fit within its
     * bounds. Lower precision is more precise and takes more time.
     *
     * @param precision The amount of precision.
     */
    fun setPrecision(precision: Float): AutoFitHelper {
        if (this.precision != precision) {
            this.precision = precision
            autoFit()
        }
        return this
    }

    /**
     * Set the minimum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param size The scaled pixel size.
     * @attr ref me.grantland.R.styleable#AutofitEditText_minTextSize
     */
    @Suppress("unused")
    fun setMinTextSize(size: Float): AutoFitHelper {
        return setMinTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     * @attr ref me.grantland.R.styleable#AutofitEditText_minTextSize
     */
    fun setMinTextSize(
        unit: Int,
        size: Float
    ): AutoFitHelper {
        val context = mTextView.context
        var r = Resources.getSystem()
        if (context != null) {
            r = context.resources
        }
        setRawMinTextSize(size = TypedValue.applyDimension(unit, size, r.displayMetrics))
        return this
    }

    private fun setRawMinTextSize(size: Float) {
        if (size != minTextSize) {
            minTextSize = size
            autoFit()
        }
    }

    /**
     * Set the maximum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param size The scaled pixel size.
     * @attr ref android.R.styleable#TextView_textSize
     */
    fun setMaxTextSize(size: Float): AutoFitHelper {
        return setMaxTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     * @attr ref android.R.styleable#TextView_textSize
     */
    fun setMaxTextSize(
        unit: Int,
        size: Float
    ): AutoFitHelper {
        val context = mTextView.context
        var r = Resources.getSystem()
        if (context != null) {
            r = context.resources
        }
        setRawMaxTextSize(TypedValue.applyDimension(unit, size, r.displayMetrics))
        return this
    }

    private fun setRawMaxTextSize(size: Float) {
        if (size != maxTextSize) {
            maxTextSize = size
            autoFit()
        }
    }

    /**
     * @see TextView.setMaxLines
     */
    fun setMaxLines(lines: Int): AutoFitHelper {
        if (maxLines != lines) {
            maxLines = lines
            autoFit()
        }
        return this
    }

    /**
     * Set the enabled state of automatically resizing text.
     */
    fun setEnabled(enabled: Boolean?): AutoFitHelper {
        enabled?.let {
            if (isEnabled != it) {
                isEnabled = it
                if (it) {
                    mTextView.addTextChangedListener(mTextWatcher)
                    mTextView.addOnLayoutChangeListener(mOnLayoutChangeListener)
                    autoFit()
                } else {
                    mTextView.removeTextChangedListener(mTextWatcher)
                    mTextView.removeOnLayoutChangeListener(mOnLayoutChangeListener)
                    mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
                }
            }
        }
        return this
    }
    /**
     * Returns the original text size of the View.
     *
     * @see TextView.getTextSize
     */
    /**
     * Set the original text size of the View.
     *
     * @see TextView.setTextSize
     */
    var textSize: Float
        get() = mTextSize
        set(size) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
        }

    /**
     * Set the original text size of the View.
     *
     * @see TextView.setTextSize
     */
    fun setTextSize(
        unit: Int,
        size: Float
    ) {
        if (mIsAutoFitting) {
            // We don't want to update the TextView's actual textSize while we're autofitting
            // since it'd get set to the autofitTextSize
            return
        }
        val context = mTextView.context
        var r = Resources.getSystem()
        if (context != null) {
            r = context.resources
        }
        setRawTextSize(TypedValue.applyDimension(unit, size, r.displayMetrics))
    }

    private fun setRawTextSize(size: Float) {
        if (mTextSize != size) {
            mTextSize = size
        }
    }

    private fun autoFit() {
        val oldTextSize = mTextView.textSize
        mIsAutoFitting = true
        autoFit(mTextView, mPaint, minTextSize, maxTextSize, maxLines, precision)
        mIsAutoFitting = false
        val textSize: Float = mTextView.textSize
        if (textSize != oldTextSize) {
            sendTextSizeChange(textSize, oldTextSize)
        }
    }

    private fun sendTextSizeChange(
        textSize: Float,
        oldTextSize: Float
    ) {
        if (mListeners == null) {
            return
        }
        mListeners?.forEach { listener ->
            listener.onTextSizeChange(textSize = textSize, oldTextSize = oldTextSize)
        }
    }

    private inner class AutoFitTextWatcher : TextWatcher {
        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            // do nothing
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            autoFit()
        }

        override fun afterTextChanged(editable: Editable) {
            // do nothing
        }
    }

    private inner class AutoFitOnLayoutChangeListener : View.OnLayoutChangeListener {

        override fun onLayoutChange(
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            autoFit()
        }
    }

    /**
     * When an object of a type is attached to an `AutofitHelper`, its methods will be called
     * when the `textSize` is changed.
     */
    interface OnTextSizeChangeListener {
        /**
         * This method is called to notify you that the size of the text has changed to
         * `textSize` from `oldTextSize`.
         */
        fun onTextSizeChange(textSize: Float, oldTextSize: Float)
    }
}
