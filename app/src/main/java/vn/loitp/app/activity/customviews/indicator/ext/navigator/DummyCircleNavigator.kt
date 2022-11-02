package vn.loitp.app.activity.customviews.indicator.ext.navigator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.View
import net.lucode.hackware.magicindicator.abs.IPagerNavigator
import net.lucode.hackware.magicindicator.buildins.UIUtil

class DummyCircleNavigator(context: Context) : View(context), IPagerNavigator {

    private var mRadius: Int
    private var mCircleColor = 0
    private var mStrokeWidth: Int
    private var mCircleSpacing: Int
    private var circleCount = 0
    private var currentIndex = 0
    private val mCirclePoints: MutableList<PointF> = ArrayList()
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        prepareCirclePoints()
    }

    private fun prepareCirclePoints() {
        mCirclePoints.clear()
        if (circleCount > 0) {
            val y = height / 2
            val measureWidth = circleCount * mRadius * 2 + (circleCount - 1) * mCircleSpacing
            val centerSpacing = mRadius * 2 + mCircleSpacing
            var startX = (width - measureWidth) / 2 + mRadius
            for (i in 0 until circleCount) {
                val pointF = PointF(startX.toFloat(), y.toFloat())
                mCirclePoints.add(pointF)
                startX += centerSpacing
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        drawDeselectedCircles(canvas)
        drawSelectedCircle(canvas)
    }

    private fun drawDeselectedCircles(canvas: Canvas) {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mStrokeWidth.toFloat()
        mPaint.color = mCircleColor
        var i = 0
        val j = mCirclePoints.size
        while (i < j) {
            val pointF = mCirclePoints[i]
            canvas.drawCircle(pointF.x, pointF.y, mRadius.toFloat(), mPaint)
            i++
        }
    }

    private fun drawSelectedCircle(canvas: Canvas) {
        mPaint.style = Paint.Style.FILL
        if (mCirclePoints.size > 0) {
            val selectedCircleX = mCirclePoints[currentIndex].x
            canvas.drawCircle(selectedCircleX, (height / 2).toFloat(), mRadius.toFloat(), mPaint)
        }
    }

    override fun onAttachToMagicIndicator() {}

    override fun onDetachFromMagicIndicator() {}

    override fun notifyDataSetChanged() {
        prepareCirclePoints()
        invalidate()
    }

    override fun onPageSelected(position: Int) {
        currentIndex = position
        invalidate()
    }

    @Suppress("unused")
    var circleColor: Int
        get() = mCircleColor
        set(circleColor) {
            mCircleColor = circleColor
            invalidate()
        }
    var strokeWidth: Int
        get() = mStrokeWidth
        set(strokeWidth) {
            mStrokeWidth = strokeWidth
            invalidate()
        }
    var radius: Int
        get() = mRadius
        set(radius) {
            mRadius = radius
            prepareCirclePoints()
            invalidate()
        }

    @Suppress("unused")
    var circleSpacing: Int
        get() = mCircleSpacing
        set(circleSpacing) {
            mCircleSpacing = circleSpacing
            prepareCirclePoints()
            invalidate()
        }

    init {
        mRadius = UIUtil.dip2px(context, 3.0)
        mCircleSpacing = UIUtil.dip2px(context, 8.0)
        mStrokeWidth = UIUtil.dip2px(context, 1.0)
    }
}
