package com.loitp.views.layout.draggablePanel

import android.view.View
import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * ViewDragHelper.Callback implementation used to work with DraggableView to perform the scale
 * effect and other animation when the view is released.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
internal class DraggableViewCallback

/**
 * Main constructor.
 *
 * @param draggableView instance used to apply some animations or visual effects.
 */(
    private val draggableView: DraggableView,
    private val draggedView: View
) : ViewDragHelper.Callback() {

    companion object {
        private const val MINIMUM_DX_FOR_HORIZONTAL_DRAG = 5
        private const val MINIMUM_DY_FOR_VERTICAL_DRAG = 15
        private const val X_MIN_VELOCITY = 1500f
        private const val Y_MIN_VELOCITY = 1000f
    }

    /**
     * Override method used to apply different scale and alpha effects while the view is being
     * dragged.
     *
     * @param left position.
     * @param top  position.
     * @param dx   change in X position from the last call.
     * @param dy   change in Y position from the last call.
     */
    override fun onViewPositionChanged(
        changedView: View,
        left: Int,
        top: Int,
        dx: Int,
        dy: Int
    ) {
        if (draggableView.isDragViewAtBottom) {
            draggableView.changeDragViewViewAlpha()
        } else {
            draggableView.restoreAlpha()
            draggableView.changeDragViewScale()
            draggableView.changeDragViewPosition()
            draggableView.changeSecondViewAlpha()
            draggableView.changeSecondViewPosition()
            draggableView.changeBackgroundAlpha()
        }
    }

    /**
     * Override method used to apply different animations when the dragged view is released. The
     * dragged view is going to be maximized or minimized if the view is above the middle of the
     * custom view and the velocity is greater than a constant value.
     *
     * @param releasedChild the captured child view now being released.
     * @param xVel          X velocity of the pointer as it left the screen in pixels per second.
     * @param yVel          Y velocity of the pointer as it left the screen in pixels per second.
     */
    override fun onViewReleased(
        releasedChild: View,
        xVel: Float,
        yVel: Float
    ) {
        super.onViewReleased(releasedChild, xVel, yVel)
        if (draggableView.isDragViewAtBottom && !draggableView.isDragViewAtRight) {
            triggerOnReleaseActionsWhileHorizontalDrag(xVel)
        } else {
            triggerOnReleaseActionsWhileVerticalDrag(yVel)
        }
    }

    /**
     * Override method used to configure which is going to be the dragged view.
     *
     * @param view      child the user is attempting to capture.
     * @param pointerId ID of the pointer attempting the capture,
     * @return true if capture should be allowed, false otherwise.
     */
    override fun tryCaptureView(
        view: View,
        pointerId: Int
    ): Boolean {
        return view == draggedView
    }

    /**
     * Override method used to configure the horizontal drag. Restrict the motion of the dragged
     * child view along the horizontal axis.
     *
     * @param child child view being dragged.
     * @param left  attempted motion along the X axis.
     * @param dx    proposed change in position for left.
     * @return the new clamped position for left.
     */
    override fun clampViewPositionHorizontal(
        child: View,
        left: Int,
        dx: Int
    ): Int {
        var newLeft = draggedView.left
        if (draggableView.isMinimized && abs(dx) > MINIMUM_DX_FOR_HORIZONTAL_DRAG || (draggableView.isDragViewAtBottom && !draggableView.isDragViewAtRight)) {
            newLeft = left
        }
        return newLeft
    }

    /**
     * Override method used to configure the vertical drag. Restrict the motion of the dragged child
     * view along the vertical axis.
     *
     * @param child child view being dragged.
     * @param top   attempted motion along the Y axis.
     * @param dy    proposed change in position for top.
     * @return the new clamped position for top.
     */
    override fun clampViewPositionVertical(
        child: View,
        top: Int,
        dy: Int
    ): Int {
        var newTop = draggableView.height - draggableView.draggedViewHeightPlusMarginTop
        if (draggableView.isMinimized && abs(dy) >= MINIMUM_DY_FOR_VERTICAL_DRAG ||
            !draggableView.isMinimized && !draggableView.isDragViewAtBottom
        ) {
            val topBound = draggableView.paddingTop
            val bottomBound = (
                    draggableView.height -
                            draggableView.draggedViewHeightPlusMarginTop -
                            draggedView.paddingBottom
                    )
            newTop = min(max(top, topBound), bottomBound)
        }
        return newTop
    }

    /**
     * Maximize or minimize the DraggableView using the draggableView position and the y axis
     * velocity.
     */
    private fun triggerOnReleaseActionsWhileVerticalDrag(yVel: Float) {
        if (yVel < 0 && yVel <= -Y_MIN_VELOCITY) {
            draggableView.maximize()
        } else if (yVel > 0 && yVel >= Y_MIN_VELOCITY) {
            draggableView.minimize()
        } else {
            if (draggableView.isDragViewAboveTheMiddle) {
                draggableView.maximize()
            } else {
                draggableView.minimize()
            }
        }
    }

    /**
     * Close the view to the right, to the left or minimize it using the draggableView position and
     * the x axis velocity.
     */
    private fun triggerOnReleaseActionsWhileHorizontalDrag(xVel: Float) {
        if (xVel < 0 && xVel <= -X_MIN_VELOCITY) {
            draggableView.closeToLeft()
        } else if (xVel > 0 && xVel >= X_MIN_VELOCITY) {
            draggableView.closeToRight()
        } else {
            when {
                draggableView.isNextToLeftBound -> {
                    draggableView.closeToLeft()
                }
                draggableView.isNextToRightBound -> {
                    draggableView.closeToRight()
                }
                else -> {
                    draggableView.minimize()
                }
            }
        }
    }
}
