package com.loitpcore.utils.util

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.Surface
import android.view.WindowManager
import com.loitp.core.utils.BarUtils.Companion.getStatusBarHeight
import com.loitpcore.utils.util.Utils.Companion.getContext

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
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

        @Suppress("unused")
        fun setLandscape(activity: Activity) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        @Suppress("unused")
        fun setPortrait(activity: Activity) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        val isLandscape: Boolean
            get() = getContext()?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE

        val isPortrait: Boolean
            get() = getContext()?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT

        @Suppress("unused")
        fun getScreenRotation(activity: Activity): Int {
            return when (activity.windowManager.defaultDisplay.rotation) {
                Surface.ROTATION_0 -> 0
                Surface.ROTATION_90 -> 90
                Surface.ROTATION_180 -> 180
                Surface.ROTATION_270 -> 270
                else -> 0
            }
        }

        @Suppress("unused")
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

        @Suppress("unused")
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
