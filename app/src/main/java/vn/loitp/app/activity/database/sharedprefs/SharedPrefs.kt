package vn.loitp.app.activity.database.sharedprefs

import android.content.Context
import android.content.SharedPreferences

import com.core.utilities.LLog
import com.utils.util.AppUtils
import com.utils.util.Utils

class SharedPrefs private constructor() {
    private val TAG = "TAG" + javaClass.simpleName
    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = Utils.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String, anonymousClass: Class<T>): T? {
        when (anonymousClass) {
            String::class.java -> {
                //LLog.d(TAG, "getString $key")
                return mSharedPreferences.getString(key, "") as T
            }
            Boolean::class.java -> {
                //LLog.d(TAG, "getBoolean $key")
                return java.lang.Boolean.valueOf(mSharedPreferences.getBoolean(key, false)) as T
            }
            Float::class.java -> {
                //LLog.d(TAG, "getFloat $key")
                return java.lang.Float.valueOf(mSharedPreferences.getFloat(key, 0f)) as T
            }
            Int::class.java -> {
                //LLog.d(TAG, "getInteger $key")
                return Integer.valueOf(mSharedPreferences.getInt(key, 0)) as T
            }
            Long::class.java -> {
                //LLog.d(TAG, "getLong $key")
                return java.lang.Long.valueOf(mSharedPreferences.getLong(key, 0)) as T
            }
            else -> LLog.d(TAG, "get -> do nothing: " + anonymousClass.simpleName)
        }
        return null
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> {
                editor.putString(key, data)
                //LLog.d(TAG, "putString $key -> $data")
            }
            is Boolean -> {
                editor.putBoolean(key, data)
                //LLog.d(TAG, "putBoolean $key -> $data")
            }
            is Float -> {
                editor.putFloat(key, data)
                //LLog.d(TAG, "putFloat $key -> $data")
            }
            is Int -> {
                editor.putInt(key, data)
                //LLog.d(TAG, "putInt $key -> $data")
            }
            is Long -> {
                editor.putLong(key, data)
                //LLog.d(TAG, "putLong $key -> $data")
            }
            else -> LLog.d(TAG, "put -> do nothing")
        }
        editor.apply()
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }

    companion object {
        private val PREFS_NAME = AppUtils.getAppPackageName()
        private var mInstance: SharedPrefs? = null

        val instance: SharedPrefs
            get() {
                if (mInstance == null) {
                    mInstance = SharedPrefs()
                }
                return mInstance!!
            }
    }
}
