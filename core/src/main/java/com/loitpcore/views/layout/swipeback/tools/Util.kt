package com.loitpcore.views.layout.swipeback.tools

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.AbsListView
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

/**
 * Created by GongWen on 17/8/25.
 */
internal object Util {

    @JvmStatic
    fun canViewScrollUp(
        mView: View?,
        x: Float,
        y: Float,
        defaultValueForNull: Boolean
    ): Boolean {
        return if (mView == null || !contains(mView, x, y)) {
            defaultValueForNull
        } else {
//            ViewCompat.canScrollVertically(mView, -1)
            mView.canScrollVertically(-1)
        }
    }

    @JvmStatic
    fun canViewScrollDown(
        mView: View?,
        x: Float,
        y: Float,
        defaultValueForNull: Boolean
    ): Boolean {
        return if (mView == null || !contains(mView, x, y)) {
            defaultValueForNull
        } else {
//            ViewCompat.canScrollVertically(mView, 1)
            mView.canScrollVertically(1)
        }
    }

    @JvmStatic
    fun canViewScrollRight(
        mView: View?,
        x: Float,
        y: Float,
        defaultValueForNull: Boolean
    ): Boolean {
        return if (mView == null || !contains(mView, x, y)) {
            defaultValueForNull
        } else {
//            ViewCompat.canScrollHorizontally(mView, -1)
            mView.canScrollHorizontally(-1)
        }
    }

    @JvmStatic
    fun canViewScrollLeft(
        mView: View?,
        x: Float,
        y: Float,
        defaultValueForNull: Boolean
    ): Boolean {
        return if (mView == null || !contains(mView, x, y)) {
            defaultValueForNull
        } else {
//            ViewCompat.canScrollHorizontally(mView, 1)
            mView.canScrollHorizontally(1)
        }
    }

    @JvmStatic
    fun findAllScrollViews(
        mViewGroup: ViewGroup
    ): View? {
        for (i in 0 until mViewGroup.childCount) {
            var mView = mViewGroup.getChildAt(i)
            if (mView?.visibility != View.VISIBLE) {
                continue
            }
            if (isScrollableView(mView)) {
                return mView
            }
            if (mView is ViewGroup) {
                mView = findAllScrollViews(mViewGroup = mView)
                if (mView != null) {
                    return mView
                }
            }
        }
        return null
    }

    fun isScrollableView(
        mView: View?
    ): Boolean {
        return (
                mView is ScrollView ||
                        mView is HorizontalScrollView ||
                        mView is NestedScrollView ||
                        mView is AbsListView ||
                        mView is RecyclerView ||
                        mView is ViewPager ||
                        mView is WebView
                )
    }

    @JvmStatic
    fun contains(
        mView: View,
        x: Float,
        y: Float
    ): Boolean {
        val localRect = Rect()
        mView.getGlobalVisibleRect(localRect)
        return localRect.contains(x.toInt(), y.toInt())
    }

    @JvmStatic
    fun onPanelSlide(
        fraction: Float
    ) {
        val activity = WxSwipeBackActivityManager.instance.penultimateActivity
        if (activity != null && !activity.isFinishing) {
            val decorView = activity.window.decorView
//            ViewCompat.setTranslationX(decorView, -(decorView.measuredWidth / 3.0f) * (1 - fraction))
            decorView.translationX = -(decorView.measuredWidth / 3.0f) * (1 - fraction)
        }
    }

    @JvmStatic
    fun onPanelReset() {
        val activity = WxSwipeBackActivityManager.instance.penultimateActivity
        if (activity != null) {
            val decorView = activity.window.decorView
//            ViewCompat.setTranslationX(decorView, 0f)
            decorView.translationX = 0f
        }
    }
}
