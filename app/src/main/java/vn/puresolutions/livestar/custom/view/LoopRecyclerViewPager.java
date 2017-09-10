package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.util.AttributeSet;

import vn.puresolutions.livestar.adapter.recycler.base.LoopRecyclerViewPagerAdapter;

public class LoopRecyclerViewPager extends RecyclerViewPager {

    public LoopRecyclerViewPager(Context context) {
        this(context, null);
    }

    public LoopRecyclerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopRecyclerViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof LoopRecyclerViewPagerAdapter) {
            super.setAdapter(adapter);
            super.scrollToPosition(getMiddlePosition());
        } else {
            throw new IllegalArgumentException("This view only support LoopRecyclerViewPagerAdapter");
        }
    }

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        if (adapter instanceof LoopRecyclerViewPagerAdapter) {
            super.swapAdapter(adapter, removeAndRecycleExistingViews);
            super.scrollToPosition(getMiddlePosition());
        } else {
            throw new IllegalArgumentException("This view only support LoopRecyclerViewPagerAdapter");
        }
    }

    /**
     * Starts a smooth scroll to an adapter position.
     * if position < adapter.getActualCount,
     * position will be transform to right position.
     *
     * @param position target position
     */
    @Override
    public void smoothScrollToPosition(int position) {
        int transformedPosition = transformInnerPositionIfNeed(position);
        super.smoothScrollToPosition(transformedPosition);
    }

    /**
     * Starts a scroll to an adapter position.
     * if position < adapter.getActualCount,
     * position will be transform to right position.
     *
     * @param position target position
     */
    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(transformInnerPositionIfNeed(position));
    }

    /**
     * get actual current position in actual adapter.
     */
    public int getActualCurrentPosition() {
        int position = getCurrentPosition();
        return transformToActualPosition(position);
    }

    /**
     * Transform adapter position to actual position.
     * @param position adapter position
     * @return actual position
     */
    public int transformToActualPosition(int position) {
        return position % getActualItemCountFromAdapter();
    }

    private int getActualItemCountFromAdapter() {
        return ((LoopRecyclerViewPagerAdapter) getAdapter()).getActualItemCount();
    }

    protected int transformInnerPositionIfNeed(int position) {
        final int actualItemCount = getActualItemCountFromAdapter();
        final int actualCurrentPosition = getCurrentPosition() % actualItemCount;
        int bakPosition1 = getCurrentPosition()
                - actualCurrentPosition
                + position % actualItemCount;
        int bakPosition2 = getCurrentPosition()
                - actualCurrentPosition
                - actualItemCount
                + position % actualItemCount;
        int bakPosition3 = getCurrentPosition()
                - actualCurrentPosition
                + actualItemCount
                + position % actualItemCount;
        // get position which is closer to current position
        if (Math.abs(bakPosition1 - getCurrentPosition()) > Math.abs(bakPosition2 -
                getCurrentPosition())){
            if (Math.abs(bakPosition2 -
                    getCurrentPosition())> Math.abs(bakPosition3 -
                    getCurrentPosition())) {
                return bakPosition3;
            }
            return bakPosition2;
        } else {
            if (Math.abs(bakPosition1 -
                    getCurrentPosition())> Math.abs(bakPosition3 -
                    getCurrentPosition())) {
                return bakPosition3;
            }
            return bakPosition1;
        }
    }

    protected int getMiddlePosition() {
        int middlePosition = Integer.MAX_VALUE / 2;
        final int actualItemCount = getActualItemCountFromAdapter();
        if (actualItemCount > 0 && middlePosition % actualItemCount != 0) {
            middlePosition = middlePosition - middlePosition % actualItemCount;
        }
        return middlePosition;
    }
}
