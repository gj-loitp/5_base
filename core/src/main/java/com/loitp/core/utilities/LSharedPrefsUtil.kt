package com.loitp.core.utilities

import android.content.Context
import android.content.SharedPreferences
import com.loitp.core.base.BaseApplication
import com.loitp.core.ext.LAppResource.application
import com.loitp.core.utils.AppUtils
import java.lang.reflect.Type

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LSharedPrefsUtil private constructor() {
    private val mSharedPreferences: SharedPreferences =
        application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private val PREFS_NAME = AppUtils.appPackageName
        private var mInstance: LSharedPrefsUtil? = null

        const val KEY_BOOLEAN_IS_CONNECTED_NETWORK = "KEY_BOOLEAN_IS_CONNECTED_NETWORK"

        val instance: LSharedPrefsUtil
            get() {
                if (mInstance == null) {
                    mInstance = LSharedPrefsUtil()
                }
                return mInstance!!
            }
    }

    fun getString(
        key: String,
        defaultValue: String = ""
    ): String {
        return get(key = key, anonymousClass = String::class.java, defaultValue = defaultValue)
    }

    fun getBoolean(
        key: String,
        defaultValue: Boolean = false
    ): Boolean {
        return get(key = key, anonymousClass = Boolean::class.java, defaultValue = defaultValue)
    }

    fun getFloat(
        key: String,
        defaultValue: Float = 0f
    ): Float {
        return get(key = key, anonymousClass = Float::class.java, defaultValue = defaultValue)
    }

    fun getInt(
        key: String,
        defaultValue: Int = 0
    ): Int {
        return get(key = key, anonymousClass = Int::class.java, defaultValue = defaultValue)
    }

    fun getLong(
        key: String,
        defaultValue: Long = 0L
    ): Long {
        return get(key = key, anonymousClass = Long::class.java, defaultValue = defaultValue)
    }

    fun <T> getObject(
        key: String,
        anonymousClass: Class<T>
    ): T {
        return get(key = key, anonymousClass = anonymousClass, defaultValue = "")
    }

    fun <T> getObjectList(
        key: String,
        typeOfT: Type
    ): ArrayList<T> {
        val value = mSharedPreferences.getString(key, "")
        if (value?.isEmpty() == true) {
            return ArrayList()
        }
        return BaseApplication.gson.fromJson(value, typeOfT)
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun <T> get(
        key: String,
        anonymousClass: Class<T>,
        defaultValue: Any
    ): T {
        when (anonymousClass) {
            String::class.java -> {
                return mSharedPreferences.getString(key, defaultValue as String) as T
            }
            Boolean::class.java -> {
                return java.lang.Boolean.valueOf(
                    mSharedPreferences.getBoolean(
                        key,
                        defaultValue as Boolean
                    )
                ) as T
            }
            Float::class.java -> {
                return java.lang.Float.valueOf(
                    mSharedPreferences.getFloat(
                        key,
                        defaultValue as Float
                    )
                ) as T
            }
            Int::class.java -> {
                return Integer.valueOf(mSharedPreferences.getInt(key, defaultValue as Int)) as T
            }
            Long::class.java -> {
                return java.lang.Long.valueOf(
                    mSharedPreferences.getLong(
                        key,
                        defaultValue as Long
                    )
                ) as T
            }
            else -> {
                val json = mSharedPreferences.getString(key, "")
                return BaseApplication.gson.fromJson(json, anonymousClass)
            }
        }
    }

    fun putString(
        key: String,
        data: String
    ) {
        put(key = key, data = data)
    }

    fun putBoolean(
        key: String,
        data: Boolean
    ) {
        put(key = key, data = data)
    }

    fun putFloat(
        key: String,
        data: Float
    ) {
        put(key = key, data = data)
    }

    fun putInt(
        key: String,
        data: Int
    ) {
        put(key = key, data = data)
    }

    fun putLong(
        key: String,
        data: Long
    ) {
        put(key = key, data = data)
    }

    fun <T> putObject(
        key: String,
        data: T
    ) {
        put(key = key, data = data)
    }

    fun <T> putObjectList(
        key: String,
        data: T
    ) {
        put(key = key, data = data)
    }

    private fun <T> put(
        key: String,
        data: T
    ) {
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

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }
}
