package com.loitp.views.layout.draggablePanel.transformer

import android.view.View
import com.nineoldandroids.view.ViewHelper

/**
 * Transformer extension created to scale the view instead of resize it as the other
 * implementation does. This implementation is based on Nineoldanroids library to scale
 * the view.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
internal class ScaleTransformer(
    view: View,
    parent: View
) : Transformer(
    view,
    parent
) {
    /**
     * Uses Nineoldandroids to change the scale.
     *
     * @param verticalDragOffset used to calculate the new scale.
     */
    override fun updateScale(verticalDragOffset: Float) {
        ViewHelper.setScaleX(mView, 1 - verticalDragOffset / xScaleFactor)
        ViewHelper.setScaleY(mView, 1 - verticalDragOffset / yScaleFactor)
    }

    /**
     * Uses Nineoldandroids to change the position of the view.
     *
     * @param verticalDragOffset used to calculate the new position.
     */
    override fun updatePosition(verticalDragOffset: Float) {
        ViewHelper.setPivotX(mView, (mView.width - mMarginRight).toFloat())
        ViewHelper.setPivotY(mView, (mView.height - mMarginBottom).toFloat())
    }

    /**
     * @return true if the right corner of the view matches with the parent view width.
     */
    override fun isViewAtRight(): Boolean {
        return mView.right == parentView.width
    }

    /**
     * @return true if the bottom corner of the view matches with the parent view height.
     */
    override fun isViewAtBottom(): Boolean {
        return mView.bottom == parentView.height
    }

    /**
     * @return true if the left position of the view is to the left of sixty percent of the parent
     * width.
     */
    override fun isNextToLeftBound(): Boolean {
        return mView.right - mMarginRight < parentView.width * 0.6
    }

    /**
     * @return true if the right position of the view is to the right of the one hundred twenty five
     * five percent of the parent view width.
     */
    override fun isNextToRightBound(): Boolean {
        return mView.right - mMarginRight > parentView.width * 1.25
    }

    /**
     * @return min view height taking into account the configured margin.
     */
    override fun getMinHeightPlusMargin(): Int {
        return mView.height
    }

    /**
     * @return min view width.
     */
    override fun getMinWidthPlusMarginRight(): Int {
        return mOriginalWidth
    }
}
