package com.loitpcore.views.imageView.panorama

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import com.loitpcore.R
import kotlin.math.abs

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LPanoramaImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        // Image's scroll orientation
        const val ORIENTATION_NONE: Byte = -1
        const val ORIENTATION_HORIZONTAL: Byte = 0
        const val ORIENTATION_VERTICAL: Byte = 1
    }

    var orientation = ORIENTATION_NONE
        private set

    // Enable panorama effect or not
    var isPanoramaModeEnabled: Boolean
        private set

    // If true, the image scroll left(top) when the device clockwise rotate along y-axis(x-axis).
    private var mInvertScrollDirection: Boolean

    // Image's width and height
    private var mDrawableWidth = 0
    private var mDrawableHeight = 0

    // View's width and height
    private var mWidth = 0
    private var mHeight = 0

    // Image's offset from initial state(center in the view).
    private var mMaxOffset = 0f

    // The scroll progress.
    private var mProgress = 0f

    // Show scroll bar or not
    var isScrollbarEnabled: Boolean
        private set

    // The paint to draw scrollbar
    private var mScrollbarPaint: Paint? = null

    // Observe scroll state
    private var mOnPanoramaScrollListener: OnPanoramaScrollListener? = null

    private fun initScrollbarPaint() {
        mScrollbarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mScrollbarPaint?.color = Color.WHITE
        mScrollbarPaint?.strokeWidth = dp2px(1.5f)
    }

    fun setGyroscopeObserver(observer: GyroscopeObserver?) {
        observer?.addPanoramaImageView(this)
    }

    fun updateProgress(progress: Float) {
        if (isPanoramaModeEnabled) {
            mProgress = if (mInvertScrollDirection) -progress else progress
            invalidate()
            mOnPanoramaScrollListener?.onScrolled(this, -mProgress)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        mHeight = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        if (drawable != null) {
            mDrawableWidth = drawable.intrinsicWidth
            mDrawableHeight = drawable.intrinsicHeight
            if (mDrawableWidth * mHeight > mDrawableHeight * mWidth) {
                orientation = ORIENTATION_HORIZONTAL
                val imgScale = mHeight.toFloat() / mDrawableHeight.toFloat()
                mMaxOffset = abs((mDrawableWidth * imgScale - mWidth) * 0.5f)
            } else if (mDrawableWidth * mHeight < mDrawableHeight * mWidth) {
                orientation = ORIENTATION_VERTICAL
                val imgScale = mWidth.toFloat() / mDrawableWidth.toFloat()
                mMaxOffset = abs((mDrawableHeight * imgScale - mHeight) * 0.5f)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (!isPanoramaModeEnabled || drawable == null || isInEditMode) {
            super.onDraw(canvas)
            return
        }

        // Draw image
        if (orientation == ORIENTATION_HORIZONTAL) {
            val currentOffsetX = mMaxOffset * mProgress
            canvas.save()
            canvas.translate(currentOffsetX, 0f)
            super.onDraw(canvas)
            canvas.restore()
        } else if (orientation == ORIENTATION_VERTICAL) {
            val currentOffsetY = mMaxOffset * mProgress
            canvas.save()
            canvas.translate(0f, currentOffsetY)
            super.onDraw(canvas)
            canvas.restore()
        }

        // Draw scrollbar
        if (isScrollbarEnabled) {
            when (orientation) {
                ORIENTATION_HORIZONTAL -> {
                    val barBgWidth = mWidth * 0.9f
                    val barWidth = barBgWidth * mWidth / mDrawableWidth
                    val barBgStartX = (mWidth - barBgWidth) / 2
                    val barBgEndX = barBgStartX + barBgWidth
                    val barStartX = barBgStartX + (barBgWidth - barWidth) / 2 * (1 - mProgress)
                    val barEndX = barStartX + barWidth
                    val barY = mHeight * 0.95f
                    mScrollbarPaint?.alpha = 100
                    mScrollbarPaint?.let {
                        canvas.drawLine(barBgStartX, barY, barBgEndX, barY, it)
                    }

                    mScrollbarPaint?.alpha = 255
                    mScrollbarPaint?.let {
                        canvas.drawLine(barStartX, barY, barEndX, barY, it)
                    }
                }
                ORIENTATION_VERTICAL -> {
                    val barBgHeight = mHeight * 0.9f
                    val barHeight = barBgHeight * mHeight / mDrawableHeight
                    val barBgStartY = (mHeight - barBgHeight) / 2
                    val barBgEndY = barBgStartY + barBgHeight
                    val barStartY = barBgStartY + (barBgHeight - barHeight) / 2 * (1 - mProgress)
                    val barEndY = barStartY + barHeight
                    val barX = mWidth * 0.95f
                    mScrollbarPaint?.alpha = 100
                    mScrollbarPaint?.let {
                        canvas.drawLine(barX, barBgStartY, barX, barBgEndY, it)
                    }
                    mScrollbarPaint?.alpha = 255
                    mScrollbarPaint?.let {
                        canvas.drawLine(barX, barStartY, barX, barEndY, it)
                    }
                }
            }
        }
    }

    @Suppress("unused")
    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            Resources.getSystem().displayMetrics
        )
    }

    @Suppress("unused")
    fun setEnablePanoramaMode(enable: Boolean) {
        isPanoramaModeEnabled = enable
    }

    @Suppress("unused")
    var isInvertScrollDirection: Boolean
        get() = mInvertScrollDirection
        set(invert) {
            if (mInvertScrollDirection != invert) {
                mInvertScrollDirection = invert
            }
        }

    @Suppress("unused")
    fun setEnableScrollbar(enable: Boolean) {
        if (isScrollbarEnabled != enable) {
            isScrollbarEnabled = enable
            if (isScrollbarEnabled) {
                initScrollbarPaint()
            } else {
                mScrollbarPaint = null
            }
        }
    }

    override fun setScaleType(scaleType: ScaleType) {
        /**
         * Do nothing because PanoramaImageView only
         * supports [scaleType CENTER_CROP]
         */
    }

    /**
     * Interface definition for a callback to be invoked when the image is scrolling
     */
    interface OnPanoramaScrollListener {
        /**
         * Call when the image is scrolling
         *
         * @param view           the panoramaImageView shows the image
         * @param offsetProgress value between (-1, 1) indicating the offset progress.
         * -1 means the image scrolls to show its left(top) bound,
         * 1 means the image scrolls to show its right(bottom) bound.
         */
        fun onScrolled(view: LPanoramaImageView?, offsetProgress: Float)
    }

    fun setOnPanoramaScrollListener(listener: OnPanoramaScrollListener?) {
        mOnPanoramaScrollListener = listener
    }

    init {
        super.setScaleType(ScaleType.CENTER_CROP)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LPanoramaImageView)
        isPanoramaModeEnabled =
            typedArray.getBoolean(R.styleable.LPanoramaImageView_piv_enablePanoramaMode, true)
        mInvertScrollDirection =
            typedArray.getBoolean(R.styleable.LPanoramaImageView_piv_invertScrollDirection, false)
        isScrollbarEnabled =
            typedArray.getBoolean(R.styleable.LPanoramaImageView_piv_show_scrollbar, true)
        typedArray.recycle()
        if (isScrollbarEnabled) {
            initScrollbarPaint()
        }
    }
}
