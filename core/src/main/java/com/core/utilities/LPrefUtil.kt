package com.core.utilities

import android.content.Context
import com.core.common.Constants
import com.function.youtubeparser.models.utubechannel.UtubeChannel
import com.google.gson.Gson
import com.model.App
import com.utils.util.AppUtils

/**
 * File created on 17/7/2019.
 *
 * @author loitp
 */
object LPrefUtil {
    private val TAG = javaClass.simpleName

    private val PREFERENCES_FILE_NAME = "loitp"
    private val CHECK_APP_READY = "CHECK_APP_READY" + AppUtils.getAppVersionCode()
    private val PRE_LOAD = "PRE_LOAD"
    val JSON_LIST_DATA = "JSON_LIST_DATA"
    val JSON_FAV_DATA = "JSON_FAV_DATA"
    val JSON_AD_DATA = "JSON_AD_DATA"
    val FIRST_RUN_APP = "FIRST_RUN_APP"
    val SAVED_NUMBER_VERSION = "saved.number.version"
    val NOT_READY_USE_APPLICATION = "not.ready.use.application"
    val TEXT_SIZE_EPUB = "TEXT_SIZE_EPUB"
    var JSON_BOOK_ASSET = "JSON_BOOK_ASSET"
    val INDEX = "INDEX"
    val PASS_CODE = "PASS_CODE"
    val UZVIDEO_WIDTH = "UZVIDEO_WIDTH"
    val UZVIDEO_HEIGHT = "UZVIDEO_HEIGHT"
    val GG_APP_SETTING = "GG_APP_SETTING"
    val GG_APP_MSG = "GG_APP_MSG"
    val IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER = "IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER"
    val YOUTUBE_CHANNEL_LIST = "YOUTUBE_CHANNEL_LIST"
    val TIME_GET_YOUTUBE_CHANNEL_LIST_SUCCESS = "TIME_GET_YOUTUBE_CHANNEL_LIST_SUCCESS"

    //region object
    fun getGGAppSetting(context: Context): App {
        val pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return Gson().fromJson(pref.getString(GG_APP_SETTING, ""), App::class.java)
    }

    fun setGGAppSetting(context: Context, user: App) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putString(GG_APP_SETTING, Gson().toJson(user))
        editor.apply()
    }

    fun getYoutubeChannelList(context: Context): UtubeChannel? {
        val pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        val json = pref.getString(YOUTUBE_CHANNEL_LIST, "")
        return if (json.isNullOrEmpty()) {
            null
        } else Gson().fromJson(json, UtubeChannel::class.java)
    }

    fun setYoutubeChannelList(context: Context, utubeChannel: UtubeChannel) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putString(YOUTUBE_CHANNEL_LIST, Gson().toJson(utubeChannel))
        editor.apply()
    }
    //endregion

    //region string
    fun getGGAppMsg(context: Context): String? {
        val pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return pref.getString(GG_APP_MSG, "")
    }

    fun setGGAppMsg(context: Context, value: String?) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putString(GG_APP_MSG, value)
        editor.apply()
    }
    //endregion

    //region boolean
    fun getCheckAppReady(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return prefs.getBoolean(CHECK_APP_READY, false)
    }

    fun setCheckAppReady(context: Context, value: Boolean) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putBoolean(CHECK_APP_READY, value)
        editor.apply()
    }

    fun getPreLoad(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return prefs.getBoolean(PRE_LOAD, false)
    }

    fun setPreLoad(context: Context, value: Boolean) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putBoolean(PRE_LOAD, value)
        editor.apply()
    }

    fun getIsShowedDlgWarningYoutubeParser(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return prefs.getBoolean(IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER, false)
    }

    fun setIsShowedDlgWarningYoutubeParser(context: Context, value: Boolean) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putBoolean(IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER, value)
        editor.apply()
    }
    //endregion

    //region long
    fun getTimeGetYoutubeChannelListSuccess(context: Context): Long {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return prefs.getLong(TIME_GET_YOUTUBE_CHANNEL_LIST_SUCCESS, 0)
    }

    fun setTimeGetYoutubeChannelListSuccess(context: Context, value: Long) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putLong(TIME_GET_YOUTUBE_CHANNEL_LIST_SUCCESS, value)
        editor.apply()
    }
    //endregion

    //region int
    fun getIndex(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return prefs.getInt(INDEX, Constants.NOT_FOUND)
    }

    fun setIndex(context: Context, value: Int) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putInt(INDEX, value)
        editor.apply()
    }

    fun getTextSizeEpub(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return prefs.getInt(TEXT_SIZE_EPUB, 110)
    }

    fun setTextSizeEpub(context: Context, value: Int) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putInt(TEXT_SIZE_EPUB, value)
        editor.apply()
    }

    fun getUZvideoWidth(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return prefs.getInt(UZVIDEO_WIDTH, 16)
    }

    fun setUZvideoWidth(context: Context, value: Int) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putInt(UZVIDEO_WIDTH, value)
        editor.apply()
    }

    fun getUzvideoHeight(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return prefs.getInt(UZVIDEO_HEIGHT, 9)
    }

    fun setUzvideoHeight(context: Context, value: Int) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putInt(UZVIDEO_HEIGHT, value)
        editor.apply()
    }
    //endregion

    //region string
    fun getJsonBookAsset(context: Context): String? {
        val pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
        return pref.getString(JSON_BOOK_ASSET, null)
    }

    fun setJsonBookAsset(context: Context, value: String) {
        val editor = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
        editor.putString(JSON_BOOK_ASSET, value)
        editor.apply()
    }

    fun savePassCode(context: Context, str: String) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(PASS_CODE, str)
        editor.apply()
    }

    fun getPassCode(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        val defaultValue = ""
        return sharedPref.getString(PASS_CODE, defaultValue)
    }
    //endregion
}
