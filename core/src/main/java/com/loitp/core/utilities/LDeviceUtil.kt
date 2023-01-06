package com.loitp.core.utilities

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
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LDeviceUtil {

    companion object {

        val isNavigationBarAvailable: Boolean
            get() {
                val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
                val hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME)
                return !(hasBackKey && hasHomeKey)
            }

        fun isTablet(): Boolean {
            return LAppResource.application.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }

        @Suppress("DEPRECATION")
        @SuppressLint("ObsoleteSdkInt")
        fun setClipboard(text: String): String {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                val clipboard =
                    LAppResource.application.getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
                clipboard.text = text
            } else {
                val clipboard =
                    LAppResource.application.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("Copy", text)
                clipboard.setPrimaryClip(clip)
            }
            return text
        }

        @JvmStatic
        fun vibrate(length: Int = 300) {
            val v = LAppResource.application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(length.toLong())
        }

        @JvmStatic
        fun vibrate() {
            vibrate(length = 200)
        }

        //rung device trong bao lau
        fun vibrate(milliseconds: Long) {
            val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    LAppResource.application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                LAppResource.application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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

        fun getRandomNumber(max: Int): Int {
            val r = Random()
            return r.nextInt(max)
        }

        fun getRandomString(maxLength: Int): String {
            val generator = Random()
            val randomStringBuilder = StringBuilder()
            val randomLength = generator.nextInt(maxLength)
            var tempChar: Char
            for (i in 0 until randomLength) {
                tempChar = (generator.nextInt(96) + 32).toChar()
                randomStringBuilder.append(tempChar)
            }
            return randomStringBuilder.toString()
        }

        fun isCanOverlay(): Boolean {
            return Settings.canDrawOverlays(LAppResource.application)
        }

        fun isEmulator(): Boolean {
            return (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || Build.MODEL.contains(
                "google_sdk"
            ) || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains(
                "Genymotion"
            ) || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") || "google_sdk" == Build.PRODUCT)
        }

        @SuppressLint("HardwareIds")
        fun getDeviceId(context: Context?): String? {
            if (context == null) {
                return null
            }
            val androidId = Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ANDROID_ID
            )
            if (androidId.isNotEmpty()) {
                return androidId
            }

            val uniquePseudoID =
                "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.DEVICE.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.TAGS.length % 10 + Build.TYPE.length % 10 + Build.USER.length % 10
            val serial = Build.getRadioVersion()
            return UUID(uniquePseudoID.hashCode().toLong(), serial.hashCode().toLong()).toString()
        }
    }
}
