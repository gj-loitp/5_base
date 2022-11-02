package vn.loitp.app.activity.customviews.indicator.ext.navigator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import net.lucode.hackware.magicindicator.NavigatorHelper
import net.lucode.hackware.magicindicator.NavigatorHelper.OnNavigatorScrollListener
import net.lucode.hackware.magicindicator.abs.IPagerNavigator
import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder
import net.lucode.hackware.magicindicator.buildins.UIUtil
import kotlin.math.abs

class ScaleCircleNavigator(context: Context) :
    View(context),
    IPagerNavigator,
    OnNavigatorScrollListener {

    private var mMinRadius = 0
    private var mMaxRadius = 0
    private var mNormalCircleColor = Color.LTGRAY
    private var mSelectedCircleColor = Color.GRAY
    private var mCircleSpacing = 0
    private var mCircleCount = 0
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mCirclePoints: MutableList<PointF> = ArrayList()
    private val mCircleRadiusArray = SparseArray<Float>()

    private var mTouchable = false
    private var mCircleClickListener: OnCircleClickListener? = null
    private var mDownX = 0f
    private var mDownY = 0f
    private var mTouchSlop = 0
    private var mFollowTouch = true
    private val mNavigatorHelper = NavigatorHelper()
    private var mStartInterpolator = LinearInterpolator()

    private fun init(context: Context) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        mMinRadius = UIUtil.dip2px(context, 3.0)
        mMaxRadius = UIUtil.dip2px(context, 5.0)
        mCircleSpacing = UIUtil.dip2px(context, 8.0)
        mNavigatorHelper.setNavigatorScrollListener(this)
        mNavigatorHelper.setSkimOver(true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = width
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> result = if (mCircleCount <= 0) {
                paddingLeft + paddingRight
            } else {
                (mCircleCount - 1) * mMinRadius * 2 + mMaxRadius * 2 + (mCircleCount - 1) * mCircleSpacing + paddingLeft + paddingRight
            }
            else -> {
                // do sth
            }
        }
        return result
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = height
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED ->
                result =
                    mMaxRadius * 2 + paddingTop + paddingBottom
            else -> {
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        var i = 0
        val j = mCirclePoints.size
        while (i < j) {
            val point = mCirclePoints[i]
            val radius = mCircleRadiusArray[i, mMinRadius.toFloat()]
            mPaint.color = ArgbEvaluatorHolder.eval(
                (radius - mMinRadius) / (mMaxRadius - mMinRadius),
                mNormalCircleColor,
                mSelectedCircleColor
            )
            canvas.drawCircle(point.x, height / 2.0f, radius, mPaint)
            i++
        }
    }

    private fun prepareCirclePoints() {
        mCirclePoints.clear()
        if (mCircleCount > 0) {
            val y = height / 2.0f
            val centerSpacing = mMinRadius * 2 + mCircleSpacing
            var startX = mMaxRadius + paddingLeft
            for (i in 0 until mCircleCount) {
                val pointF = PointF(startX.toFloat(), y)
                mCirclePoints.add(pointF)
                startX += centerSpacing
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (mTouchable) {
                mDownX = x
                mDownY = y
                return true
            }
            MotionEvent.ACTION_UP -> if (mCircleClickListener != null) {
                if (abs(x - mDownX) <= mTouchSlop && abs(y - mDownY) <= mTouchSlop) {
                    var max = Float.MAX_VALUE
                    var index = 0
                    var i = 0
                    while (i < mCirclePoints.size) {
                        val pointF = mCirclePoints[i]
                        val offset = abs(pointF.x - x)
                        if (offset < max) {
                            max = offset
                            index = i
                        }
                        i++
                    }
                    mCircleClickListener?.onClick(index)
                }
            }
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mNavigatorHelper.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        mNavigatorHelper.onPageSelected(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
        mNavigatorHelper.onPageScrollStateChanged(state)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        prepareCirclePoints()
    }

    override fun notifyDataSetChanged() {
        prepareCirclePoints()
        requestLayout()
    }

    override fun onAttachToMagicIndicator() {}

    override fun onDetachFromMagicIndicator() {}

    @Suppress("unused")
    fun setMinRadius(minRadius: Int) {
        mMinRadius = minRadius
        prepareCirclePoints()
        invalidate()
    }

    @Suppress("unused")
    fun setMaxRadius(maxRadius: Int) {
        mMaxRadius = maxRadius
        prepareCirclePoints()
        invalidate()
    }

    fun setNormalCircleColor(normalCircleColor: Int) {
        mNormalCircleColor = normalCircleColor
        invalidate()
    }

    fun setSelectedCircleColor(selectedCircleColor: Int) {
        mSelectedCircleColor = selectedCircleColor
        invalidate()
    }

    @Suppress("unused")
    fun setCircleSpacing(circleSpacing: Int) {
        mCircleSpacing = circleSpacing
        prepareCirclePoints()
        invalidate()
    }

    @Suppress("unused")
    fun setStartInterpolator(startInterpolator: Interpolator) {
        if (startInterpolator is LinearInterpolator) {
            mStartInterpolator = startInterpolator
        }
    }

    fun setCircleCount(count: Int) {
        mCircleCount = count
        mNavigatorHelper.totalCount = mCircleCount
    }

    @Suppress("unused")
    fun setTouchable(touchable: Boolean) {
        mTouchable = touchable
    }

    @Suppress("unused")
    fun setFollowTouch(followTouch: Boolean) {
        mFollowTouch = followTouch
    }

    @Suppress("unused")
    fun setSkimOver(skimOver: Boolean) {
        mNavigatorHelper.setSkimOver(skimOver)
    }

    fun setCircleClickListener(circleClickListener: OnCircleClickListener?) {
        if (!mTouchable) {
            mTouchable = true
        }
        mCircleClickListener = circleClickListener
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        if (mFollowTouch) {
            val radius =
                mMinRadius + (mMaxRadius - mMinRadius) * mStartInterpolator.getInterpolation(
                    enterPercent
                )
            mCircleRadiusArray.put(index, radius)
            invalidate()
        }
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        if (mFollowTouch) {
            val radius =
                mMaxRadius + (mMinRadius - mMaxRadius) * mStartInterpolator.getInterpolation(
                    leavePercent
                )
            mCircleRadiusArray.put(index, radius)
            invalidate()
        }
    }

    override fun onSelected(index: Int, totalCount: Int) {
        if (!mFollowTouch) {
            mCircleRadiusArray.put(index, mMaxRadius.toFloat())
            invalidate()
        }
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        if (!mFollowTouch) {
            mCircleRadiusArray.put(index, mMinRadius.toFloat())
            invalidate()
        }
    }

    interface OnCircleClickListener {
        fun onClick(index: Int)
    }

    init {
        init(context)
    }
}
