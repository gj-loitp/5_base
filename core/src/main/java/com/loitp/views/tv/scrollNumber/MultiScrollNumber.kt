package com.loitp.views.tv.scrollNumber

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import com.loitp.core.utilities.LAppResource.getColor
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
// guide https://github.com/a-voyager/ScrollNumber?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3973
class MultiScrollNumber @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(mContext, attrs, defStyleAttr) {

    private val mTargetNumbers: MutableList<Int> = ArrayList()
    private val mPrimaryNumbers: MutableList<Int> = ArrayList()
    private val mScrollNumbers: MutableList<ScrollNumber> = ArrayList()
    private var mTextSize = 130
    private var mTextColors = intArrayOf(R.color.purple)
    private var mInterpolator: Interpolator = AccelerateDecelerateInterpolator()
    private var mFontFileName: String? = null
    private var mVelocity = 15

    fun setNumber(num: Double) {
        require(num >= 0) {
            "number value should >= 0"
        }
        resetView()
        val str = num.toString()
        val charArray = str.toCharArray()
        for (i in charArray.indices.reversed()) {
            if (Character.isDigit(charArray[i])) {
                mTargetNumbers.add(charArray[i] - '0')
            } else {
                mTargetNumbers.add(-1)
            }
        }
        for (i in mTargetNumbers.indices.reversed()) {
            if (mTargetNumbers[i] != -1) {
                val scrollNumber = ScrollNumber(mContext)
                scrollNumber.setTextColor(getColor(mTextColors[i % mTextColors.size]))
                scrollNumber.setVelocity(mVelocity)
                scrollNumber.setTextSize(mTextSize)
                scrollNumber.setInterpolator(mInterpolator)
                if (!TextUtils.isEmpty(mFontFileName)) scrollNumber.setTextFont(mFontFileName)
                scrollNumber.setNumber(0, mTargetNumbers[i], (i * 10).toLong())
                mScrollNumbers.add(scrollNumber)
                addView(scrollNumber)
            } else {
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                val point = TextView(mContext)
                point.text = " . "
                point.gravity = Gravity.BOTTOM
                point.setTextColor(getColor(mTextColors[i % mTextColors.size]))
                point.textSize = (mTextSize / 3).toFloat()
                addView(point, params)
            }
        }
    }

    @Suppress("unused")
    fun setNumber(no: Int) {
        resetView()
        var number = no
        while (number > 0) {
            val i = number % 10
            mTargetNumbers.add(i)
            number /= 10
        }
        for (i in mTargetNumbers.indices.reversed()) {
            val scrollNumber = ScrollNumber(mContext)
            scrollNumber.setTextColor(getColor(mTextColors[i % mTextColors.size]))
            scrollNumber.setVelocity(mVelocity)
            scrollNumber.setTextSize(mTextSize)
            scrollNumber.setInterpolator(mInterpolator)
            if (!TextUtils.isEmpty(mFontFileName)) {
                scrollNumber.setTextFont(mFontFileName)
            }
            scrollNumber.setNumber(0, mTargetNumbers[i], (i * 10).toLong())
            mScrollNumbers.add(scrollNumber)
            addView(scrollNumber)
        }
    }

    private fun resetView() {
        mTargetNumbers.clear()
        mScrollNumbers.clear()
        removeAllViews()
    }

    @Suppress("unused")
    fun setNumber(
        from: Int,
        to: Int
    ) {
        if (to < from) throw UnsupportedOperationException("'to' value must > 'from' value")
        resetView()
        // operate to
        var number = to
        var count = 0
        while (number > 0) {
            val i = number % 10
            mTargetNumbers.add(i)
            number /= 10
            count++
        }
        // operate from
        number = from
        while (count > 0) {
            val i = number % 10
            mPrimaryNumbers.add(i)
            number /= 10
            count--
        }
        for (i in mTargetNumbers.indices.reversed()) {
            val scrollNumber = ScrollNumber(mContext)
            scrollNumber.setTextColor(getColor(mTextColors[i % mTextColors.size]))
            scrollNumber.setTextSize(mTextSize)
            if (!TextUtils.isEmpty(mFontFileName)) scrollNumber.setTextFont(mFontFileName)
            scrollNumber.setNumber(mPrimaryNumbers[i], mTargetNumbers[i], (i * 10).toLong())
            mScrollNumbers.add(scrollNumber)
            addView(scrollNumber)
        }
    }

    fun setTextColors(@ColorRes textColors: IntArray?) {
        require(!(textColors == null || textColors.isEmpty())) { "color array couldn't be empty!" }
        mTextColors = textColors
        for (i in mScrollNumbers.indices.reversed()) {
            val scrollNumber = mScrollNumbers[i]
            scrollNumber.setTextColor(getColor(mTextColors[i % mTextColors.size]))
        }
    }

    fun setTextSize(textSize: Int) {
        require(textSize > 0) { "text size must > 0!" }
        mTextSize = textSize
        for (s in mScrollNumbers) {
            s.setTextSize(textSize)
        }
    }

    fun setInterpolator(interpolator: Interpolator?) {
        requireNotNull(interpolator) { "interpolator couldn't be null" }
        mInterpolator = interpolator
        for (s in mScrollNumbers) {
            s.setInterpolator(interpolator)
        }
    }

    @Suppress("unused")
    fun setTextFont(fileName: String?) {
        require(!TextUtils.isEmpty(fileName)) { "file name is null" }
        mFontFileName = fileName
        for (s in mScrollNumbers) {
            s.setTextFont(fileName)
        }
    }

    fun setScrollVelocity(@IntRange(from = 0, to = 1000) velocity: Int) {
        mVelocity = velocity
        for (s in mScrollNumbers) {
            s.setVelocity(velocity)
        }
    }

    @Suppress("unused")
    private fun dp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, resources.displayMetrics
        ).toInt()
    }

    @Suppress("unused")
    private fun sp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            dpVal, resources.displayMetrics
        ).toInt()
    }

    init {
        val typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MultiScrollNumber)
        val primaryNumber = typedArray.getInteger(R.styleable.MultiScrollNumber_primary_number, 0)
        val targetNumber = typedArray.getInteger(R.styleable.MultiScrollNumber_target_number, 0)
        val numberSize = typedArray.getInteger(R.styleable.MultiScrollNumber_number_size, 130)
        setNumber(primaryNumber, targetNumber)
        setTextSize(numberSize)
        typedArray.recycle()
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }
}
