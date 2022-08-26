package com.loitpcore.core.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Vibrator
import android.provider.Settings
import android.view.KeyCharacterMap
import android.view.KeyEvent
import java.util.* // ktlint-disable no-wildcard-imports

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
            return (
                    Build.FINGERPRINT.startsWith("generic") ||
                            Build.FINGERPRINT.startsWith("unknown") ||
                            Build.MODEL.contains("google_sdk") ||
                            Build.MODEL.contains("Emulator") ||
                            Build.MODEL.contains("Android SDK built for x86") ||
                            Build.MANUFACTURER.contains("Genymotion") ||
                            Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
                            "google_sdk" == Build.PRODUCT
                    )
        }
    }
}
