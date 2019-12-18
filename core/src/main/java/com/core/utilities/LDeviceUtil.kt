package com.core.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Vibrator
import android.provider.Settings
import android.view.KeyCharacterMap
import android.view.KeyEvent
import java.io.File
import java.util.*


/**
 * File created on 11/14/2016.
 *
 * @author loitp
 */
object LDeviceUtil {

    private val TAG = LDeviceUtil::class.java.simpleName

    val isNavigationBarAvailable: Boolean
        get() {
            val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            val hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME)
            return !(hasBackKey && hasHomeKey)
        }

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
            clipboard.setPrimaryClip(clip)
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

    fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)
    }

    fun getAvailableSpaceInMb(context: Context): Int {
        val freeBytesExternal = File(context.getExternalFilesDir(null).toString()).freeSpace
        val freeMb = (freeBytesExternal / (1024 * 1024)).toInt()
        //val totalSize = File(context.getExternalFilesDir(null).toString()).totalSpace
        //val totalMb = (totalSize / (1024 * 1024)).toInt()
        return freeMb
    }

    fun getAvailableRAM(context: Context): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(memoryInfo)
        val availableMegs = memoryInfo.availMem / 1048576L
        val percentAvail = memoryInfo.availMem / memoryInfo.totalMem
        //LLog.d(TAG, "percentAvail $percentAvail")
        return availableMegs
    }
}
