package vn.loitp.up.a.func.viewDragHelperSimple

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.core.view.MotionEventCompat
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.loitp.core.ext.screenHeight
import com.loitp.core.ext.screenWidth
import vn.loitp.R

class VDHView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var headerView: View
    private lateinit var bodyView: View
    private lateinit var mViewDragHelper: ViewDragHelper
    private var mAutoBackViewX = 0
    private var mAutoBackViewY = 0

    // private float mInitialMotionX;
    // private float mInitialMotionY;
    private var mDragRange = 0
    private var mDragOffset = 0f
    var isEnableAlpha = true
    var isEnableRevertMaxSize = true
    var isMinimized = false // header view is scaled at least 1 = false
    private var sizeWHeaderViewOriginal = 0
    private var sizeHHeaderViewOriginal = 0
    private var sizeWHeaderViewMin = 0
    private var sizeHHeaderViewMin = 0
    private var newSizeWHeaderView = 0
    private var newSizeHHeaderView = 0
    private var mCenterY = 0
    private var mCenterX = 0
    private var screenW = 0
    private var screenH = 0

    interface Callback {
        fun onStateChange(state: State)
        fun onPartChange(part: Part)
        fun onViewPositionChanged(left: Int, top: Int, dragOffset: Float)
        fun onOverScroll(state: State?, part: Part?)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    private fun initView() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback)
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        screenW = screenWidth
        screenH = screenHeight

        headerView = findViewById(R.id.headerView)
        bodyView = findViewById(R.id.bodyView)

        headerView.post {
            sizeWHeaderViewOriginal = headerView.measuredWidth
            sizeHHeaderViewOriginal = headerView.measuredHeight
            sizeWHeaderViewMin = sizeWHeaderViewOriginal / 2
            sizeHHeaderViewMin = sizeHHeaderViewOriginal / 2
        }
    }

    private val mCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)

            mDragOffset = if (mDragOffset == top.toFloat() / mDragRange) {
                return
            } else {
                top.toFloat() / mDragRange
            }
            if (mDragOffset > 1) {
                mDragOffset = 1f
            }
            if (mDragOffset < 0) {
                mDragOffset = 0f
            }

            callback?.onViewPositionChanged(left, top, mDragOffset)

            val x = 0
            val y = headerView.height + top
            bodyView.layout(x, y, x + bodyView.measuredWidth, y + bodyView.measuredHeight)

            if (isEnableAlpha) {
                bodyView.alpha = 1 - mDragOffset / 2
            } else {
                if (bodyView.alpha != 1f) {
                    bodyView.alpha = 1f
                }
            }

            if (isMinimized) {
                if (isEnableRevertMaxSize) {
                    headerView.pivotX = headerView.width / 2f
                    headerView.pivotY = headerView.height.toFloat()
                    headerView.scaleX = 1 - mDragOffset / 2
                    headerView.scaleY = 1 - mDragOffset / 2
                }
            } else {
                headerView.pivotX = headerView.width / 2f
                headerView.pivotY = headerView.height.toFloat()
                headerView.scaleX = 1 - mDragOffset / 2
                headerView.scaleY = 1 - mDragOffset / 2
            }
            newSizeWHeaderView = (sizeWHeaderViewOriginal * headerView.scaleX).toInt()
            newSizeHHeaderView = (sizeHHeaderViewOriginal * headerView.scaleY).toInt()
            mCenterX = left + sizeWHeaderViewOriginal / 2
            mCenterY = top + newSizeHHeaderView / 2 + sizeHHeaderViewOriginal - newSizeHHeaderView

            when (mDragOffset) {
                0f -> {
                    // top_left, top, top_right
                    when {
                        left <= -headerView.width / 2 -> {
                            changeState(State.TOP_LEFT)
                        }
                        left >= headerView.width / 2 -> {
                            changeState(State.TOP_RIGHT)
                        }
                        else -> {
                            changeState(State.TOP)
                        }
                    }
                }
                1f -> {
                    // bottom_left, bottom, bottom_right
                    when {
                        left <= -headerView.width / 2 -> {
                            changeState(State.BOTTOM_LEFT)
                        }
                        left >= headerView.width / 2 -> {
                            changeState(State.BOTTOM_RIGHT)
                        }
                        else -> {
                            changeState(State.BOTTOM)
                        }
                    }
                    isMinimized = true
                }
                else -> {
                    // mid_left, mid, mid_right
                    when {
                        left <= -headerView.width / 2 -> {
                            changeState(State.MID_LEFT)
                        }
                        left >= headerView.width / 2 -> {
                            changeState(State.MID_RIGHT)
                        }
                        else -> {
                            changeState(State.MID)
                        }
                    }
                }
            }
            if (mCenterY < screenH / 2) {
                if (mCenterX < screenW / 2) {
                    changePart(Part.TOP_LEFT)
                } else {
                    changePart(Part.TOP_RIGHT)
                }
            } else {
                if (mCenterX < screenW / 2) {
                    changePart(Part.BOTTOM_LEFT)
                } else {
                    changePart(Part.BOTTOM_RIGHT)
                }
            }
        }

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return headerView === child
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val minY: Int = if (isEnableRevertMaxSize) {
                -child.height / 2
            } else {
                -sizeHHeaderViewMin * 3 / 2
            }
            val scaledY = child.scaleY
            val sizeHScaled = (scaledY * child.height).toInt()
            val maxY = height - sizeHScaled * 3 / 2
            return when {
                top <= minY -> {
                    minY
                }
                top > maxY -> {
                    maxY
                }
                else -> {
                    top
                }
            }
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val minX = -child.width / 2
            val maxX = width - child.width / 2
            return when {
                left <= minX -> {
                    minX
                }
                left > maxX -> {
                    maxX
                }
                else -> {
                    left
                }
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (releasedChild === headerView) {
                mViewDragHelper.settleCapturedViewAt(mAutoBackViewX, mAutoBackViewY)
            }
            invalidate()
        }

        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {}
    }

    private fun changeState(newState: State) {
        if (state != newState) {
            state = newState
            state?.let {
                callback?.onStateChange(it)
            }
        }
    }

    private fun changePart(newPart: Part) {
        if (part != newPart) {
            part = newPart
            part?.let {
                callback?.onPartChange(it)
            }
        }
    }

    override fun computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mViewDragHelper.shouldInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper.processTouchEvent(event)
        val x = event.x
        val y = event.y
        val isViewUnder = mViewDragHelper.isViewUnder(headerView, x.toInt(), y.toInt())
        when (event.action and MotionEventCompat.ACTION_MASK) {
            MotionEvent.ACTION_UP -> {
                if (state == State.TOP_LEFT || state == State.TOP_RIGHT || state == State.BOTTOM_LEFT || state == State.BOTTOM_RIGHT) {
                    callback?.onOverScroll(state = state, part = part)
                } else {
                    if (part == Part.BOTTOM_LEFT) {
                        minimizeBottomLeft()
                    } else if (part == Part.BOTTOM_RIGHT) {
                        minimizeBottomRight()
                    } else if (part == Part.TOP_LEFT) {
                        if (isEnableRevertMaxSize) {
                            maximize()
                        } else {
                            if (isMinimized) {
                                minimizeTopLeft()
                            }
                        }
                    } else if (part == Part.TOP_RIGHT) {
                        if (isEnableRevertMaxSize) {
                            maximize()
                        } else {
                            if (isMinimized) {
                                minimizeTopRight()
                            }
                        }
                    }
                }
            }
        }
        return isViewUnder
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        super.onLayout(changed, l, t, r, b)
        mDragRange = height - headerView.height
        mAutoBackViewX = headerView.left
        mAutoBackViewY = headerView.top
    }

    enum class State {
        TOP, TOP_LEFT, TOP_RIGHT, BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT, MID, MID_LEFT, MID_RIGHT
    }

    enum class Part {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    private var state: State? = null
    private var part: Part? = null

    fun maximize() {
        if (isEnableRevertMaxSize) {
            smoothSlideTo(0, 0)
        }
    }

    fun minimizeBottomLeft() {
        val posX = width - sizeWHeaderViewOriginal - sizeWHeaderViewMin / 2
        val posY = height - sizeHHeaderViewOriginal
        smoothSlideTo(posX, posY)
    }

    fun minimizeBottomRight() {
        val posX = width - sizeWHeaderViewOriginal + sizeWHeaderViewMin / 2
        val posY = height - sizeHHeaderViewOriginal
        smoothSlideTo(posX, posY)
    }

    fun minimizeTopRight() {
        if (isEnableRevertMaxSize) {
//            Log.e(TAG, "Error: cannot minimizeTopRight because isEnableRevertMaxSize is true")
            return
        }
        if (!isMinimized) {
//            Log.e(TAG, "Error: cannot minimizeTopRight because isMinimized is false. This function only works if the header view is scrolled BOTTOM")
            return
        }
        val posX = screenW - sizeWHeaderViewMin * 3 / 2
        val posY = -sizeHHeaderViewMin
        smoothSlideTo(posX, posY)
    }

    fun minimizeTopLeft() {
        if (isEnableRevertMaxSize) {
//            Log.e(TAG, "Error: cannot minimizeTopRight because isEnableRevertMaxSize is true")
            return
        }
        if (!isMinimized) {
//            Log.e(TAG, "Error: cannot minimizeTopRight because isMinimized is false. This function only works if the header view is scrolled BOTTOM")
            return
        }
        val posX = -sizeWHeaderViewMin / 2
        val posY = -sizeHHeaderViewMin
        smoothSlideTo(positionX = posX, positionY = posY)
    }

    fun smoothSlideTo(
        positionX: Int,
        positionY: Int
    ) {
        if (mViewDragHelper.smoothSlideViewTo(headerView, positionX, positionY)) {
            ViewCompat.postInvalidateOnAnimation(this)
            postInvalidate()
        }
    }

    fun toggleShowHideHeaderView() {
        if (headerView.visibility == View.VISIBLE) {
            headerView.visibility = View.INVISIBLE
        } else {
            headerView.visibility = View.VISIBLE
        }
    }

    fun toggleShowHideBodyView() {
        if (bodyView.visibility == View.VISIBLE) {
            bodyView.visibility = View.INVISIBLE
        } else {
            bodyView.visibility = View.VISIBLE
        }
    }

    fun onPause() {
        if (!isEnableRevertMaxSize) {
            headerView.visibility = View.INVISIBLE
            bodyView.visibility = View.INVISIBLE
            minimizeBottomRight()
        }
    }

    fun dissappear() {}
    fun appear() {}

    init {
        initView()
    }
}
