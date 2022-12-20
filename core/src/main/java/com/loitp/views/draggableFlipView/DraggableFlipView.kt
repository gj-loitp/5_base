package com.loitp.views.draggableFlipView

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.loitpcore.R
import kotlin.math.abs

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DraggableFlipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(
    context,
    attrs,
    defStyleAttr
),
    DragGestureDetector.DragGestureListener {

    companion object {
        private const val DRAG_THRESHOLD_PARAM = 50.0f
        private const val DEFAULT_VALUE = 0
        private const val DEFAULT_DRAGGABLE_VALUE = 50
        private const val DEFAULT_DRAG_DETECT_VALUE = 7
    }

    private var mDragGestureDetector: DragGestureDetector? = null
    private var isAnimation = false
    private var isDragging = false
    private var mAngle = 0
    private var mDraggableAngle = 0
    private var mDragDetectAngle = 0
    private var mIsReverse = false
    private var mFlipListener: FlipListener? = null
    private var mFrontLayout: RelativeLayout? = null
    private var mBackLayout: RelativeLayout? = null

    private enum class RotateDirection(val value: Int) {
        RIGHT(1), LEFT(-1);
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mFrontLayout = RelativeLayout(context)
        mFrontLayout?.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        mBackLayout = RelativeLayout(context)
        mBackLayout?.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        this.addView(mFrontLayout)
        this.addView(mBackLayout)

        mBackLayout?.visibility = INVISIBLE
        mFlipListener = FlipListener(
            mFrontView = mFrontLayout,
            mBackView = mBackLayout,
            mParentView = this
        )

        mDragGestureDetector = DragGestureDetector(this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DraggableFlipView)
        LayoutInflater.from(context).inflate(
            typedArray.getResourceId(R.styleable.DraggableFlipView_frontView, DEFAULT_VALUE),
            mFrontLayout
        )
        LayoutInflater.from(context).inflate(
            typedArray.getResourceId(R.styleable.DraggableFlipView_backView, DEFAULT_VALUE),
            mBackLayout
        )
        mDraggableAngle = typedArray.getInteger(
            R.styleable.DraggableFlipView_draggableAngle,
            DEFAULT_DRAGGABLE_VALUE
        )
        mDragDetectAngle = typedArray.getInteger(
            R.styleable.DraggableFlipView_dragDetectAngle,
            DEFAULT_DRAG_DETECT_VALUE
        )

        typedArray.recycle()
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent): Boolean {
        if (mDragGestureDetector == null) {
            return false
        }
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                mDragGestureDetector?.let { dgd ->

                    dgd.touchPoint?.x?.let { x ->
                        dgd.touchPoint?.y?.let { y ->

                            if (abs(motionEvent.x - x) > DRAG_THRESHOLD_PARAM ||
                                abs(motionEvent.y - y) > DRAG_THRESHOLD_PARAM
                            ) {
                                mDragGestureDetector?.setPointMap(motionEvent)
                                return true
                            }
                        }
                    }
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> return true
        }
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragGestureDetector?.onTouchEvent(event)
        return true
    }

    override fun onDragGestureListener(dragGestureDetector: DragGestureDetector?, action: Int) {
        if (isAnimation) {
            return
        }
        if (action == MotionEvent.ACTION_UP) {
            if (mAngle >= mDragDetectAngle) {
                startAutoRotateAnimation(RotateDirection.RIGHT)
            } else if (mAngle < -mDragDetectAngle) {
                startAutoRotateAnimation(RotateDirection.LEFT)
            }
            return
        }
        dragGestureDetector?.let {
            mAngle = if (it.deltaX - it.prevDeltaX > 0) {
                ++mAngle
            } else {
                --mAngle
            }
        }

        if (abs(mAngle) > mDragDetectAngle) {
            isDragging = true
        }
        if (isDragging) {
            this.rotationY = mAngle.toFloat()
        }
        if (mAngle >= mDraggableAngle) {
            startAutoRotateAnimation(RotateDirection.RIGHT)
        } else if (mAngle < -mDraggableAngle) {
            startAutoRotateAnimation(RotateDirection.LEFT)
        }
    }

    private fun startAutoRotateAnimation(rotateDirection: RotateDirection) {
        isAnimation = true
        if (mIsReverse) {
            mFlipListener?.reverse()
        } else {
            mIsReverse = true
        }
        mFlipListener?.setRotateDirection(rotateDirection.value)
        val flipAnimator = ValueAnimator.ofFloat(0f, 1f)
        flipAnimator.addUpdateListener(mFlipListener)
        flipAnimator.start()
        flipAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                mAngle = 0
                isAnimation = false
                isDragging = false
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    init {
        init(context, attrs)
    }
}
