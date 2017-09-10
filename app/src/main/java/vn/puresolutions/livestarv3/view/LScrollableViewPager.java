package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by www.muathu@gmail.com on 7/31/2017.
 */

public class LScrollableViewPager extends ViewPager {
    private boolean canScroll = true;

    public boolean isCanScroll() {
        return canScroll;
    }

    public LScrollableViewPager(Context context) {
        super(context);
    }

    public LScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroll && super.onInterceptTouchEvent(ev);
    }
}