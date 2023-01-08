package com.loitp.core.ext

import android.content.Context
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LSharedPrefsUtil
import com.loitp.core.utils.AppUtils
import com.loitp.model.App

/**
 * Created by Loitp on 08,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun isDarkTheme(): Boolean {
//            return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    return LSharedPrefsUtil.instance.getBoolean(Constants.IS_DARK_THEME, false)
}

fun setDarkTheme(isDarkTheme: Boolean) {
    if (isDarkTheme) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        LSharedPrefsUtil.instance.putBoolean(Constants.IS_DARK_THEME, true)
    } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        LSharedPrefsUtil.instance.putBoolean(Constants.IS_DARK_THEME, false)
    }
}

private const val PREFERENCES_FILE_NAME = "roy93group"
private val CHECK_APP_READY = "CHECK_APP_READY" + AppUtils.appVersionCode
private const val PRE_LOAD = "PRE_LOAD"
private const val TEXT_SIZE_EPUB_PERCENT = "TEXT_SIZE_EPUB"
private var JSON_BOOK_ASSET = "JSON_BOOK_ASSET"
private const val PASS_CODE = "PASS_CODE"
private const val GG_APP_SETTING = "GG_APP_SETTING"

fun Context.getGGAppSetting(): App {
    val pref = this.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
    return BaseApplication.gson.fromJson(
        pref.getString(GG_APP_SETTING, ""),
        App::class.java
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
        PREFERENCES_FILE_NAME,
        Context.MODE_PRIVATE
    )
    val editor = sharedPref.edit()
    editor.putString(PASS_CODE, str)
    editor.apply()
}

@Suppress("unused")
fun Context.getPassCode(): String? {
    val sharedPref = this.getSharedPreferences(
        PREFERENCES_FILE_NAME,
        Context.MODE_PRIVATE
    )
    val defaultValue = ""
    return sharedPref.getString(PASS_CODE, defaultValue)
}
