package com.loitpcore.function.simpleFingerGestures

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.loitpcore.function.simpleFingerGestures.GestureAnalyser.GestureType

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SimpleFingerGestures : OnTouchListener {

    companion object {
        // Will see if these need to be used. For now just returning duration in milliS
        @Suppress("unused")
        const val GESTURE_SPEED_SLOW: Long = 1500

        @Suppress("unused")
        const val GESTURE_SPEED_MEDIUM: Long = 1000

        @Suppress("unused")
        const val GESTURE_SPEED_FAST: Long = 500

        @Suppress("unused")
        private const val TAG = "SimpleFingerGestures"
    }

    interface OnFingerGestureListener {
        fun onSwipeUp(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean
        fun onSwipeDown(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean
        fun onSwipeLeft(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean
        fun onSwipeRight(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean
        fun onPinch(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean
        fun onUnpinch(fingers: Int, gestureDuration: Long, gestureDistance: Double): Boolean
        fun onDoubleTap(fingers: Int): Boolean
    }

    var consumeTouchEvents = false
    private var tracking = booleanArrayOf(false, false, false, false, false)
    private var gestureAnalyser = GestureAnalyser()
    private var onFingerGestureListener: OnFingerGestureListener? = null

    constructor() {
        gestureAnalyser = GestureAnalyser()
    }

    @Suppress("unused")
    constructor(
        swipeSlopeIntolerance: Int,
        doubleTapMaxDelayMillis: Int,
        doubleTapMaxDownMillis: Int
    ) {
        gestureAnalyser =
            GestureAnalyser(swipeSlopeIntolerance, doubleTapMaxDelayMillis, doubleTapMaxDownMillis)
    }

    fun setOnFingerGestureListener(listener: OnFingerGestureListener?) {
        onFingerGestureListener = listener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(
        view: View,
        motionEvent: MotionEvent
    ): Boolean {
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                startTracking(0)
                gestureAnalyser.trackGesture(motionEvent)
                return consumeTouchEvents
            }
            MotionEvent.ACTION_UP -> {
                if (tracking[0]) {
                    doCallBack(gestureAnalyser.getGesture(motionEvent))
                }
                stopTracking(0)
                gestureAnalyser.untrackGesture()
                return consumeTouchEvents
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                startTracking(motionEvent.pointerCount - 1)
                gestureAnalyser.trackGesture(motionEvent)
                return consumeTouchEvents
            }
            MotionEvent.ACTION_POINTER_UP -> {
                if (tracking[1]) {
                    doCallBack(gestureAnalyser.getGesture(motionEvent))
                }
                stopTracking(motionEvent.pointerCount - 1)
                gestureAnalyser.untrackGesture()
                return consumeTouchEvents
            }
            MotionEvent.ACTION_CANCEL -> {
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                return consumeTouchEvents
            }
        }
        return consumeTouchEvents
    }

    private fun doCallBack(gestureType: GestureType) {
        when (gestureType.gestureFlag) {
            GestureAnalyser.SWIPE_1_UP -> onFingerGestureListener?.onSwipeUp(
                fingers = 1,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_1_DOWN -> onFingerGestureListener?.onSwipeDown(
                fingers = 1,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_1_LEFT -> onFingerGestureListener?.onSwipeLeft(
                fingers = 1,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_1_RIGHT -> onFingerGestureListener?.onSwipeRight(
                fingers = 1,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_2_UP -> onFingerGestureListener?.onSwipeUp(
                fingers = 2,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_2_DOWN -> onFingerGestureListener?.onSwipeDown(
                fingers = 2,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_2_LEFT -> onFingerGestureListener?.onSwipeLeft(
                fingers = 2,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_2_RIGHT -> onFingerGestureListener?.onSwipeRight(
                fingers = 2,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.PINCH_2 -> onFingerGestureListener?.onPinch(
                fingers = 2,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.UNPINCH_2 -> onFingerGestureListener?.onUnpinch(
                fingers = 2,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_3_UP -> onFingerGestureListener?.onSwipeUp(
                fingers = 3,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_3_DOWN -> onFingerGestureListener?.onSwipeDown(
                fingers = 3,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_3_LEFT -> onFingerGestureListener?.onSwipeLeft(
                fingers = 3,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_3_RIGHT -> onFingerGestureListener?.onSwipeRight(
                fingers = 3,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.PINCH_3 -> onFingerGestureListener?.onPinch(
                fingers = 3,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.UNPINCH_3 -> onFingerGestureListener?.onUnpinch(
                fingers = 3,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_4_UP -> onFingerGestureListener?.onSwipeUp(
                fingers = 4,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_4_DOWN -> onFingerGestureListener?.onSwipeDown(
                fingers = 4,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_4_LEFT -> onFingerGestureListener?.onSwipeLeft(
                fingers = 4,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.SWIPE_4_RIGHT -> onFingerGestureListener?.onSwipeRight(
                fingers = 4,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.PINCH_4 -> onFingerGestureListener?.onPinch(
                fingers = 4,
                gestureDuration = gestureType.gestureDuration,
                gestureDistance = gestureType.gestureDistance
            )
            GestureAnalyser.UNPINCH_4 -> {
                onFingerGestureListener?.let {
                    it.onUnpinch(
                        fingers = 4,
                        gestureDuration = gestureType.gestureDuration,
                        gestureDistance = gestureType.gestureDistance
                    )
                    it.onDoubleTap(fingers = 1)
                }
            }
            GestureAnalyser.DOUBLE_TAP_1 -> onFingerGestureListener?.onDoubleTap(fingers = 1)
        }
    }

    private fun startTracking(nthPointer: Int) {
        for (i in 0..nthPointer) {
            tracking[i] = true
        }
    }

    private fun stopTracking(nthPointer: Int) {
        for (i in nthPointer until tracking.size) {
            tracking[i] = false
        }
    }
}
