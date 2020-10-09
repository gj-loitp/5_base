package com.core.utilities

import android.content.Context
import com.R
import com.core.base.BaseApplication
import com.core.common.Constants
import com.model.App
import com.utils.util.AppUtils

/**
 * File created on 17/7/2019.
 *
 * @author loitp
 */
class LPrefUtil {
    companion object {
        private val logTag = LPrefUtil::class.java.simpleName

        private const val PREFERENCES_FILE_NAME = "loitp"
        private val CHECK_APP_READY = "CHECK_APP_READY" + AppUtils.getAppVersionCode()
        private const val PRE_LOAD = "PRE_LOAD"
        const val JSON_LIST_DATA = "JSON_LIST_DATA"
        const val JSON_FAV_DATA = "JSON_FAV_DATA"
        const val JSON_AD_DATA = "JSON_AD_DATA"
        const val FIRST_RUN_APP = "FIRST_RUN_APP"
        const val SAVED_NUMBER_VERSION = "saved.number.version"
        const val NOT_READY_USE_APPLICATION = "not.ready.use.application"
        private const val TEXT_SIZE_EPUB = "TEXT_SIZE_EPUB"
        var JSON_BOOK_ASSET = "JSON_BOOK_ASSET"
        private const val INDEX = "INDEX"
        private const val PASS_CODE = "PASS_CODE"
        private const val GG_APP_SETTING = "GG_APP_SETTING"
        private const val GG_APP_MSG = "GG_APP_MSG"
        private const val IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER = "IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER"

        //region object
        fun getGGAppSetting(): App {
            val pref = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return BaseApplication.gson.fromJson(pref.getString(GG_APP_SETTING, ""), App::class.java)
        }

        fun setGGAppSetting(user: App) {
            val editor = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putString(GG_APP_SETTING, BaseApplication.gson.toJson(user))
            editor.apply()
        }
        //endregion

        //region string
        fun getGGAppMsg(): String? {
            val pref = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return pref.getString(GG_APP_MSG, "")
        }

        fun setGGAppMsg(value: String?) {
            val editor = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putString(GG_APP_MSG, value)
            editor.apply()
        }
        //endregion

        //region boolean
        fun getCheckAppReady(): Boolean {
            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return prefs.getBoolean(CHECK_APP_READY, false)
        }

        fun setCheckAppReady(value: Boolean) {
            val editor = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putBoolean(CHECK_APP_READY, value)
            editor.apply()
        }

        fun getPreLoad(): Boolean {
            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return prefs.getBoolean(PRE_LOAD, false)
        }

        fun setPreLoad(value: Boolean) {
            val editor = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putBoolean(PRE_LOAD, value)
            editor.apply()
        }

        fun getIsShowedDlgWarningYoutubeParser(): Boolean {
            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return prefs.getBoolean(IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER, false)
        }

        fun setIsShowedDlgWarningYoutubeParser(value: Boolean) {
            val editor = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putBoolean(IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER, value)
            editor.apply()
        }
        //endregion

        //region int
        fun getIndex(): Int {
            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return prefs.getInt(INDEX, Constants.NOT_FOUND)
        }

        fun setIndex(value: Int) {
            val editor = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putInt(INDEX, value)
            editor.apply()
        }

        fun getTextSizeEpub(): Int {
            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return prefs.getInt(TEXT_SIZE_EPUB, LAppResource.getDimenValue(R.dimen.text_medium))
        }

        fun setTextSizeEpub(value: Int) {
            val editor = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putInt(TEXT_SIZE_EPUB, value)
            editor.apply()
        }
        //endregion

        //region string
        fun getJsonBookAsset(): String? {
            val pref = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return pref.getString(JSON_BOOK_ASSET, null)
        }

        fun setJsonBookAsset(value: String) {
            val editor = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putString(JSON_BOOK_ASSET, value)
            editor.apply()
        }

        fun savePassCode(str: String) {
            val sharedPref = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(PASS_CODE, str)
            editor.apply()
        }

        fun getPassCode(): String? {
            val sharedPref = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
            val defaultValue = ""
            return sharedPref.getString(PASS_CODE, defaultValue)
        }
        //endregion
    }
}
