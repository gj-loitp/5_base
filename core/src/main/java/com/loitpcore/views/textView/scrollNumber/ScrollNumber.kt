package com.loitpcore.views.textView.scrollNumber

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.annotation.IntRange
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
internal class ScrollNumber @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(mContext, attrs, defStyleAttr) {

    companion object {
        const val TAG = "ScrollNumber"

        /**
         * default animation velocity
         */
        const val DEFAULT_VELOCITY = 15
    }

    /**
     * number to - number from
     */
    private var mDeltaNum = 0

    /**
     * the current showing number
     */
    private var mCurNum = 0

    /**
     * the next showing number
     */
    private var mNextNum = 0

    /**
     * the target number
     */
    private var mTargetNum = 0

    /**
     * number offset
     */
    private var mOffset = 0f
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mInterpolator: Interpolator = AccelerateDecelerateInterpolator()
    private var mTextCenterX = 0f
    private var mTextHeight = 0
    private val mTextBounds = Rect()
    private var mTextSize = sp2px(130f)
    private var mTextColor = -0x1000000
    private var mTypeface: Typeface? = null
    private var mVelocity = DEFAULT_VELOCITY

    fun setVelocity(@IntRange(from = 0, to = 1000) velocity: Int) {
        mVelocity = velocity
    }

    fun setNumber(from: Int, to: Int, delay: Long) {
        postDelayed({
            setFromNumber(from)
            setTargetNumber(to)
            mDeltaNum = to - from
        }, delay)
    }

    fun setTextSize(textSize: Int) {
        mTextSize = sp2px(textSize.toFloat())
        mPaint.textSize = mTextSize.toFloat()
        measureTextHeight()
        requestLayout()
        invalidate()
    }

    fun setTextFont(fileName: String?) {
        require(!TextUtils.isEmpty(fileName)) {
            "please check file name end with '.ttf' or '.otf'"
        }
        mTypeface = Typeface.createFromAsset(mContext.assets, fileName)
        if (mTypeface == null) throw RuntimeException("please check your font!")
        mPaint.typeface = mTypeface
        requestLayout()
        invalidate()
    }

    fun setTextColor(mTextColor: Int) {
        this.mTextColor = mTextColor
        mPaint.color = mTextColor
        invalidate()
    }

    fun setInterpolator(interpolator: Interpolator) {
        mInterpolator = interpolator
    }

    private fun measureTextHeight() {
        mPaint.getTextBounds(mCurNum.toString() + "", 0, 1, mTextBounds)
        mTextHeight = mTextBounds.height()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
        mTextCenterX = (measuredWidth - paddingLeft - paddingRight ushr 1).toFloat()
    }

    private fun measureHeight(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = size
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                mPaint.getTextBounds("0", 0, 1, mTextBounds)
                result = mTextBounds.height()
            }
        }
        result = if (mode == MeasureSpec.AT_MOST) min(result, size) else result
        return result + paddingTop + paddingBottom + dp2px(40f)
    }

    private fun measureWidth(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = size
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> {
                mPaint.getTextBounds("0", 0, 1, mTextBounds)
                result = mTextBounds.width()
            }
        }
        result = if (mode == MeasureSpec.AT_MOST) min(result, size) else result
        return result + paddingLeft + paddingRight + 15
    }

    override fun onDraw(canvas: Canvas) {
        if (mCurNum != mTargetNum) {
            postDelayed(mScrollRunnable, 0)
        }

        canvas.translate(0f, mOffset * measuredHeight)
        drawSelf(canvas)
        drawNext(canvas)
    }

    private fun setFromNumber(number: Int) {
        if (number < 0 || number > 9) throw RuntimeException("invalidate number , should in [0,9]")
        calNum(number)
        mOffset = 0f
        invalidate()
    }

    @Suppress("NAME_SHADOWING")
    private fun calNum(number: Int) {
        var number = number
        number = if (number == -1) 9 else number
        number = if (number == 10) 0 else number
        mCurNum = number
        mNextNum = if (number + 1 == 10) 0 else number + 1
    }

    private val mScrollRunnable = Runnable {
        val x = (1 - 1.0 * (mTargetNum - mCurNum) / mDeltaNum).toFloat()
        mOffset -= (mVelocity * 0.01f * (1 - mInterpolator.getInterpolation(x) + 0.1)).toFloat()
        invalidate()
        if (mOffset <= -1) {
            mOffset = 0f
            calNum(mCurNum + 1)
        }
    }

    private fun drawNext(canvas: Canvas) {
        val y = (measuredHeight * 1.5).toFloat()
        canvas.drawText(mNextNum.toString() + "", mTextCenterX, y + mTextHeight / 2, mPaint)
    }

    private fun drawSelf(canvas: Canvas) {
        val y = measuredHeight / 2
        canvas.drawText(
            mCurNum.toString() + "",
            mTextCenterX,
            (y + mTextHeight / 2).toFloat(),
            mPaint
        )
    }

    @Suppress("unused")
    fun setTargetNumber(nextNum: Int) {
        mTargetNum = nextNum
        invalidate()
    }

    @Suppress("unused")
    private fun dp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, resources.displayMetrics
        ).toInt()
    }

    private fun sp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            dpVal, resources.displayMetrics
        ).toInt()
    }

    init {
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.textSize = mTextSize.toFloat()
        mPaint.color = mTextColor
        if (mTypeface != null) mPaint.typeface = mTypeface
        measureTextHeight()
    }
}
