package com.loitp.views.draggableFlipView

import android.view.MotionEvent

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DragGestureDetector(
    private val dragGestureListener: DragGestureListener
) {
    @JvmField
    var deltaX = 0f
    private var deltaY = 0f

    @JvmField
    var prevDeltaX = 0f
    private var prevDeltaY = 0f
    private var originalIndex = 0
    private var velocityX = 0f
    private var velocityY = 0f
    private val pointMap = HashMap<Int, TouchPoint?>()

    fun setPointMap(event: MotionEvent) {
        val eventX = event.x
        val eventY = event.y
        var downPoint = pointMap[0]
        if (downPoint != null) {
            downPoint.setXY(eventX, eventY)
            return
        }
        downPoint = createPoint(x = eventX, y = eventY)
        pointMap[0] = downPoint
    }

    val touchPoint: TouchPoint?
        get() = pointMap[originalIndex]

    @Synchronized
    fun onTouchEvent(event: MotionEvent): Boolean {
        val eventX = event.getX(originalIndex)
        val eventY = event.getY(originalIndex)
        when (val action = event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val originalPoint = pointMap[originalIndex]
                if (originalPoint != null) {
                    deltaX = eventX - originalPoint.x
                    deltaY = eventY - originalPoint.y
                    dragGestureListener.onDragGestureListener(
                        dragGestureDetector = this,
                        action = action
                    )
                    velocityX = deltaX - prevDeltaX
                    velocityY = deltaY - prevDeltaY
                    prevDeltaX = deltaX
                    prevDeltaY = deltaY
                }
            }
            MotionEvent.ACTION_UP -> {
                val originalPoint = pointMap[originalIndex]
                if (originalPoint != null) {
                    dragGestureListener.onDragGestureListener(
                        dragGestureDetector = this,
                        action = action
                    )
                }
                velocityY = 0f
                velocityX = velocityY
                prevDeltaY = 0f
                prevDeltaX = prevDeltaY
                deltaY = 0f
                deltaX = deltaY
            }
            else -> {
            }
        }
        return false
    }

    private fun createPoint(
        x: Float,
        y: Float
    ): TouchPoint {
        return TouchPoint(x, y)
    }

    interface DragGestureListener {
        fun onDragGestureListener(dragGestureDetector: DragGestureDetector?, action: Int)
    }

    inner class TouchPoint(
        var x: Float,
        var y: Float
    ) {
        fun setXY(x: Float, y: Float): TouchPoint {
            this.x = x
            this.y = y
            return this
        }
    }

    init {
        pointMap[0] = createPoint(0f, 0f)
    }
}
