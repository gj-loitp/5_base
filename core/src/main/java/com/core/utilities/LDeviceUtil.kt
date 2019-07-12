package com.core.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Vibrator
import android.provider.Settings
import android.view.KeyCharacterMap
import android.view.KeyEvent
import java.util.*


/**
 * File created on 11/14/2016.
 *
 * @author loitp
 */
class LDeviceUtil {

    /*
    check device has navigation bar
     */
    //LLog.dialog(TAG, "isNavigationBarAvailable: " + (!(hasBackKey && hasHomeKey)));
    val isNavigationBarAvailable: Boolean
        get() {
            val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            val hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME)
            return !(hasBackKey && hasHomeKey)
        }

    companion object {
        private val TAG = LDeviceUtil::class.java.simpleName

        fun isTablet(activity: Activity): Boolean {
            return activity.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }

        /*
        get current android version
        @return int
        */
        fun getCurrentAndroidVersion(activity: Activity): Int {
            val thisVersion: Int
            thisVersion = try {
                val pi = activity.packageManager.getPackageInfo(activity.packageName, 0)
                pi.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                1
            }
            return thisVersion
        }

        @SuppressLint("ObsoleteSdkInt")
        fun setClipboard(context: Context, text: String) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
                clipboard.text = text
            } else {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("Copy", text)
                clipboard.primaryClip = clip
            }
        }

        @JvmStatic
        fun vibrate(context: Context, length: Int = 300) {
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(length.toLong())
        }

        @JvmStatic
        fun vibrate(context: Context) {
            vibrate(context, 200)
        }

        fun getRandomNumber(max: Int): Int {
            val r = Random()
            return r.nextInt(max)
        }

        fun isCanOverlay(activity: Activity?): Boolean {
            if (activity == null) {
                return false
            }
            return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity))
        }
    }
}
