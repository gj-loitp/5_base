package com.loitpcore.function.simpleFingerGestures

import android.os.SystemClock
import android.view.MotionEvent
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class GestureAnalyser @JvmOverloads constructor(
    swipeSlopeIntolerance: Int = 3,
    doubleTapMaxDelayMillis: Int = 500,
    doubleTapMaxDownMillis: Int = 100
) {

    inner class GestureType {
        var gestureFlag = 0
        var gestureDuration: Long = 0
        var gestureDistance = 0.0
    }

    companion object {
        // Finished gestures flags
        const val SWIPE_1_UP = 11
        const val SWIPE_1_DOWN = 12
        const val SWIPE_1_LEFT = 13
        const val SWIPE_1_RIGHT = 14
        const val SWIPE_2_UP = 21
        const val SWIPE_2_DOWN = 22
        const val SWIPE_2_LEFT = 23
        const val SWIPE_2_RIGHT = 24
        const val SWIPE_3_UP = 31
        const val SWIPE_3_DOWN = 32
        const val SWIPE_3_LEFT = 33
        const val SWIPE_3_RIGHT = 34
        const val SWIPE_4_UP = 41
        const val SWIPE_4_DOWN = 42
        const val SWIPE_4_LEFT = 43
        const val SWIPE_4_RIGHT = 44
        const val PINCH_2 = 25
        const val UNPINCH_2 = 26
        const val PINCH_3 = 35
        const val UNPINCH_3 = 36
        const val PINCH_4 = 45
        const val UNPINCH_4 = 46
        const val DOUBLE_TAP_1 = 107
        @Suppress("unused")
        const val SWIPING_1_UP = 101
        @Suppress("unused")
        const val SWIPING_1_DOWN = 102
        @Suppress("unused")
        const val SWIPING_1_LEFT = 103
        @Suppress("unused")
        const val SWIPING_1_RIGHT = 104
        @Suppress("unused")
        const val SWIPING_2_UP = 201
        @Suppress("unused")
        const val SWIPING_2_DOWN = 202
        @Suppress("unused")
        const val SWIPING_2_LEFT = 203
        @Suppress("unused")
        const val SWIPING_2_RIGHT = 204
        @Suppress("unused")
        const val PINCHING = 205
        @Suppress("unused")
        const val UNPINCHING = 206
        @Suppress("unused")
        private const val TAG = "GestureAnalyser"
    }

    private val initialX = DoubleArray(5)
    private val initialY = DoubleArray(5)
    private val finalX = DoubleArray(5)
    private val finalY = DoubleArray(5)
    private val currentX = DoubleArray(5)
    private val currentY = DoubleArray(5)
    private val delX = DoubleArray(5)
    private val delY = DoubleArray(5)
    private var numFingers = 0
    private var initialT: Long = 0
    private var finalT: Long = 0
    private var currentT: Long = 0
    private var prevInitialT: Long = 0
    private var prevFinalT: Long = 0
    private var swipeSlopeIntolerance = 3
    private val doubleTapMaxDelayMillis: Long
    private val doubleTapMaxDownMillis: Long

    init {
        this.swipeSlopeIntolerance = swipeSlopeIntolerance
        this.doubleTapMaxDownMillis = doubleTapMaxDownMillis.toLong()
        this.doubleTapMaxDelayMillis = doubleTapMaxDelayMillis.toLong()
    }

    fun trackGesture(ev: MotionEvent) {
        val n = ev.pointerCount
        for (i in 0 until n) {
            initialX[i] = ev.getX(i).toDouble()
            initialY[i] = ev.getY(i).toDouble()
        }
        numFingers = n
        initialT = SystemClock.uptimeMillis()
    }

    fun untrackGesture() {
        numFingers = 0
        prevFinalT = SystemClock.uptimeMillis()
        prevInitialT = initialT
    }

    fun getGesture(motionEvent: MotionEvent): GestureType {
        var averageDistance = 0.0
        for (i in 0 until numFingers) {
            finalX[i] = motionEvent.getX(i).toDouble()
            finalY[i] = motionEvent.getY(i).toDouble()
            delX[i] = finalX[i] - initialX[i]
            delY[i] = finalY[i] - initialY[i]
            averageDistance += sqrt(
                x = (finalX[i] - initialX[i]).pow(2.0) + (finalY[i] - initialY[i]).pow(
                    2.0
                )
            )
        }
        averageDistance /= numFingers.toDouble()
        finalT = SystemClock.uptimeMillis()

        val gestureType = GestureType()
        gestureType.gestureFlag = calcGesture()
        gestureType.gestureDuration = finalT - initialT
        gestureType.gestureDistance = averageDistance
        return gestureType
    }

    @Suppress("unused")
    fun getOngoingGesture(motionEvent: MotionEvent): Int {
        for (i in 0 until numFingers) {
            currentX[i] = motionEvent.getX(i).toDouble()
            currentY[i] = motionEvent.getY(i).toDouble()
            delX[i] = finalX[i] - initialX[i]
            delY[i] = finalY[i] - initialY[i]
        }
        currentT = SystemClock.uptimeMillis()
        return calcGesture()
    }

    private fun calcGesture(): Int {
        if (isDoubleTap) {
            return DOUBLE_TAP_1
        }
        if (numFingers == 1) {
            if (-delY[0] > swipeSlopeIntolerance * abs(delX[0])) {
                return SWIPE_1_UP
            }
            if (delY[0] > swipeSlopeIntolerance * abs(delX[0])) {
                return SWIPE_1_DOWN
            }
            if (-delX[0] > swipeSlopeIntolerance * abs(delY[0])) {
                return SWIPE_1_LEFT
            }
            if (delX[0] > swipeSlopeIntolerance * abs(delY[0])) {
                return SWIPE_1_RIGHT
            }
        }
        if (numFingers == 2) {
            if (-delY[0] > swipeSlopeIntolerance * abs(delX[0]) && -delY[1] > swipeSlopeIntolerance * abs(
                    delX[1]
                )
            ) {
                return SWIPE_2_UP
            }
            if (delY[0] > swipeSlopeIntolerance * abs(delX[0]) && delY[1] > swipeSlopeIntolerance * abs(
                    delX[1]
                )
            ) {
                return SWIPE_2_DOWN
            }
            if (-delX[0] > swipeSlopeIntolerance * abs(delY[0]) && -delX[1] > swipeSlopeIntolerance * abs(
                    delY[1]
                )
            ) {
                return SWIPE_2_LEFT
            }
            if (delX[0] > swipeSlopeIntolerance * abs(delY[0]) && delX[1] > swipeSlopeIntolerance * abs(
                    delY[1]
                )
            ) {
                return SWIPE_2_RIGHT
            }
            if (finalFingDist(fingNum1 = 0, fingNum2 = 1) > 2 * initialFingDist(
                    fingNum1 = 0,
                    fingNum2 = 1
                )
            ) {
                return UNPINCH_2
            }
            if (finalFingDist(fingNum1 = 0, fingNum2 = 1) < 0.5 * initialFingDist(
                    fingNum1 = 0,
                    fingNum2 = 1
                )
            ) {
                return PINCH_2
            }
        }
        if (numFingers == 3) {
            if (-delY[0] > swipeSlopeIntolerance * abs(delX[0]) &&
                -delY[1] > swipeSlopeIntolerance * abs(delX[1]) &&
                -delY[2] > swipeSlopeIntolerance * abs(delX[2])
            ) {
                return SWIPE_3_UP
            }
            if (delY[0] > swipeSlopeIntolerance * abs(delX[0]) &&
                delY[1] > swipeSlopeIntolerance * abs(delX[1]) &&
                delY[2] > swipeSlopeIntolerance * abs(delX[2])
            ) {
                return SWIPE_3_DOWN
            }
            if (-delX[0] > swipeSlopeIntolerance * abs(delY[0]) &&
                -delX[1] > swipeSlopeIntolerance * abs(delY[1]) &&
                -delX[2] > swipeSlopeIntolerance * abs(delY[2])
            ) {
                return SWIPE_3_LEFT
            }
            if (delX[0] > swipeSlopeIntolerance * abs(delY[0]) &&
                delX[1] > swipeSlopeIntolerance * abs(delY[1]) &&
                delX[2] > swipeSlopeIntolerance * abs(delY[2])
            ) {
                return SWIPE_3_RIGHT
            }
            if (finalFingDist(fingNum1 = 0, fingNum2 = 1) > 1.75 * initialFingDist(
                    fingNum1 = 0,
                    fingNum2 = 1
                ) &&
                finalFingDist(fingNum1 = 1, fingNum2 = 2) > 1.75 * initialFingDist(
                    fingNum1 = 1,
                    fingNum2 = 2
                ) &&
                finalFingDist(fingNum1 = 2, fingNum2 = 0) > 1.75 * initialFingDist(
                    fingNum1 = 2,
                    fingNum2 = 0
                )
            ) {
                return UNPINCH_3
            }
            if (finalFingDist(fingNum1 = 0, fingNum2 = 1) < 0.66 * initialFingDist(
                    fingNum1 = 0,
                    fingNum2 = 1
                ) &&
                finalFingDist(fingNum1 = 1, fingNum2 = 2) < 0.66 * initialFingDist(
                    fingNum1 = 1,
                    fingNum2 = 2
                ) &&
                finalFingDist(fingNum1 = 2, fingNum2 = 0) < 0.66 * initialFingDist(
                    fingNum1 = 2,
                    fingNum2 = 0
                )
            ) {
                return PINCH_3
            }
        }
        if (numFingers == 4) {
            if (-delY[0] > swipeSlopeIntolerance * abs(delX[0]) &&
                -delY[1] > swipeSlopeIntolerance * abs(delX[1]) &&
                -delY[2] > swipeSlopeIntolerance * abs(delX[2]) &&
                -delY[3] > swipeSlopeIntolerance * abs(delX[3])
            ) {
                return SWIPE_4_UP
            }
            if (delY[0] > swipeSlopeIntolerance * abs(delX[0]) &&
                delY[1] > swipeSlopeIntolerance * abs(delX[1]) &&
                delY[2] > swipeSlopeIntolerance * abs(delX[2]) &&
                delY[3] > swipeSlopeIntolerance * abs(delX[3])
            ) {
                return SWIPE_4_DOWN
            }
            if (-delX[0] > swipeSlopeIntolerance * abs(delY[0]) &&
                -delX[1] > swipeSlopeIntolerance * abs(delY[1]) &&
                -delX[2] > swipeSlopeIntolerance * abs(delY[2]) &&
                -delX[3] > swipeSlopeIntolerance * abs(delY[3])
            ) {
                return SWIPE_4_LEFT
            }
            if (delX[0] > swipeSlopeIntolerance * abs(delY[0]) &&
                delX[1] > swipeSlopeIntolerance * abs(delY[1]) &&
                delX[2] > swipeSlopeIntolerance * abs(delY[2]) &&
                delX[3] > swipeSlopeIntolerance * abs(delY[3])
            ) {
                return SWIPE_4_RIGHT
            }
            if (finalFingDist(fingNum1 = 0, fingNum2 = 1) > 1.5 * initialFingDist(
                    fingNum1 = 0,
                    fingNum2 = 1
                ) &&
                finalFingDist(fingNum1 = 1, fingNum2 = 2) > 1.5 * initialFingDist(
                    fingNum1 = 1,
                    fingNum2 = 2
                ) &&
                finalFingDist(fingNum1 = 2, fingNum2 = 3) > 1.5 * initialFingDist(
                    fingNum1 = 2,
                    fingNum2 = 3
                ) &&
                finalFingDist(fingNum1 = 3, fingNum2 = 0) > 1.5 * initialFingDist(
                    fingNum1 = 3,
                    fingNum2 = 0
                )
            ) {
                return UNPINCH_4
            }
            if (finalFingDist(fingNum1 = 0, fingNum2 = 1) < 0.8 * initialFingDist(
                    fingNum1 = 0,
                    fingNum2 = 1
                ) &&
                finalFingDist(fingNum1 = 1, fingNum2 = 2) < 0.8 * initialFingDist(
                    fingNum1 = 1,
                    fingNum2 = 2
                ) &&
                finalFingDist(fingNum1 = 2, fingNum2 = 3) < 0.8 * initialFingDist(
                    fingNum1 = 2,
                    fingNum2 = 3
                ) &&
                finalFingDist(fingNum1 = 3, fingNum2 = 0) < 0.8 * initialFingDist(
                    fingNum1 = 3,
                    fingNum2 = 0
                )
            ) {
                return PINCH_4
            }
        }
        return 0
    }

    private fun initialFingDist(fingNum1: Int, fingNum2: Int): Double {
        return sqrt(
            (initialX[fingNum1] - initialX[fingNum2]).pow(2.0) +
                    (initialY[fingNum1] - initialY[fingNum2]).pow(2.0)
        )
    }

    private fun finalFingDist(fingNum1: Int, fingNum2: Int): Double {
        return sqrt(
            (finalX[fingNum1] - finalX[fingNum2]).pow(2.0) +
                    (finalY[fingNum1] - finalY[fingNum2]).pow(2.0)
        )
    }

    val isDoubleTap: Boolean
        get() = initialT - prevFinalT < doubleTapMaxDelayMillis && finalT - initialT < doubleTapMaxDownMillis && prevFinalT - prevInitialT < doubleTapMaxDownMillis
}
