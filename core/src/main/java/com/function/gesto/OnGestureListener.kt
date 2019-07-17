package com.function.gesto

/**
 * Created by www.muathu@gmail.com on 1/16/2018.
 */

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnGestureListener
/**
 * Required constructor.
 *
 * @param context Context to use.
 */
(context: Context) : View.OnTouchListener {
    //GestureDetector global reference
    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(motionEvent)
    }

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


        override fun onSingleTapUp(e: MotionEvent): Boolean {
            onClick()
            return super.onSingleTapUp(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick()
            return super.onDoubleTap(e)
        }

        override fun onLongPress(e: MotionEvent) {
            onLongClick()
            super.onLongPress(e)
        }

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
    }

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}

    open fun onSwipeTop() {}

    open fun onSwipeBottom() {}

    open fun onClick() {

    }

    open fun onDoubleClick() {

    }

    open fun onLongClick() {

    }
}