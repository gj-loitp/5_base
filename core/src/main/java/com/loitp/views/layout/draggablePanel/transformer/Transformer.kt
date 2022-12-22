package com.loitp.views.layout.draggablePanel.transformer

import android.view.View
import android.widget.RelativeLayout
import com.nineoldandroids.view.ViewHelper

/**
 * Abstract class created to be implemented by different classes are going to change the size of a
 * view. The most basic one is going to scale the view and the most complex used with VideoView is
 * going to change the size of the view.
 *
 *
 * The view used in this class has to be contained by a RelativeLayout.
 *
 *
 * This class also provide information about the size of the view and the position because
 * different Transformer implementations could change the size of the view but not the position,
 * like ScaleTransformer does.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
abstract class Transformer(
    protected val mView: View,
    protected val parentView: View
) {

    var mMarginRight = 0
    var mMarginBottom = 0
    var xScaleFactor = 0f
    var yScaleFactor = 0f
    /**
     * @return height of the view before it has change the size.
     */
    /**
     * Change view height using the LayoutParams of the view.
     *
     * param newHeight to change..
     */
    var mOriginalHeight = 0
        get() {
            if (field == 0) {
                field = mView.measuredHeight
            }
            return field
        }
        set(newHeight) {
            if (newHeight > 0) {
                field = newHeight
                val layoutParams = mView.layoutParams as RelativeLayout.LayoutParams
                layoutParams.height = newHeight
                mView.layoutParams = layoutParams
            }
        }

    /**
     * @return width of the view before it has change the size.
     */
    var mOriginalWidth = 0
        get() {
            if (field == 0) {
                field = mView.measuredWidth
            }
            return field
        }
        private set

    abstract fun updatePosition(verticalDragOffset: Float)

    abstract fun updateScale(verticalDragOffset: Float)

    val isViewAtTop: Boolean
        get() = mView.top == 0

    val isAboveTheMiddle: Boolean
        get() {
            val parentHeight = parentView.height
            val viewYPosition = ViewHelper.getY(mView) + mView.height * 0.5f
            return viewYPosition < parentHeight * 0.5
        }

    abstract fun isViewAtRight(): Boolean

    abstract fun isViewAtBottom(): Boolean

    abstract fun isNextToRightBound(): Boolean

    abstract fun isNextToLeftBound(): Boolean

    /**
     * @return min possible height, after apply the transformation, plus the margin right.
     */
    abstract fun getMinHeightPlusMargin(): Int

    /**
     * @return min possible width, after apply the transformation.
     */
    abstract fun getMinWidthPlusMarginRight(): Int
}
