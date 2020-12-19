package com.views.layout.draggablepanel.transformer;

import android.view.View;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Abstract class created to be implemented by different classes are going to change the size of a
 * view. The most basic one is going to scale the view and the most complex used with VideoView is
 * going to change the size of the view.
 * <p/>
 * The view used in this class has to be contained by a RelativeLayout.
 * <p/>
 * This class also provide information about the size of the view and the position because
 * different Transformer implementations could change the size of the view but not the position,
 * like ScaleTransformer does.
 *
 * @author Pedro Vicente Gómez Sánchez
 */

public abstract class Transformer {

    private final View mView;
    private final View mParent;

    private int mMarginRight;
    private int mMarginBottom;

    private float mXScaleFactor;
    private float mYScaleFactor;

    private int mOriginalHeight;
    private int mOriginalWidth;

    public Transformer(View mView, View mParent) {
        this.mView = mView;
        this.mParent = mParent;
    }

    public float getXScaleFactor() {
        return mXScaleFactor;
    }

    public void setXScaleFactor(float xScaleFactor) {
        this.mXScaleFactor = xScaleFactor;
    }

    public float getYScaleFactor() {
        return mYScaleFactor;
    }

    public void setYScaleFactor(float yScaleFactor) {
        this.mYScaleFactor = yScaleFactor;
    }

    public int getMMarginRight() {
        return mMarginRight;
    }

    public void setMMarginRight(int mMarginRight) {
        this.mMarginRight = Math.round(mMarginRight);
    }

    public int getMMarginBottom() {
        return mMarginBottom;
    }

    public void setMMarginBottom(int mMarginBottom) {
        this.mMarginBottom = Math.round(mMarginBottom);
    }

    /**
     * @return height of the view before it has change the size.
     */
    public int getMOriginalHeight() {
        if (mOriginalHeight == 0) {
            mOriginalHeight = mView.getMeasuredHeight();
        }
        return mOriginalHeight;
    }

    /**
     * Change view height using the LayoutParams of the view.
     *
     * @param newHeight to change..
     */
    public void setMOriginalHeight(int newHeight) {
        if (newHeight > 0) {
            mOriginalHeight = newHeight;
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) mView.getLayoutParams();
            layoutParams.height = newHeight;
            mView.setLayoutParams(layoutParams);
        }
    }

    protected View getMView() {
        return mView;
    }

    protected View getParentView() {
        return mParent;
    }

    public abstract void updatePosition(float verticalDragOffset);

    public abstract void updateScale(float verticalDragOffset);

    /**
     * @return width of the view before it has change the size.
     */
    public int getMOriginalWidth() {
        if (mOriginalWidth == 0) {
            mOriginalWidth = mView.getMeasuredWidth();
        }
        return mOriginalWidth;
    }

    public boolean isViewAtTop() {
        return mView.getTop() == 0;
    }

    public boolean isAboveTheMiddle() {
        int parentHeight = mParent.getHeight();
        float viewYPosition = ViewHelper.getY(mView) + (mView.getHeight() * 0.5f);
        return viewYPosition < (parentHeight * 0.5);
    }

    public abstract boolean isViewAtRight();

    public abstract boolean isViewAtBottom();

    public abstract boolean isNextToRightBound();

    public abstract boolean isNextToLeftBound();

    /**
     * @return min possible height, after apply the transformation, plus the margin right.
     */
    public abstract int getMinHeightPlusMargin();

    /**
     * @return min possible width, after apply the transformation.
     */
    public abstract int getMinWidthPlusMarginRight();
}
