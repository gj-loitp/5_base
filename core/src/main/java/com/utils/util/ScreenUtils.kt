package com.utils.util

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.Surface
import android.view.WindowManager
import com.utils.util.BarUtils.Companion.getStatusBarHeight
import com.utils.util.Utils.Companion.getContext

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : 屏幕相关工具类
</pre> *
 */
class ScreenUtils {
    companion object {

        val screenWidth: Int
            get() {
                val windowManager =
                    getContext()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                val dm = DisplayMetrics()
                windowManager?.defaultDisplay?.getMetrics(dm)
                return dm.widthPixels
            }

        val screenHeight: Int
            get() {
                val windowManager =
                    getContext()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
                val dm = DisplayMetrics()
                windowManager?.defaultDisplay?.getMetrics(dm)
                return dm.heightPixels
            }

        fun setLandscape(activity: Activity) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        fun setPortrait(activity: Activity) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val isLandscape: Boolean
            get() = getContext()?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE

        val isPortrait: Boolean
            get() = getContext()?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT

        fun getScreenRotation(activity: Activity): Int {
            return when (activity.windowManager.defaultDisplay.rotation) {
                Surface.ROTATION_0 -> 0
                Surface.ROTATION_90 -> 90
                Surface.ROTATION_180 -> 180
                Surface.ROTATION_270 -> 270
                else -> 0
            }
        }

        fun captureWithStatusBar(activity: Activity): Bitmap {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(dm)
            val ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels)
            view.destroyDrawingCache()
            return ret
        }

        fun captureWithoutStatusBar(activity: Activity): Bitmap {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val statusBarHeight = getStatusBarHeight(activity)
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(dm)
            val ret = Bitmap.createBitmap(
                bmp,
                0,
                statusBarHeight,
                dm.widthPixels,
                dm.heightPixels - statusBarHeight
            )
            view.destroyDrawingCache()
            return ret
        }
    }
}
