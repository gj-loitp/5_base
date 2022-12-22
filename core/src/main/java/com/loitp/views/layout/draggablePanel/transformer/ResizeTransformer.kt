package com.loitp.views.layout.draggablePanel.transformer

import android.view.View
import android.widget.RelativeLayout

/**
 * Transformer extension created to resize the view instead of scale it as the other
 * implementation does. This implementation is based on change the LayoutParams.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
internal class ResizeTransformer(
    view: View,
    parent: View
) : Transformer(view, parent) {

    private val layoutParams: RelativeLayout.LayoutParams =
        view.layoutParams as RelativeLayout.LayoutParams

    /**
     * Changes view scale using view's LayoutParam.
     *
     * @param verticalDragOffset used to calculate the new size.
     */
    override fun updateScale(verticalDragOffset: Float) {
        layoutParams.width = (mOriginalWidth * (1 - verticalDragOffset / xScaleFactor)).toInt()
        layoutParams.height = (mOriginalHeight * (1 - verticalDragOffset / yScaleFactor)).toInt()
        mView.layoutParams = layoutParams
    }

    /**
     * Changes X view position using layout() method.
     *
     * @param verticalDragOffset used to calculate the new X position.
     */
    override fun updatePosition(verticalDragOffset: Float) {
        val right = getViewRightPosition(verticalDragOffset)
        val left = right - layoutParams.width
        val top = mView.top
        val bottom = top + layoutParams.height
        mView.layout(left, top, right, bottom)
    }

    /**
     * @return true if the right position of the view plus the right margin is equals to the parent
     * width.
     */
    override fun isViewAtRight(): Boolean {
        return mView.right + mMarginRight == parentView.width
    }

    /**
     * @return true if the bottom position of the view plus the margin right is equals to
     * the parent view height.
     */
    override fun isViewAtBottom(): Boolean {
        return mView.bottom + mMarginBottom == parentView.height
    }

    /**
     * @return true if the left position of the view is to the right of the seventy five percent of
     * the parent view width.
     */
    override fun isNextToRightBound(): Boolean {
        return mView.left - mMarginRight > parentView.width * 0.75
    }

    /**
     * @return true if the left position of the view is to the left of the twenty five percent of
     * the parent width.
     */
    override fun isNextToLeftBound(): Boolean {
        return mView.left - mMarginRight < parentView.width * 0.05
    }

    /**
     * Uses the Y scale factor to calculate the min possible height.
     */
    override fun getMinHeightPlusMargin(): Int {
        return (mOriginalHeight * (1 - 1 / yScaleFactor) + mMarginBottom).toInt()
    }

    /**
     * Uses the X scale factor to calculate the min possible width.
     */
    override fun getMinWidthPlusMarginRight(): Int {
        return (mOriginalWidth * (1 - 1 / xScaleFactor) + mMarginRight).toInt()
    }

    /**
     * Calculate the current view right position for a given verticalDragOffset.
     *
     * @param verticalDragOffset used to calculate the new right position.
     */
    private fun getViewRightPosition(verticalDragOffset: Float): Int {
        return (mOriginalWidth - mMarginRight * verticalDragOffset).toInt()
    }
}
