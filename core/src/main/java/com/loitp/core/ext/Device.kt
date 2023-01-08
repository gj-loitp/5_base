package com.loitp.core.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.loitp.func.wallpo.Wallpo
import java.util.*

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun isNavigationBarAvailable(): Boolean {
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
    val hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME)
    return !(hasBackKey && hasHomeKey)
}

fun Context.isTablet(): Boolean {
    return this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
}

@Suppress("DEPRECATION")
@SuppressLint("ObsoleteSdkInt")
fun Context.setClipboard(text: String): String {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
        val clipboard =
            this.getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
        clipboard.text = text
    } else {
        val clipboard =
            this.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("Copy", text)
        clipboard.setPrimaryClip(clip)
    }
    return text
}

fun Context.vibrate(length: Int = 300) {
    val v = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    v.vibrate(length.toLong())
}

fun Context.vibrate() {
    this.vibrate(length = 200)
}

//rung device trong bao lau
fun Context.vibrate(milliseconds: Long) {
    val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION") this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vib.vibrate(
            VibrationEffect.createOneShot(
                /* milliseconds = */ milliseconds,
                /* amplitude = */ VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }
}

fun Context.isCanOverlay(): Boolean {
    return Settings.canDrawOverlays(this)
}

fun isEmulator(): Boolean {
    return (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || Build.MODEL.contains(
        "google_sdk"
    ) || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains(
        "Genymotion"
    ) || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") || "google_sdk" == Build.PRODUCT)
}

@SuppressLint("HardwareIds")
fun Context?.getDeviceId(): String? {
    if (this == null) {
        return null
    }
    val androidId = Settings.Secure.getString(
        this.contentResolver, Settings.Secure.ANDROID_ID
    )
    if (androidId.isNotEmpty()) {
        return androidId
    }

    val uniquePseudoID =
        "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.DEVICE.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.TAGS.length % 10 + Build.TYPE.length % 10 + Build.USER.length % 10
    val serial = Build.getRadioVersion()
    return UUID(uniquePseudoID.hashCode().toLong(), serial.hashCode().toLong()).toString()
}

fun Context.dip2px(dpValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dip(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.px2sp(pxValue: Float): Int {
    val fontScale = this.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val fontScale = this.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

fun Context.getDialogW(): Int {
    val dm = this.resources.displayMetrics
    return dm.widthPixels - 100
}

fun Context.getScreenW(): Int {
    val dm = this.resources.displayMetrics
    return dm.widthPixels
}

fun Context.getScreenH(): Int {
    val dm = this.resources.displayMetrics
    return dm.heightPixels
}

fun Context.toggleKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) {
        imm.toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun ImageView.setWallpaperAndLockScreen(
    isSetWallpaper: Boolean = true,
    isSetLockScreen: Boolean = true,
) {
    if (isSetWallpaper) {
        Wallpo.setMainScreenWallpaper(
            context = this.context,
            imageView = this,
        )
    }
    if (isSetLockScreen) {
        Wallpo.setLockScreenWallpaper(
            context = this.context,
            imageView = this,
        )
    }
}

fun Context.setWallpaperAndLockScreen(
    color: Int,
    isSetWallpaper: Boolean = true,
    isSetLockScreen: Boolean = true,
) {
    if (isSetWallpaper) {
        Wallpo.setMainScreenWallpaper(
            context = this,
            color = color,
        )
    }
    if (isSetLockScreen) {
        Wallpo.setLockScreenWallpaper(
            context = this,
            color = color,
        )
    }
}
