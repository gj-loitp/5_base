package vn.loitp.views.layout.swipeback.tools;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Created by GongWen on 17/8/25.
 */

public class Util {
    public static boolean canViewScrollUp(View mView, float x, float y, boolean defaultValueForNull) {
        if (mView == null || !contains(mView, x, y)) {
            return defaultValueForNull;
        }
        return ViewCompat.canScrollVertically(mView, -1);
    }

    public static boolean canViewScrollDown(View mView, float x, float y, boolean defaultValueForNull) {
        if (mView == null || !contains(mView, x, y)) {
            return defaultValueForNull;
        }
        return ViewCompat.canScrollVertically(mView, 1);
    }

    public static boolean canViewScrollRight(View mView, float x, float y, boolean defaultValueForNull) {
        if (mView == null || !contains(mView, x, y)) {
            return defaultValueForNull;
        }
        return ViewCompat.canScrollHorizontally(mView, -1);
    }

    public static boolean canViewScrollLeft(View mView, float x, float y, boolean defaultValueForNull) {
        if (mView == null || !contains(mView, x, y)) {
            return defaultValueForNull;
        }
        return ViewCompat.canScrollHorizontally(mView, 1);
    }


    public static View findAllScrollViews(ViewGroup mViewGroup) {
        for (int i = 0; i < mViewGroup.getChildCount(); i++) {
            View mView = mViewGroup.getChildAt(i);
            if (mView.getVisibility() != View.VISIBLE) {
                continue;
            }
            if (isScrollableView(mView)) {
                return mView;
            }
            if (mView instanceof ViewGroup) {
                mView = findAllScrollViews((ViewGroup) mView);
                if (mView != null) {
                    return mView;
                }
            }
        }
        return null;
    }

    public static boolean isScrollableView(View mView) {
        return mView instanceof ScrollView
                || mView instanceof HorizontalScrollView
                || mView instanceof NestedScrollView
                || mView instanceof AbsListView
                || mView instanceof RecyclerView
                || mView instanceof ViewPager
                || mView instanceof WebView;
    }

    public static boolean contains(View mView, float x, float y) {
        Rect localRect = new Rect();
        mView.getGlobalVisibleRect(localRect);
        return localRect.contains((int) x, (int) y);
    }

    public static void onPanelSlide(float fraction) {
        Activity activity = WxSwipeBackActivityManager.getInstance().getPenultimateActivity();
        if (activity != null && !activity.isFinishing()) {
            View decorView = activity.getWindow().getDecorView();
            ViewCompat.setTranslationX(decorView, -(decorView.getMeasuredWidth() / 3.0f) * (1 - fraction));
        }
    }

    public static void onPanelReset() {
        Activity activity = WxSwipeBackActivityManager.getInstance().getPenultimateActivity();
        if (activity != null) {
            View decorView = activity.getWindow().getDecorView();
            ViewCompat.setTranslationX(decorView, 0);
        }
    }
}
