package com.loitp.views.layout.floatDrag

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue
import android.view.ViewConfiguration
import kotlin.math.roundToInt

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object DisplayUtil {
    @JvmStatic
    fun getScreenContentWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    @JvmStatic
    fun getScreenContentHeight(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }

    @JvmStatic
    fun getScreenHardwareWidth(context: Context): Int {
        val contentWidth = getScreenContentWidth(context)
        return if (isLandscape(context)) {
            contentWidth + getNavigationBarHeight(context)
        } else {
            contentWidth
        }
    }

    @JvmStatic
    fun getScreenHardwareHeight(context: Context): Int {
        val contentHeight = getScreenContentHeight(context)
        return if (isLandscape(context)) {
            contentHeight
        } else {
            contentHeight + getNavigationBarHeight(context)
        }
    }

    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    @JvmStatic
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        val resources = context.resources
        try {
            @SuppressLint("PrivateApi") val clazz = Class.forName("com.android.internal.R\$dimen")
            val obj = clazz.newInstance()
            val height = clazz.getField("status_bar_height")[obj]?.toString()?.toInt() ?: 0
            statusHeight = resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusHeight = resources.getDimensionPixelSize(resourceId)
            }
        }
        return statusHeight
    }

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    @JvmStatic
    fun getNavigationBarHeight(context: Context): Int {
        var result = 0
        if (hasNavBar(context)) {
            val res = context.resources
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    @SuppressLint("DiscouragedApi")
    @Suppress("unused")
    fun hasNavBar(context: Context): Boolean {
        val res = context.resources
        val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
        return if (resourceId != 0) {
            var hasNav = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride = navBarOverride
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            hasNav
        } else { // fallback
            !ViewConfiguration.get(context).hasPermanentMenuKey()
        }
    }

    private val navBarOverride: String?
        get() {
            var sNavBarOverride: String? = null
            try {
                @SuppressLint("PrivateApi") val aClass =
                    Class.forName("android.os.SystemProperties")
                val m = aClass.getDeclaredMethod("get", String::class.java)
                m.isAccessible = true
                sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return sNavBarOverride
        }

    fun dp2px(
        context: Context,
        dps: Int
    ): Int {
        return (dps.toFloat() * getDensityDpiScale(context)).roundToInt()
    }

    @Suppress("unused")
    fun px2dp(
        context: Context,
        pixels: Int
    ): Int {
        return (pixels.toFloat() / getDensityDpiScale(context)).roundToInt()
    }

    private fun getDensityDpiScale(context: Context): Float {
        return context.resources.displayMetrics.xdpi / 160.0f
    }

    fun applyDimension(
        unit: Int,
        value: Float
    ): Int {
        return TypedValue.applyDimension(unit, value, Resources.getSystem().displayMetrics).toInt()
    }
}
