package com.loitpcore.core.utilities.statusbar

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlin.math.abs

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
internal class StatusBarCompatLollipop {

    companion object {
        private var sAnimator: ValueAnimator? = null

        /**
         * return statusBar's Height in pixels
         */
        private fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resId > 0) {
                result = context.resources.getDimensionPixelOffset(resId)
            }
            return result
        }

        /**
         * set StatusBarColor
         *
         *
         * 1. set Flags to call setStatusBarColor
         * 2. call setSystemUiVisibility to clear translucentStatusBar's Flag.
         * 3. set FitsSystemWindows to false
         */
        fun setStatusBarColor(activity: Activity, statusColor: Int) {
            val window = activity.window
            window?.let {
                it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                it.statusBarColor = statusColor
                it.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }

            val mContentView = window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            val mChildView = mContentView.getChildAt(0)
            mChildView?.let {
                ViewCompat.setFitsSystemWindows(it, false)
                ViewCompat.requestApplyInsets(it)
            }
        }

        /**
         * translucentStatusBar(full-screen)
         *
         *
         * 1. set Flags to full-screen
         * 2. set FitsSystemWindows to false
         *
         * @param hideStatusBarBackground hide statusBar's shadow
         */
        fun translucentStatusBar(activity: Activity, hideStatusBarBackground: Boolean) {
            val window = activity.window

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (hideStatusBarBackground) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = Color.TRANSPARENT
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }

            val mContentView = window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            val mChildView = mContentView.getChildAt(0)
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false)
                ViewCompat.requestApplyInsets(mChildView)
            }
        }

        /**
         * compat for CollapsingToolbarLayout
         *
         *
         * 1. change to full-screen parrallaxMode(like translucentStatusBar).
         * 2. cancel CollapsingToolbarLayout's WindowInsets, let it layout as normal(now setStatusBarScrimColor is useless).
         * 3. set View's FitsSystemWindow to false.
         * 4. add Toolbar's height, let it layout from top, then add paddingTop to layout normal.
         * 5. change statusBarColor by AppBarLayout's offset.
         * 6. add Listener to change statusBarColor
         */
        fun setStatusBarColorForCollapsingToolbar(
            activity: Activity,
            appBarLayout: AppBarLayout,
            collapsingToolbarLayout: CollapsingToolbarLayout,
            toolbar: Toolbar,
            statusColor: Int
        ) {
            val window = activity.window

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

            ViewCompat.setOnApplyWindowInsetsListener(collapsingToolbarLayout) { _, insets -> insets }

            val mContentView = window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            val mChildView = mContentView.getChildAt(0)
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false)
                ViewCompat.requestApplyInsets(mChildView)
            }

            (appBarLayout.parent as View).fitsSystemWindows = false
            appBarLayout.fitsSystemWindows = false

            toolbar.fitsSystemWindows = false
            if (toolbar.tag == null) {
                val lp = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
                val statusBarHeight = getStatusBarHeight(activity)
                lp.height += statusBarHeight
                toolbar.layoutParams = lp
                toolbar.setPadding(
                    toolbar.paddingLeft,
                    toolbar.paddingTop + statusBarHeight,
                    toolbar.paddingRight,
                    toolbar.paddingBottom
                )
                toolbar.tag = true
            }

            val behavior = (appBarLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior
            if (behavior != null && behavior is AppBarLayout.Behavior) {
                val verticalOffset = behavior.topAndBottomOffset
                if (abs(verticalOffset) > appBarLayout.height - collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                    window.statusBarColor = statusColor
                } else {
                    window.statusBarColor = Color.TRANSPARENT
                }
            } else {
                window.statusBarColor = Color.TRANSPARENT
            }

            collapsingToolbarLayout.fitsSystemWindows = false
            appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) > appBarLayout.height - collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                    if (window.statusBarColor != statusColor) {
                        startColorAnimation(
                            window.statusBarColor,
                            statusColor,
                            collapsingToolbarLayout.scrimAnimationDuration,
                            window
                        )
                    }
                } else {
                    if (window.statusBarColor != Color.TRANSPARENT) {
                        startColorAnimation(
                            window.statusBarColor,
                            Color.TRANSPARENT,
                            collapsingToolbarLayout.scrimAnimationDuration,
                            window
                        )
                    }
                }
            }
            collapsingToolbarLayout.getChildAt(0).fitsSystemWindows = false
            collapsingToolbarLayout.setStatusBarScrimColor(statusColor)
        }

        /**
         * use ValueAnimator to change statusBarColor when using collapsingToolbarLayout
         */
        @Suppress("unused")
        fun startColorAnimation(startColor: Int, endColor: Int, duration: Long, window: Window?) {
            sAnimator?.cancel()
            sAnimator = ValueAnimator.ofArgb(startColor, endColor)
                .setDuration(duration)
            sAnimator?.addUpdateListener { valueAnimator ->
                window?.statusBarColor = valueAnimator.animatedValue as Int
            }
            sAnimator?.start()
        }
    }
}
