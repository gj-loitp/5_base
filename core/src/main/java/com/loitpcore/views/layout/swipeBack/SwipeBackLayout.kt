package com.loitpcore.views.layout.swipeBack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import androidx.core.view.MotionEventCompat
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.loitpcore.R
import com.loitpcore.views.layout.swipeBack.tools.Util.canViewScrollDown
import com.loitpcore.views.layout.swipeBack.tools.Util.canViewScrollLeft
import com.loitpcore.views.layout.swipeBack.tools.Util.canViewScrollRight
import com.loitpcore.views.layout.swipeBack.tools.Util.canViewScrollUp
import com.loitpcore.views.layout.swipeBack.tools.Util.contains
import com.loitpcore.views.layout.swipeBack.tools.Util.findAllScrollViews
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class SwipeBackLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    companion object {
        const val FROM_LEFT = 1
        const val FROM_RIGHT = 1 shl 1
        const val FROM_TOP = 1 shl 2
        const val FROM_BOTTOM = 1 shl 3
    }

    @IntDef(FROM_LEFT, FROM_TOP, FROM_RIGHT, FROM_BOTTOM)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class DirectionMode

    private var mDirectionMode = FROM_LEFT
    private val mDragHelper: ViewDragHelper
    private var mDragContentView: View? = null
    private var innerScrollView: View? = null
    private var mWidth = 0
    private var mHeight = 0
    private val mTouchSlop: Int
    private var swipeBackFactor = 0.5f
    private var swipeBackFraction = 0f
    private var maskAlpha = 125
    var isSwipeFromEdge = false
    private var downX = 0f
    private var downY = 0f
    private var leftOffset = 0
    private var topOffset = 0
    private var autoFinishedVelocityLimit = 2000f
    private var touchedEdge = ViewDragHelper.INVALID_POINTER

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeBackLayout)
        directionMode = typedArray.getInt(R.styleable.SwipeBackLayout_directionMode, mDirectionMode)
        setSwipeBackFactor(
            typedArray.getFloat(
                R.styleable.SwipeBackLayout_swipeBackFactor,
                swipeBackFactor
            )
        )
        setMaskAlpha(typedArray.getInteger(R.styleable.SwipeBackLayout_maskAlpha, maskAlpha))
        isSwipeFromEdge =
            typedArray.getBoolean(R.styleable.SwipeBackLayout_isSwipeFromEdge, isSwipeFromEdge)
        typedArray.recycle()
    }

    fun attachToActivity(activity: Activity) {
        val decorView = activity.window.decorView as ViewGroup
        val decorChild = decorView.getChildAt(0) as ViewGroup
        decorChild.setBackgroundColor(Color.TRANSPARENT)
        decorView.removeView(decorChild)
        addView(decorChild)
        decorView.addView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childCount = childCount
        check(childCount <= 1) { "SwipeBackLayout must contains only one direct child." }
        var defaultMeasuredWidth = 0
        var defaultMeasuredHeight = 0
        val measuredWidth: Int
        val measuredHeight: Int
        if (childCount > 0) {
            measureChildren(widthMeasureSpec, heightMeasureSpec)
            mDragContentView = getChildAt(0)
            mDragContentView?.let {
                defaultMeasuredWidth = it.measuredWidth
                defaultMeasuredHeight = it.measuredHeight
            }
        }
        measuredWidth =
            resolveSize(defaultMeasuredWidth, widthMeasureSpec) + paddingLeft + paddingRight
        measuredHeight =
            resolveSize(defaultMeasuredHeight, heightMeasureSpec) + paddingTop + paddingBottom
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount == 0) {
            return
        }
        val left = paddingLeft + leftOffset
        val top = paddingTop + topOffset

        val measuredWidth = mDragContentView?.measuredWidth ?: 0
        val measuredHeight = mDragContentView?.measuredHeight ?: 0

        val right = left + measuredWidth
        val bottom = top + measuredHeight
        mDragContentView?.layout(left, top, right, bottom)
        if (changed) {
            mWidth = width
            mHeight = height
        }
        innerScrollView = findAllScrollViews(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawARGB(maskAlpha - (maskAlpha * swipeBackFraction).toInt(), 0, 0, 0)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (MotionEventCompat.getActionMasked(ev)) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.rawX
                downY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                innerScrollView?.let { isv ->
                    if (contains(mView = isv, x = downX, y = downY)) {
                        val distanceX = abs(x = ev.rawX - downX)
                        val distanceY = abs(x = ev.rawY - downY)
                        if (mDirectionMode == FROM_LEFT || mDirectionMode == FROM_RIGHT) {
                            if (distanceY > mTouchSlop && distanceY > distanceX) {
                                return super.onInterceptTouchEvent(ev)
                            }
                        } else if (mDirectionMode == FROM_TOP || mDirectionMode == FROM_BOTTOM) {
                            if (distanceX > mTouchSlop && distanceX > distanceY) {
                                return super.onInterceptTouchEvent(ev)
                            }
                        }
                    }
                }
            }
        }
        val handled = mDragHelper.shouldInterceptTouchEvent(ev)
        return handled || super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    fun smoothScrollToX(finalLeft: Int) {
        if (mDragHelper.settleCapturedViewAt(finalLeft, paddingTop)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    fun smoothScrollToY(finalTop: Int) {
        if (mDragHelper.settleCapturedViewAt(paddingLeft, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private inner class DragHelperCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child === mDragContentView
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            leftOffset = paddingLeft
            if (isSwipeEnabled) {
                if (mDirectionMode == FROM_LEFT && !canViewScrollRight(
                        mView = innerScrollView,
                        x = downX,
                        y = downY,
                        defaultValueForNull = false
                    )
                ) {
                    leftOffset = min(max(left, paddingLeft), mWidth)
                } else if (mDirectionMode == FROM_RIGHT && !canViewScrollLeft(
                        mView = innerScrollView,
                        x = downX,
                        y = downY,
                        defaultValueForNull = false
                    )
                ) {
                    leftOffset = min(max(left, -mWidth), paddingRight)
                }
            }
            return leftOffset
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            topOffset = paddingTop
            if (isSwipeEnabled) {
                if (mDirectionMode == FROM_TOP && !canViewScrollUp(
                        mView = innerScrollView,
                        x = downX,
                        y = downY,
                        defaultValueForNull = false
                    )
                ) {
                    topOffset = min(max(top, paddingTop), mHeight)
                } else if (mDirectionMode == FROM_BOTTOM && !canViewScrollDown(
                        mView = innerScrollView,
                        x = downX,
                        y = downY,
                        defaultValueForNull = false
                    )
                ) {
                    topOffset = min(max(top, -mHeight), paddingBottom)
                }
            }
            return topOffset
        }

        @Suppress("NAME_SHADOWING")
        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            var left = left
            var top = top
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            left = abs(left)
            top = abs(top)
            when (mDirectionMode) {
                FROM_LEFT, FROM_RIGHT -> swipeBackFraction = 1.0f * left / mWidth
                FROM_TOP, FROM_BOTTOM -> swipeBackFraction = 1.0f * top / mHeight
            }
            mSwipeBackListener?.onViewPositionChanged(
                mView = mDragContentView,
                swipeBackFraction = swipeBackFraction,
                swipeBackFactor = swipeBackFactor
            )
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            topOffset = 0
            leftOffset = topOffset
            if (!isSwipeEnabled) {
                touchedEdge = ViewDragHelper.INVALID_POINTER
                return
            }
            touchedEdge = ViewDragHelper.INVALID_POINTER
            val isBackToEnd =
                backJudgeBySpeed(xvel = xvel, yvel = yvel) || swipeBackFraction >= swipeBackFactor
            if (isBackToEnd) {
                when (mDirectionMode) {
                    FROM_LEFT -> smoothScrollToX(finalLeft = mWidth)
                    FROM_TOP -> smoothScrollToY(finalTop = mHeight)
                    FROM_RIGHT -> smoothScrollToX(finalLeft = -mWidth)
                    FROM_BOTTOM -> smoothScrollToY(finalTop = -mHeight)
                }
            } else {
                when (mDirectionMode) {
                    FROM_LEFT, FROM_RIGHT -> smoothScrollToX(finalLeft = paddingLeft)
                    FROM_BOTTOM, FROM_TOP -> smoothScrollToY(finalTop = paddingTop)
                }
            }
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (state == ViewDragHelper.STATE_IDLE) {
                if (swipeBackFraction == 0f) {
                    mSwipeBackListener?.onViewSwipeFinished(mView = mDragContentView, isEnd = false)
                } else if (swipeBackFraction == 1f) {
                    mSwipeBackListener?.onViewSwipeFinished(mView = mDragContentView, isEnd = true)
                }
            }
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return mWidth
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return mHeight
        }

        override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
            super.onEdgeTouched(edgeFlags, pointerId)
            touchedEdge = edgeFlags
        }
    }

    fun finish() {
        (context as Activity).finish()
    }

    private val isSwipeEnabled: Boolean
        get() {
            if (isSwipeFromEdge) {
                when (mDirectionMode) {
                    FROM_LEFT -> return touchedEdge == ViewDragHelper.EDGE_LEFT
                    FROM_TOP -> return touchedEdge == ViewDragHelper.EDGE_TOP
                    FROM_RIGHT -> return touchedEdge == ViewDragHelper.EDGE_RIGHT
                    FROM_BOTTOM -> return touchedEdge == ViewDragHelper.EDGE_BOTTOM
                }
            }
            return true
        }

    private fun backJudgeBySpeed(xvel: Float, yvel: Float): Boolean {
        when (mDirectionMode) {
            FROM_LEFT -> return xvel > autoFinishedVelocityLimit
            FROM_TOP -> return yvel > autoFinishedVelocityLimit
            FROM_RIGHT -> return xvel < -autoFinishedVelocityLimit
            FROM_BOTTOM -> return yvel < -autoFinishedVelocityLimit
        }
        return false
    }

    @Suppress("NAME_SHADOWING")
    fun setSwipeBackFactor(@FloatRange(from = 0.0, to = 1.0) swipeBackFactor: Float) {
        var swipeBackFactor = swipeBackFactor
        if (swipeBackFactor > 1) {
            swipeBackFactor = 1f
        } else if (swipeBackFactor < 0) {
            swipeBackFactor = 0f
        }
        this.swipeBackFactor = swipeBackFactor
    }

    fun getSwipeBackFactor(): Float {
        return swipeBackFactor
    }

    @Suppress("NAME_SHADOWING")
    fun setMaskAlpha(@IntRange(from = 0, to = 255) maskAlpha: Int) {
        var maskAlpha = maskAlpha
        if (maskAlpha > 255) {
            maskAlpha = 255
        } else if (maskAlpha < 0) {
            maskAlpha = 0
        }
        this.maskAlpha = maskAlpha
    }

    fun getMaskAlpha(): Int {
        return maskAlpha
    }

    open var directionMode: Int
        get() = mDirectionMode
        set(direction) {
            mDirectionMode = direction
            mDragHelper.setEdgeTrackingEnabled(direction)
        }

    private var mSwipeBackListener: OnSwipeBackListener? = null

    fun setSwipeBackListener(mSwipeBackListener: OnSwipeBackListener?) {
        this.mSwipeBackListener = mSwipeBackListener
    }

    interface OnSwipeBackListener {
        fun onViewPositionChanged(mView: View?, swipeBackFraction: Float, swipeBackFactor: Float)
        fun onViewSwipeFinished(mView: View?, isEnd: Boolean)
    }

    init {
        this.setWillNotDraw(false)
        mDragHelper = ViewDragHelper.create(this, 1f, DragHelperCallback())
        mDragHelper.setEdgeTrackingEnabled(mDirectionMode)
        mTouchSlop = mDragHelper.touchSlop

        val defaultSwipeBackListener: OnSwipeBackListener = object : OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
                invalidate()
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                }
            }
        }
        setSwipeBackListener(defaultSwipeBackListener)
        init(context, attrs)
    }
}
