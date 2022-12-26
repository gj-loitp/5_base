package com.loitp.views.layout.youtube

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.loitp.R
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
class YoutubeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ViewGroup(context, attrs, defStyle) {

    //    private val logTag = javaClass.simpleName
    private val mDragHelper: ViewDragHelper
    private var mHeaderView: View? = null
    private var mDescView: View? = null
    private var mInitialMotionX = 0f
    private var mInitialMotionY = 0f
    private var mDragRange = 0
    private var mTop = 0
    private var mDragOffset = 0f

    override fun onFinishInflate() {
        super.onFinishInflate()
        mHeaderView = findViewById(R.id.view_header)
        mDescView = findViewById(R.id.view_desc)
    }

    fun maximize() {
        smoothSlideTo(0f)
    }

    private fun smoothSlideTo(slideOffset: Float): Boolean {
        val topBound = paddingTop
        val y = (topBound + slideOffset * mDragRange).toInt()
        mHeaderView?.let {
            if (mDragHelper.smoothSlideViewTo(it, it.left, y)) {
                ViewCompat.postInvalidateOnAnimation(this)
                return true
            }
        }

        return false
    }

    private inner class DragHelperCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child === mHeaderView
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            mTop = top
            mDragOffset = top.toFloat() / mDragRange
            when (mDragOffset) {
                0f -> {
                    isPositionTop
                }
                1f -> {
                    isPositionBottom
                }
                else -> {
                    isPositionMid
                }
            }
            mHeaderView?.apply {
                pivotX = width.toFloat()
                pivotY = height.toFloat()
                scaleX = 1 - mDragOffset / 2
                scaleY = 1 - mDragOffset / 2
//                alpha = 1 - mDragOffset
            }

            requestLayout()
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            var top = paddingTop
            if (yvel > 0 || yvel == 0f && mDragOffset > 0.5f) {
                top += mDragRange
            }
            mDragHelper.settleCapturedViewAt(releasedChild.left, top)
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return mDragRange
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val topBound = paddingTop
            var bottomBound = 0
            mHeaderView?.let {
                bottomBound = height - it.height - it.paddingBottom
            }
            return min(
                a = max(a = top, b = topBound),
                b = bottomBound
            )
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val leftBound = paddingLeft
            var rightBound = 0
            mHeaderView?.let {
                rightBound = width - it.width
            }
            return min(
                a = max(a = left, b = leftBound),
                b = rightBound
            )
        }
    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(ev)
        if (action != MotionEvent.ACTION_DOWN) {
            mDragHelper.cancel()
            return super.onInterceptTouchEvent(ev)
        }
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel()
            return false
        }
        val x = ev.x
        val y = ev.y
        var interceptTap = false
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mInitialMotionX = x
                mInitialMotionY = y
                interceptTap = mDragHelper.isViewUnder(mHeaderView, x.toInt(), y.toInt())
            }
            MotionEvent.ACTION_MOVE -> {
                val adx = abs(x - mInitialMotionX)
                val ady = abs(y - mInitialMotionY)
                val slop = mDragHelper.touchSlop
                if (ady > slop && adx > ady) {
                    mDragHelper.cancel()
                    return false
                }
            }
        }
        return mDragHelper.shouldInterceptTouchEvent(ev) || interceptTap
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(ev)
        val action = ev.action
        val x = ev.x
        val y = ev.y
        val isHeaderViewUnder = mDragHelper.isViewUnder(mHeaderView, x.toInt(), y.toInt())
        when (action and MotionEventCompat.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mInitialMotionX = x
                mInitialMotionY = y
            }
            MotionEvent.ACTION_UP -> {
                if (mDragOffset < 0.5f) {
                    smoothSlideTo(0f)
                } else {
                    smoothSlideTo(1f)
                }
            }
        }
        return isHeaderViewUnder && isViewHit(mHeaderView, x.toInt(), y.toInt()) || isViewHit(
            mDescView,
            x.toInt(),
            y.toInt()
        )
    }

    private fun isViewHit(
        view: View?,
        x: Int,
        y: Int
    ): Boolean {
        if (view == null) {
            return false
        }
        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)
        val parentLocation = IntArray(2)
        getLocationOnScreen(parentLocation)
        val screenX = parentLocation[0] + x
        val screenY = parentLocation[1] + y
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.width && screenY >= viewLocation[1] && screenY < viewLocation[1] + view.height
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        val maxHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
            resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
        )
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        mHeaderView?.let {
            mDragRange = height - it.height

            it.layout(
                0,
                mTop,
                r,
                mTop + it.measuredHeight
            )
            mDescView?.layout(
                0,
                mTop + it.measuredHeight,
                r,
                mTop + b
            )
        }
    }

    private enum class STATE {
        TOP, BOTTOM, MID
    }

    private var state: STATE? = null

    private val isPositionTop: Unit
        get() {
            state = STATE.TOP
        }

    private val isPositionBottom: Unit
        get() {
            state = STATE.BOTTOM
        }

    private val isPositionMid: Unit
        get() {
            state = STATE.MID
        }

    init {
        mDragHelper = ViewDragHelper.create(this, 1f, DragHelperCallback())
    }
}
