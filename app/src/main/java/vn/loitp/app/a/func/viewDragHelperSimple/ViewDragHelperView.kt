package vn.loitp.app.a.func.viewDragHelperSimple

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

class ViewDragHelperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var mDragView: View? = null
    private var mAutoBackView: View? = null
    private var mEdgeTrackerView: View? = null
    private lateinit var mViewDragHelper: ViewDragHelper
    private var mAutoBackViewX = 0
    private var mAutoBackViewY = 0

    private fun initView() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, callback)
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    private val callback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return mDragView === child || mAutoBackView === child
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val rangeX = width - child.width
            return when {
                left <= 0 -> {
                    0
                }
                left > rangeX -> {
                    rangeX
                }
                else -> {
                    left
                }
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val rangeY = height - child.height
            return when {
                top <= 0 -> {
                    0
                }
                top > rangeY -> {
                    rangeY
                }
                else -> {
                    top
                }
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (releasedChild === mAutoBackView) {
                mViewDragHelper.settleCapturedViewAt(mAutoBackViewX, mAutoBackViewY)
            }
            invalidate()
        }

        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            mEdgeTrackerView?.let {
                mViewDragHelper.captureChildView(it, pointerId)
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mViewDragHelper.shouldInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mDragView = getChildAt(0)
        mAutoBackView = getChildAt(1)
        mEdgeTrackerView = getChildAt(2)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        mAutoBackView?.let {
            mAutoBackViewX = it.left
            mAutoBackViewY = it.top
        }
    }

    init {
        initView()
    }
}
