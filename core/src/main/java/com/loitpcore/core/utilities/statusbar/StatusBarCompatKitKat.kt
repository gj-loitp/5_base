package com.loitpcore.core.utilities.statusbar

import android.app.Activity
import android.content.Context
import android.view.*
import android.widget.FrameLayout
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
internal class StatusBarCompatKitKat {

    companion object {
        private const val TAG_FAKE_STATUS_BAR_VIEW = "statusBarView"
        private const val TAG_MARGIN_ADDED = "marginAdded"

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
         * 1. Add fake statusBarView.
         * 2. set tag to statusBarView.
         */
        private fun addFakeStatusBarView(
            activity: Activity,
            statusBarColor: Int,
            statusBarHeight: Int
        ): View {
            val window = activity.window
            val mDecorView = window.decorView as ViewGroup

            val mStatusBarView = View(activity)
            val layoutParams =
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
            layoutParams.gravity = Gravity.TOP
            mStatusBarView.layoutParams = layoutParams
            mStatusBarView.setBackgroundColor(statusBarColor)
            mStatusBarView.tag = TAG_FAKE_STATUS_BAR_VIEW

            mDecorView.addView(mStatusBarView)
            return mStatusBarView
        }

        /**
         * use reserved order to remove is more quickly.
         */
        private fun removeFakeStatusBarViewIfExist(activity: Activity) {
            val window = activity.window
            val mDecorView = window.decorView as ViewGroup

            val fakeView = mDecorView.findViewWithTag<View>(TAG_FAKE_STATUS_BAR_VIEW)
            if (fakeView != null) {
                mDecorView.removeView(fakeView)
            }
        }

        /**
         * add marginTop to simulate set FitsSystemWindow true
         */
        private fun addMarginTopToContentChild(mContentChild: View?, statusBarHeight: Int) {
            if (mContentChild == null) {
                return
            }
            if (TAG_MARGIN_ADDED != mContentChild.tag) {
                val lp = mContentChild.layoutParams as FrameLayout.LayoutParams
                lp.topMargin += statusBarHeight
                mContentChild.layoutParams = lp
                mContentChild.tag = TAG_MARGIN_ADDED
            }
        }

        /**
         * remove marginTop to simulate set FitsSystemWindow false
         */
        private fun removeMarginTopOfContentChild(mContentChild: View?, statusBarHeight: Int) {
            if (mContentChild == null) {
                return
            }
            if (TAG_MARGIN_ADDED == mContentChild.tag) {
                val lp = mContentChild.layoutParams as FrameLayout.LayoutParams
                lp.topMargin -= statusBarHeight
                mContentChild.layoutParams = lp
                mContentChild.tag = null
            }
        }

        /**
         * set StatusBarColor
         *
         *
         * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
         * 2. removeFakeStatusBarViewIfExist
         * 3. addFakeStatusBarView
         * 4. addMarginTopToContentChild
         * 5. cancel ContentChild's fitsSystemWindow
         */
        fun setStatusBarColor(activity: Activity, statusColor: Int) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            val mContentView = window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            val mContentChild = mContentView.getChildAt(0)
            val statusBarHeight = getStatusBarHeight(activity)

            removeFakeStatusBarViewIfExist(activity)
            addFakeStatusBarView(activity, statusColor, statusBarHeight)
            addMarginTopToContentChild(mContentChild, statusBarHeight)

            if (mContentChild != null) {
                ViewCompat.setFitsSystemWindows(mContentChild, false)
            }
        }

        /**
         * translucentStatusBar
         *
         *
         * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
         * 2. removeFakeStatusBarViewIfExist
         * 3. removeMarginTopOfContentChild
         * 4. cancel ContentChild's fitsSystemWindow
         */
        fun translucentStatusBar(activity: Activity) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            val mContentView = activity.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            val mContentChild = mContentView.getChildAt(0)

            removeFakeStatusBarViewIfExist(activity)
            removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity))
            if (mContentChild != null) {
                ViewCompat.setFitsSystemWindows(mContentChild, false)
            }
        }

        /**
         * compat for CollapsingToolbarLayout
         *
         *
         * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
         * 2. set FitsSystemWindows for views.
         * 3. add Toolbar's height, let it layout from top, then add paddingTop to layout normal.
         * 4. removeFakeStatusBarViewIfExist
         * 5. removeMarginTopOfContentChild
         * 6. add OnOffsetChangedListener to change statusBarView's alpha
         */
        fun setStatusBarColorForCollapsingToolbar(
            activity: Activity,
            appBarLayout: AppBarLayout,
            collapsingToolbarLayout: CollapsingToolbarLayout,
            toolbar: Toolbar,
            statusColor: Int
        ) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val mContentView = window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup

            val mContentChild = mContentView.getChildAt(0)
            mContentChild.fitsSystemWindows = false
            (appBarLayout.parent as View).fitsSystemWindows = false
            appBarLayout.fitsSystemWindows = false
            collapsingToolbarLayout.fitsSystemWindows = false
            collapsingToolbarLayout.getChildAt(0).fitsSystemWindows = false

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

            val statusBarHeight = getStatusBarHeight(activity)
            removeFakeStatusBarViewIfExist(activity)
            removeMarginTopOfContentChild(mContentChild, statusBarHeight)
            val statusView = addFakeStatusBarView(activity, statusColor, statusBarHeight)

            val behavior = (appBarLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior
            if (behavior != null && behavior is AppBarLayout.Behavior) {
                val verticalOffset = behavior.topAndBottomOffset
                if (abs(verticalOffset) > appBarLayout.height - collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                    statusView.alpha = 1f
                } else {
                    statusView.alpha = 0f
                }
            } else {
                statusView.alpha = 0f
            }

            appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
                if (abs(verticalOffset) > appBarLayout.height - collapsingToolbarLayout.scrimVisibleHeightTrigger) {
                    if (statusView.alpha == 0f) {
                        statusView.animate().cancel()
                        statusView.animate().alpha(1f)
                            .setDuration(collapsingToolbarLayout.scrimAnimationDuration).start()
                    }
                } else {
                    if (statusView.alpha == 1f) {
                        statusView.animate().cancel()
                        statusView.animate().alpha(0f)
                            .setDuration(collapsingToolbarLayout.scrimAnimationDuration).start()
                    }
                }
            }
        }
    }
}
