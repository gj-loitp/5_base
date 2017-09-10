package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;

/**
 * Created by Phu Tran on 8/11/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class OnAirRecyclerViewPager extends RecyclerViewPager {

    private LinearLayoutManager layoutManager;
    private int extraOffset;

    public OnAirRecyclerViewPager(Context context) {
        super(context);
        init();
    }

    public OnAirRecyclerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OnAirRecyclerViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof LinearLayoutManager) {
            super.setLayoutManager(layout);
            layoutManager = (LinearLayoutManager) layout;
        } else {
            throw new IllegalArgumentException("This view only support linear layout manager");
        }
    }

    private void init() {
        setSinglePageFling(true);
        setFlingFactor(1);
        setTriggerOffset(0.75f);
    }

    @Override
    public void smoothScrollToPosition(int position) {
        if (DEBUG) {
            Log.d("@", "OnAirRecyclerViewPager - smoothScrollToPosition:" + position);
        }
        mSmoothScrollTargetPosition = position;
        if (getLayoutManager() != null && getLayoutManager() instanceof LinearLayoutManager) {
            // exclude item decoration
            LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(getContext()) {
                        @Override
                        public PointF computeScrollVectorForPosition(int targetPosition) {
                            if (getLayoutManager() == null) {
                                return null;
                            }
                            return ((LinearLayoutManager) getLayoutManager())
                                    .computeScrollVectorForPosition(targetPosition);
                        }

                        @Override
                        protected int getVerticalSnapPreference() {
                            return SNAP_TO_START;
                        }

                        @Override
                        protected void onTargetFound(View targetView, State state, Action action) {
                            if (getLayoutManager() == null) {
                                return;
                            }
                            int padding = getBottomPaddingOffset();
                            int dy = calculateDyToMakeVisible(targetView, getVerticalSnapPreference())
                                    + (extraOffset - padding * 2) + padding;
                            final int distance = (int) Math.sqrt(dy * dy);
                            final int time = calculateTimeForDeceleration(distance);
                            if (time > 0) {
                                action.update(0, -dy, time, mDecelerateInterpolator);
                            }
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            if (position == RecyclerView.NO_POSITION) {
                return;
            }
            getLayoutManager().startSmoothScroll(linearSmoothScroller);
        } else {
            super.smoothScrollToPosition(position);
        }
    }
}
