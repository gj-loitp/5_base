package com.views.viewpager.autoviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

import loitp.core.R;

public class AutoViewPager extends ViewPager {

    private static final String TAG = AutoViewPager.class.getSimpleName();

    private static final int DEFAULT_DURATION = 1000;

    private int duration = DEFAULT_DURATION;
    private float startX;
    private boolean autoScrollEnabled;
    private boolean indeterminate;

    private final Runnable autoScroll = new Runnable() {
        @Override
        public void run() {
            if (!isShown()) {
                return;
            }
            if (getAdapter() == null) {
                return;
            }
            if (getCurrentItem() == getAdapter().getCount() - 1) {
                setCurrentItem(0);
            } else {
                setCurrentItem(getCurrentItem() + 1);
            }
            postDelayed(autoScroll, duration);
        }
    };

    public AutoViewPager(Context context) {
        super(context);
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AutoViewPager);
        try {
            setAutoScrollEnabled(a.getBoolean(R.styleable.AutoViewPager_avp_autoScroll, false));
            setIndeterminate(a.getBoolean(R.styleable.AutoViewPager_avp_indeterminate, false));
            setDuration(a.getInteger(R.styleable.AutoViewPager_avp_duration, DEFAULT_DURATION));
        } finally {
            a.recycle();
        }
    }

    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
    }

    public void setAutoScrollEnabled(boolean enabled) {
        if (autoScrollEnabled == enabled) {
            return;
        }
        autoScrollEnabled = enabled;
        stopAutoScroll();
        if (enabled) {
            startAutoScroll();
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private void startAutoScroll() {
        postDelayed(autoScroll, duration);
    }

    private void stopAutoScroll() {
        removeCallbacks(autoScroll);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            final int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    break;
            }
            return super.onInterceptTouchEvent(event);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (indeterminate) {
                if (getCurrentItem() == 0 || getCurrentItem() == getAdapter().getCount() - 1) {
                    final int action = event.getAction();
                    final float x = event.getX();
                    switch (action & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            if (getCurrentItem() == getAdapter().getCount() - 1 && x < startX) {
                                post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setCurrentItem(0);
                                    }
                                });
                            }
                            break;
                    }
                } else {
                    startX = 0;
                }
            }
            return super.onTouchEvent(event);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return false;
    }
}