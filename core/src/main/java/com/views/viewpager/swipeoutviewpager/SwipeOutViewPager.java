package com.views.viewpager.swipeoutviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by loitp on 7/20/2018.
 */

public class SwipeOutViewPager extends ViewPager {

    float mStartDragX;
    OnSwipeOutListener mOnSwipeOutListener;

    public SwipeOutViewPager(Context context) {
        super(context);
    }

    public SwipeOutViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mOnSwipeOutListener = listener;
    }

    private void onSwipeOutAtStart() {
        if (mOnSwipeOutListener != null) {
            mOnSwipeOutListener.onSwipeOutAtStart();
        }
    }

    private void onSwipeOutAtEnd() {
        if (mOnSwipeOutListener != null) {
            mOnSwipeOutListener.onSwipeOutAtEnd();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mStartDragX = ev.getX();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (getCurrentItem() == 0 || getCurrentItem() == getAdapter().getCount() - 1) {
            final int action = ev.getAction();
            float x = ev.getX();
            switch (action & MotionEventCompat.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    if (getCurrentItem() == 0 && x > mStartDragX) {
                        onSwipeOutAtStart();
                    }
                    if (getCurrentItem() == getAdapter().getCount() - 1 && x < mStartDragX) {
                        onSwipeOutAtEnd();
                    }
                    break;
            }
        } else {
            mStartDragX = 0;
        }
        return super.onTouchEvent(ev);

    }

    public interface OnSwipeOutListener {
        void onSwipeOutAtStart();

        void onSwipeOutAtEnd();
    }
}