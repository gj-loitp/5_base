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

    fun getString(key: String): String? {
        return get(key, String::class.java)
    }

    fun getBoolean(key: String): Boolean? {
        return get(key, Boolean::class.java)
    }

    fun getFloat(key: String): Float? {
        return get(key, Float::class.java)
    }

    fun getInt(key: String): Int? {
        return get(key, Int::class.java)
    }

    fun getLong(key: String): Long? {
        return get(key, Long::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun <T> get(key: String, anonymousClass: Class<T>): T? {
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

    fun putString(key: String, data: String) {
        put(key, data)
    }

    fun putBoolean(key: String, data: Boolean) {
        put(key, data)
    }

    fun putFloat(key: String, data: Float) {
        put(key, data)
    }

    fun putInt(key: String, data: Int) {
        put(key, data)
    }

    fun putLong(key: String, data: Long) {
        put(key, data)
    }

    private fun <T> put(key: String, data: T) {
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
