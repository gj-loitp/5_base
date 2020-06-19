package com.views.viewpager.swipeout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.core.view.MotionEventCompat
import androidx.viewpager.widget.ViewPager

/**
 * Created by loitp on 7/20/2018.
 */

class LSwipeOutViewPager : ViewPager {
    internal var mStartDragX: Float = 0.toFloat()
    internal var mOnSwipeOutListener: OnSwipeOutListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setOnSwipeOutListener(listener: OnSwipeOutListener) {
        mOnSwipeOutListener = listener
    }

    private fun onSwipeOutAtStart() {
        mOnSwipeOutListener?.onSwipeOutAtStart()
    }

    private fun onSwipeOutAtEnd() {
        mOnSwipeOutListener?.onSwipeOutAtEnd()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action and MotionEventCompat.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> mStartDragX = ev.x
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        adapter?.let {
            if (currentItem == 0 || currentItem == it.count - 1) {
                val action = ev.action
                val x = ev.x
                when (action and MotionEventCompat.ACTION_MASK) {
                    MotionEvent.ACTION_MOVE -> {
                    }
                    MotionEvent.ACTION_UP -> {
                        if (currentItem == 0 && x > mStartDragX) {
                            onSwipeOutAtStart()
                        }
                        if (currentItem == it.count - 1 && x < mStartDragX) {
                            onSwipeOutAtEnd()
                        }
                    }
                }
            } else {
                mStartDragX = 0f
            }
        }
        return super.onTouchEvent(ev)

    }

    interface OnSwipeOutListener {
        fun onSwipeOutAtStart()

        fun onSwipeOutAtEnd()
    }
}
