package com.core.utilities

import android.content.Context
import android.content.SharedPreferences
import com.core.base.BaseApplication
import com.utils.util.AppUtils
import com.utils.util.DeviceUtils
import java.lang.reflect.Type

class LEncryptionSharedPrefsUtil private constructor() {
    private val mSharedPreferences: SharedPreferences

    companion object {
        private const val logTag = "EncryptionSharedPrefs"
        private val PREFS_NAME = logTag + AppUtils.appPackageName
        private var mInstance: LEncryptionSharedPrefsUtil? = null
        private val pw = LEncryptionUtil.encodeBase64(PREFS_NAME + DeviceUtils.getAndroidID() + DeviceUtils.getMacAddress()) + "1993"

        val instance: LEncryptionSharedPrefsUtil
            get() {
                if (mInstance == null) {
                    mInstance = LEncryptionSharedPrefsUtil()
                }
                return mInstance!!
            }
    }

    init {
        mSharedPreferences = LAppResource.application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getString(key: String): String {
        return get(key = key, anonymousClass = String::class.java)
    }

    fun getBoolean(key: String): Boolean {
        return get(key = key, anonymousClass = Boolean::class.java)
    }

    fun getFloat(key: String): Float {
        return get(key = key, anonymousClass = Float::class.java)
    }

    fun getInt(key: String): Int {
        return get(key = key, anonymousClass = Int::class.java)
    }

    fun getLong(key: String): Long {
        return get(key = key, anonymousClass = Long::class.java)
    }

    fun <T> getObject(key: String, anonymousClass: Class<T>): T {
        return get(key = key, anonymousClass = anonymousClass)
    }

    fun <T> getObjectList(key: String, anonymousClass: Class<T>, typeOfT: Type): ArrayList<T> {
        val value = mSharedPreferences.getString(key, "")
        if (value?.isEmpty() == true) {
            return ArrayList()
        }
        val originalValue = LEncryptionUtil.decrypt(value, pw)
        if (originalValue.isNullOrEmpty()) {
            return ArrayList()
        }
        return BaseApplication.gson.fromJson(originalValue, typeOfT)
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun <T> get(key: String, anonymousClass: Class<T>): T {
        when (anonymousClass) {
            String::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return value as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                return originalValue as T
            }
            Boolean::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                val defaultValue = false
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return originalValue.toBoolean() as T
            }
            Float::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                val defaultValue = 0f
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return originalValue.toFloat() as T
            }
            Int::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                val defaultValue = 0
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return originalValue.toInt() as T
            }
            Long::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                val defaultValue = 0L
                if (value?.isEmpty() == true) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return originalValue.toLong() as T
            }
            else -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return null as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return null as T
                }
                return BaseApplication.gson.fromJson(originalValue, anonymousClass)
            }
        }
    }

    fun getString(key: String, defaultValue: String): String {
        return get(key = key, anonymousClass = String::class.java, defaultValue = defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return get(key = key, anonymousClass = Boolean::class.java, defaultValue = defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return get(key = key, anonymousClass = Float::class.java, defaultValue = defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return get(key = key, anonymousClass = Int::class.java, defaultValue = defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return get(key = key, anonymousClass = Long::class.java, defaultValue = defaultValue)
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun <T> get(key: String, anonymousClass: Class<T>, defaultValue: T): T {
        when (anonymousClass) {
            String::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                return originalValue as T
            }
            Boolean::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue
                }
                return originalValue.toBoolean() as T
            }
            Float::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue
                }
                return originalValue.toFloat() as T
            }
            Int::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue
                }
                return originalValue.toInt() as T
            }
            Long::class.java -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return defaultValue
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue
                }
                return originalValue.toLong() as T
            }
            else -> {
                val value = mSharedPreferences.getString(key, "")
                if (value?.isEmpty() == true) {
                    return null as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return null as T
                }
                return BaseApplication.gson.fromJson(originalValue, anonymousClass)
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
            editor.apply()
        } else {
            val s = BaseApplication.gson.toJson(data)
            val newS = LEncryptionUtil.encrypt(plaintext = s, password = pw)
            val editor = mSharedPreferences.edit()
            editor.putString(key, newS)
            editor.apply()
        }
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }
}
