package com.loitp.views.sb.vertical

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import kotlin.math.max

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LVerticalSeekBarWrapper
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(
    context,
    attrs,
    defStyleAttr
) {

    override fun onSizeChanged(
        w: Int,
        h: Int,
        oldw: Int,
        oldh: Int
    ) {
        if (useViewRotation()) {
            onSizeChangedUseViewRotation(w = w, h = h, oldw = oldw, oldh = oldh)
        } else {
            onSizeChangedTraditionalRotation(w = w, h = h, oldw = oldw, oldh = oldh)
        }
    }

    private fun onSizeChangedTraditionalRotation(
        w: Int,
        h: Int,
        oldw: Int,
        oldh: Int
    ) {
        val seekBar = childSeekBar
        if (seekBar != null) {
            val hPadding = paddingLeft + paddingRight
            val vPadding = paddingTop + paddingBottom
            val lp = seekBar.layoutParams as LayoutParams
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
            lp.height = max(a = 0, b = h - vPadding)
            seekBar.layoutParams = lp
            seekBar.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
            val seekBarMeasuredWidth = seekBar.measuredWidth
            seekBar.measure(
                MeasureSpec.makeMeasureSpec(max(a = 0, b = w - hPadding), MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(max(a = 0, b = h - vPadding), MeasureSpec.EXACTLY)
            )
            lp.gravity = Gravity.TOP or Gravity.START
            lp.leftMargin = (max(a = 0, b = w - hPadding) - seekBarMeasuredWidth) / 2
            seekBar.layoutParams = lp
        }
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun onSizeChangedUseViewRotation(
        w: Int,
        h: Int,
        oldw: Int,
        oldh: Int
    ) {
        val seekBar = childSeekBar
        if (seekBar != null) {
            val hPadding = paddingLeft + paddingRight
            val vPadding = paddingTop + paddingBottom
            seekBar.measure(
                MeasureSpec.makeMeasureSpec(max(a = 0, b = h - vPadding), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(max(a = 0, b = w - hPadding), MeasureSpec.AT_MOST)
            )
        }
        applyViewRotation(w, h)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        val seekBar = childSeekBar
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (seekBar != null && widthMode != MeasureSpec.EXACTLY) {
            val seekBarWidth: Int
            val seekBarHeight: Int
            val hPadding = paddingLeft + paddingRight
            val vPadding = paddingTop + paddingBottom
            val innerContentWidthMeasureSpec =
                MeasureSpec.makeMeasureSpec(max(a = 0, b = widthSize - hPadding), widthMode)
            val innerContentHeightMeasureSpec =
                MeasureSpec.makeMeasureSpec(max(a = 0, b = heightSize - vPadding), heightMode)
            if (useViewRotation()) {
                seekBar.measure(innerContentHeightMeasureSpec, innerContentWidthMeasureSpec)
                seekBarWidth = seekBar.measuredHeight
                seekBarHeight = seekBar.measuredWidth
            } else {
                seekBar.measure(innerContentWidthMeasureSpec, innerContentHeightMeasureSpec)
                seekBarWidth = seekBar.measuredWidth
                seekBarHeight = seekBar.measuredHeight
            }
            val measuredWidth =
                ViewCompat.resolveSizeAndState(seekBarWidth + hPadding, widthMeasureSpec, 0)
            val measuredHeight =
                ViewCompat.resolveSizeAndState(seekBarHeight + vPadding, heightMeasureSpec, 0)
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    fun applyViewRotation() {
        applyViewRotation(width, height)
    }

    private fun applyViewRotation(
        w: Int,
        h: Int
    ) {
        val seekBar = childSeekBar
        if (seekBar != null) {
            val isLTR = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_LTR
            val rotationAngle = seekBar.rotationAngle
            val seekBarMeasuredWidth = seekBar.measuredWidth
            val seekBarMeasuredHeight = seekBar.measuredHeight
            val hPadding = paddingLeft + paddingRight
            val vPadding = paddingTop + paddingBottom
            val hOffset = (max(0, w - hPadding) - seekBarMeasuredHeight) * 0.5f
            val lp = seekBar.layoutParams
            lp.width = max(0, h - vPadding)
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
            seekBar.layoutParams = lp
            ViewCompat.setPivotX(seekBar, if (isLTR) 0f else max(a = 0, b = h - vPadding).toFloat())
            ViewCompat.setPivotY(seekBar, 0f)
            when (rotationAngle) {
                LVerticalSeekBar.ROTATION_ANGLE_CW_90 -> {
                    ViewCompat.setRotation(seekBar, 90f)
                    if (isLTR) {
                        ViewCompat.setTranslationX(seekBar, seekBarMeasuredHeight + hOffset)
                        ViewCompat.setTranslationY(seekBar, 0f)
                    } else {
                        ViewCompat.setTranslationX(seekBar, -hOffset)
                        ViewCompat.setTranslationY(seekBar, seekBarMeasuredWidth.toFloat())
                    }
                }
                LVerticalSeekBar.ROTATION_ANGLE_CW_270 -> {
                    ViewCompat.setRotation(seekBar, 270f)
                    if (isLTR) {
                        ViewCompat.setTranslationX(seekBar, hOffset)
                        ViewCompat.setTranslationY(seekBar, seekBarMeasuredWidth.toFloat())
                    } else {
                        ViewCompat.setTranslationX(seekBar, -(seekBarMeasuredHeight + hOffset))
                        ViewCompat.setTranslationY(seekBar, 0f)
                    }
                }
            }
        }
    }

    private val childSeekBar: LVerticalSeekBar?
        get() {
            val child = if (childCount > 0) {
                getChildAt(0)
            } else {
                null
            }
            return if (child is LVerticalSeekBar) {
                child
            } else {
                null
            }
        }

    private fun useViewRotation(): Boolean {
        val seekBar = childSeekBar
        return seekBar?.useViewRotation() ?: false
    }
}
