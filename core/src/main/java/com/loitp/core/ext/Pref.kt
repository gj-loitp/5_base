package com.loitp.core.ext

import android.content.Context
import android.content.SharedPreferences
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.Constants
import com.loitp.core.utils.AppUtils
import com.loitp.model.App
import java.lang.reflect.Type

/**
 * Created by Loitp on 08,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun Context.isDarkTheme(): Boolean {
//            return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    return getBoolean(Constants.IS_DARK_THEME, false)
}

fun Context.setDarkTheme(isDarkTheme: Boolean) {
    if (isDarkTheme) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        putBoolean(Constants.IS_DARK_THEME, true)
    } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        putBoolean(Constants.IS_DARK_THEME, false)
    }
}

private const val PREFERENCES_FILE_NAME = "roy93group"
private val CHECK_APP_READY = "CHECK_APP_READY" + AppUtils.appVersionCode
private const val PRE_LOAD = "PRE_LOAD"
private const val TEXT_SIZE_EPUB_PERCENT = "TEXT_SIZE_EPUB"
private var JSON_BOOK_ASSET = "JSON_BOOK_ASSET"
private const val PASS_CODE = "PASS_CODE"
private const val GG_APP_SETTING = "GG_APP_SETTING"
const val KEY_BOOLEAN_IS_CONNECTED_NETWORK = "KEY_BOOLEAN_IS_CONNECTED_NETWORK"

fun Context.getGGAppSetting(): App {
    val pref = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
    return BaseApplication.gson.fromJson(
        pref.getString(GG_APP_SETTING, ""), App::class.java
    )
}

fun Context.setGGAppSetting(user: App) {
    val editor = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
    editor.putString(GG_APP_SETTING, BaseApplication.gson.toJson(user))
    editor.apply()
}

fun Context.getCheckAppReady(): Boolean {
    val prefs = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
    return prefs.getBoolean(CHECK_APP_READY, false)
}

fun Context.setCheckAppReady(value: Boolean) {
    val editor = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
    editor.putBoolean(CHECK_APP_READY, value)
    editor.apply()
}

fun Context.getPreLoad(): Boolean {
    val prefs = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
    return prefs.getBoolean(PRE_LOAD, false)
}

fun Context.setPreLoad(value: Boolean) {
    val editor = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
    editor.putBoolean(PRE_LOAD, value)
    editor.apply()
}

fun Context.getTextSizePercentEpub(): Int {
    val prefs = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
    return prefs.getInt(TEXT_SIZE_EPUB_PERCENT, 100) // 100%
}

fun Context.setTextSizePercentEpub(value: Int) {
    val editor = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
    editor.putInt(TEXT_SIZE_EPUB_PERCENT, value)
    editor.apply()
}

fun Context.getJsonBookAsset(): String? {
    val pref = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
    return pref.getString(JSON_BOOK_ASSET, null)
}

@Suppress("unused")
fun Context.setJsonBookAsset(value: String) {
    val editor = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
    editor.putString(JSON_BOOK_ASSET, value)
    editor.apply()
}

@Suppress("unused")
fun Context.savePassCode(str: String) {
    val sharedPref = this.getSharedPreferences(
        PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
    )
    val editor = sharedPref.edit()
    editor.putString(PASS_CODE, str)
    editor.apply()
}

@Suppress("unused")
fun Context.getPassCode(): String? {
    val sharedPref = this.getSharedPreferences(
        PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
    )
    val defaultValue = ""
    return sharedPref.getString(PASS_CODE, defaultValue)
}

fun Context.getString(
    key: String, defaultValue: String = ""
): String {
    return get(key = key, anonymousClass = String::class.java, defaultValue = defaultValue)
}

fun Context.getBoolean(
    key: String, defaultValue: Boolean = false
): Boolean {
    return get(key = key, anonymousClass = Boolean::class.java, defaultValue = defaultValue)
}

fun Context.getFloat(
    key: String, defaultValue: Float = 0f
): Float {
    return get(key = key, anonymousClass = Float::class.java, defaultValue = defaultValue)
}

fun Context.getInt(
    key: String, defaultValue: Int = 0
): Int {
    return get(key = key, anonymousClass = Int::class.java, defaultValue = defaultValue)
}

fun Context.getLong(
    key: String, defaultValue: Long = 0L
): Long {
    return get(key = key, anonymousClass = Long::class.java, defaultValue = defaultValue)
}

fun <T> Context.getObject(
    key: String, anonymousClass: Class<T>
): T {
    return get(key = key, anonymousClass = anonymousClass, defaultValue = "")
}

fun <T> Context.getObjectList(
    key: String, typeOfT: Type
): ArrayList<T> {
    val mSharedPreferences: SharedPreferences =
        this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    val value = mSharedPreferences.getString(key, "")
    if (value?.isEmpty() == true) {
        return ArrayList()
    }
    return BaseApplication.gson.fromJson(value, typeOfT)
}

@Suppress("UNCHECKED_CAST")
private fun <T> Context.get(
    key: String, anonymousClass: Class<T>, defaultValue: Any
): T {
    val mSharedPreferences: SharedPreferences =
        this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    when (anonymousClass) {
        String::class.java -> {
            return mSharedPreferences.getString(key, defaultValue as String) as T
        }
        Boolean::class.java -> {
            return java.lang.Boolean.valueOf(
                mSharedPreferences.getBoolean(
                    key, defaultValue as Boolean
                )
            ) as T
        }
        Float::class.java -> {
            return java.lang.Float.valueOf(
                mSharedPreferences.getFloat(
                    key, defaultValue as Float
                )
            ) as T
        }
        Int::class.java -> {
            return Integer.valueOf(mSharedPreferences.getInt(key, defaultValue as Int)) as T
        }
        Long::class.java -> {
            return java.lang.Long.valueOf(
                mSharedPreferences.getLong(
                    key, defaultValue as Long
                )
            ) as T
        }
        else -> {
            val json = mSharedPreferences.getString(key, "")
            return BaseApplication.gson.fromJson(json, anonymousClass)
        }
    }
}

fun Context.putString(
    key: String, data: String
) {
    put(key = key, data = data)
}

fun Context.putBoolean(
    key: String, data: Boolean
) {
    put(key = key, data = data)
}

fun Context.putFloat(
    key: String, data: Float
) {
    put(key = key, data = data)
}

fun Context.putInt(
    key: String, data: Int
) {
    put(key = key, data = data)
}

fun Context.putLong(
    key: String, data: Long
) {
    put(key = key, data = data)
}

fun <T> Context.putObject(
    key: String, data: T
) {
    put(key = key, data = data)
}

fun <T> Context.putObjectList(
    key: String, data: T
) {
    put(key = key, data = data)
}

private fun <T> Context.put(
    key: String, data: T
) {
    val mSharedPreferences: SharedPreferences =
        this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    val editor = mSharedPreferences.edit()
    when (data) {
        is String -> {
            editor.putString(key, data)
        }
        is Boolean -> {
            editor.putBoolean(key, data)
        }
        is Float -> {
            editor.putFloat(key, data)
        }
        is Int -> {
            editor.putInt(key, data)
        }
        is Long -> {
            editor.putLong(key, data)
        }
        else -> {
            val json = BaseApplication.gson.toJson(data)
            editor.putString(key, json)
        }
    }
    editor.apply()
}

fun Context.clearPreferences() {
    val mSharedPreferences: SharedPreferences =
        this.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    mSharedPreferences.edit().clear().apply()
}
