package com.loitp.core.utilities

import android.content.Context
import com.loitp.core.base.BaseApplication
import com.loitp.model.App
import com.loitp.core.utils.AppUtils

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LPrefUtil {
    companion object {
        private const val PREFERENCES_FILE_NAME = "roygroup"
        private val CHECK_APP_READY = "CHECK_APP_READY" + AppUtils.appVersionCode
        private const val PRE_LOAD = "PRE_LOAD"
        private const val TEXT_SIZE_EPUB_PERCENT = "TEXT_SIZE_EPUB"
        private var JSON_BOOK_ASSET = "JSON_BOOK_ASSET"
        private const val PASS_CODE = "PASS_CODE"
        private const val GG_APP_SETTING = "GG_APP_SETTING"

        //region object
        fun getGGAppSetting(): App {
            val pref = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return BaseApplication.gson.fromJson(
                pref.getString(GG_APP_SETTING, ""),
                App::class.java
            )
        }

        fun setGGAppSetting(user: App) {
            val editor =
                LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putString(GG_APP_SETTING, BaseApplication.gson.toJson(user))
            editor.apply()
        }
        //endregion

        //region string
//        fun getGGAppMsg(): String? {
//            val pref = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
//            return pref.getString(GG_APP_MSG, "")
//        }
//
//        fun setGGAppMsg(value: String?) {
//            val editor =
//                LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
//            editor.putString(GG_APP_MSG, value)
//            editor.apply()
//        }
        //endregion

        //region boolean
        fun getCheckAppReady(): Boolean {
            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return prefs.getBoolean(CHECK_APP_READY, false)
        }

        fun setCheckAppReady(value: Boolean) {
            val editor =
                LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putBoolean(CHECK_APP_READY, value)
            editor.apply()
        }

        fun getPreLoad(): Boolean {
            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return prefs.getBoolean(PRE_LOAD, false)
        }

        fun setPreLoad(value: Boolean) {
            val editor =
                LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putBoolean(PRE_LOAD, value)
            editor.apply()
        }

//        fun getIsShowedDlgWarningYoutubeParser(): Boolean {
//            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
//            return prefs.getBoolean(IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER, false)
//        }
//
//        fun setIsShowedDlgWarningYoutubeParser(value: Boolean) {
//            val editor =
//                LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
//            editor.putBoolean(IS_SHOWED_DLG_WARNING_YOUTUBE_PARSER, value)
//            editor.apply()
//        }
        //endregion

        //region int
//        fun getIndex(): Int {
//            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
//            return prefs.getInt(INDEX, Constants.NOT_FOUND)
//        }
//
//        fun setIndex(value: Int) {
//            val editor =
//                LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
//            editor.putInt(INDEX, value)
//            editor.apply()
//        }

        fun getTextSizePercentEpub(): Int {
            val prefs = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return prefs.getInt(TEXT_SIZE_EPUB_PERCENT, 100) // 100%
        }

        fun setTextSizePercentEpub(value: Int) {
            val editor =
                LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putInt(TEXT_SIZE_EPUB_PERCENT, value)
            editor.apply()
        }
        //endregion

        //region string
        fun getJsonBookAsset(): String? {
            val pref = LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
            return pref.getString(JSON_BOOK_ASSET, null)
        }

        @Suppress("unused")
        fun setJsonBookAsset(value: String) {
            val editor =
                LAppResource.application.getSharedPreferences(PREFERENCES_FILE_NAME, 0).edit()
            editor.putString(JSON_BOOK_ASSET, value)
            editor.apply()
        }

        @Suppress("unused")
        fun savePassCode(str: String) {
            val sharedPref = LAppResource.application.getSharedPreferences(
                PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE
            )
            val editor = sharedPref.edit()
            editor.putString(PASS_CODE, str)
            editor.apply()
        }

        @Suppress("unused")
        fun getPassCode(): String? {
            val sharedPref = LAppResource.application.getSharedPreferences(
                PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE
            )
            val defaultValue = ""
            return sharedPref.getString(PASS_CODE, defaultValue)
        }
        //endregion
    }
}
