package vn.loitp.views.viewpager.autoviewpager.lib;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import loitp.core.R;

/**
 * Created by santalu on 09/08/2017.
 */

public class AutoViewPager extends ViewPager {

    private static final String TAG = AutoViewPager.class.getSimpleName();

    private static final int DEFAULT_DURATION = 10000;

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
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AutoViewPager);
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
            int action = event.getActionMasked();
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
                    float x = event.getX();
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