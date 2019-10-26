package com.core.utilities

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.utils.util.AppUtils
import com.utils.util.DeviceUtils
import com.utils.util.Utils

class LEncryptionSharedPrefsUtil private constructor() {
    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = Utils.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getString(key: String): String {
        return get(key, String::class.java)
    }

    fun getBoolean(key: String): Boolean {
        return get(key, Boolean::class.java)
    }

    fun getFloat(key: String): Float {
        return get(key, Float::class.java)
    }

    fun getInt(key: String): Int {
        return get(key, Int::class.java)
    }

    fun getLong(key: String): Long {
        return get(key, Long::class.java)
    }

    fun <T> getObject(key: String, anonymousClass: Class<T>): T {
        return get(key, anonymousClass)
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun <T> get(key: String, anonymousClass: Class<T>): T {
        when (anonymousClass) {
            String::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return value as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getString $value\n$orginalValue")
                return orginalValue as T
            }
            Boolean::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                val defaultValue = false
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getBoolean $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return orginalValue.toBoolean() as T
            }
            Float::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                val defaultValue = 0f
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getFloat $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return orginalValue.toFloat() as T
            }
            Int::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                val defaultValue = 0
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getFloat $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return orginalValue.toInt() as T
            }
            Long::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                val defaultValue = 0L
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getFloat $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return orginalValue.toLong() as T
            }
            else -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return null as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getFloat $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return null as T
                }
                return Gson().fromJson(orginalValue, anonymousClass)
            }
        }
    }

    fun getString(key: String, defaultValue: String): String {
        return get(key, String::class.java, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return get(key, Boolean::class.java, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return get(key, Float::class.java, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return get(key, Int::class.java, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return get(key, Long::class.java, defaultValue)
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun <T> get(key: String, anonymousClass: Class<T>, defaultValue: T): T {
        when (anonymousClass) {
            String::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getString $value\n$orginalValue")
                return orginalValue as T
            }
            Boolean::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getBoolean $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return orginalValue.toBoolean() as T
            }
            Float::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getFloat $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return orginalValue.toFloat() as T
            }
            Int::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getFloat $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return orginalValue.toInt() as T
            }
            Long::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getFloat $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return orginalValue.toLong() as T
            }
            else -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return null as T
                }
                val orginalValue = LEncryptionUtil.decrypt(value, pw)
                LLog.d(TAG, "getFloat $value\n$orginalValue")
                if (orginalValue.isNullOrEmpty()) {
                    return null as T
                }
                return Gson().fromJson(orginalValue, anonymousClass)
            }
        }
    }

    fun <T> put(key: String, data: T) {
        if (data is String
                || data is Boolean
                || data is Float
                || data is Int
                || data is Long) {
            val s = data.toString()
            val newS = LEncryptionUtil.encrypt(s, pw)
            val editor = mSharedPreferences.edit()
            editor.putString(key, newS)
            //LLog.d(TAG, "putString $key -> $data")
            editor.apply()
        } else {
            val s = Gson().toJson(data)
            val newS = LEncryptionUtil.encrypt(s, pw)
            val editor = mSharedPreferences.edit()
            editor.putString(key, newS)
            //LLog.d(TAG, "putString $key -> $data")
            editor.apply()
        }
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }

    companion object {
        private val TAG = "TAGLEncryptionSharedPrefsUtil"
        private val PREFS_NAME = AppUtils.getAppPackageName() + TAG
        private var mInstance: LEncryptionSharedPrefsUtil? = null
        private val pw = DeviceUtils.getAndroidID()

        val instance: LEncryptionSharedPrefsUtil
            get() {
                if (mInstance == null) {
                    mInstance = LEncryptionSharedPrefsUtil()
                }
                return mInstance!!
            }
    }
}
