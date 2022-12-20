package com.loitpcore.core.utilities

import android.content.Context
import android.content.SharedPreferences
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.utils.util.AppUtils
import com.loitpcore.utils.util.DeviceUtils
import java.lang.reflect.Type

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LEncryptionSharedPrefsUtil private constructor() {
    private val mSharedPreferences: SharedPreferences =
        LAppResource.application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val logTag = "EncryptionSharedPrefs"
        private val PREFS_NAME = logTag + AppUtils.appPackageName
        private var mInstance: LEncryptionSharedPrefsUtil? = null
        private val pw = LEncryptionUtil.encodeBase64(PREFS_NAME + DeviceUtils.androidID) + "1993"

        val instance: LEncryptionSharedPrefsUtil
            get() {
                if (mInstance == null) {
                    mInstance = LEncryptionSharedPrefsUtil()
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
        val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
        if (originalValue.isNullOrEmpty()) {
            return ArrayList()
        }
        return BaseApplication.gson.fromJson(originalValue, typeOfT)
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun <T> get(
        key: String,
        anonymousClass: Class<T>,
        defaultValue: Any
    ): T {
        val defValue = ""
        when (anonymousClass) {
            String::class.java -> {
                val value = mSharedPreferences.getString(key, defValue)
                if (value.isNullOrEmpty()) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                return originalValue as T
            }
            Boolean::class.java -> {
                val value = mSharedPreferences.getString(key, defValue)
                if (value.isNullOrEmpty()) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return originalValue.toBoolean() as T
            }
            Float::class.java -> {
                val value = mSharedPreferences.getString(key, defValue)
                if (value.isNullOrEmpty()) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return originalValue.toFloat() as T
            }
            Int::class.java -> {
                val value = mSharedPreferences.getString(key, defValue)
                if (value.isNullOrEmpty()) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return originalValue.toInt() as T
            }
            Long::class.java -> {
                val value = mSharedPreferences.getString(key, defValue)
                if (value.isNullOrEmpty()) {
                    return defaultValue as T
                }
                val originalValue = LEncryptionUtil.decrypt(cipherText = value, password = pw)
                if (originalValue.isNullOrEmpty()) {
                    return defaultValue as T
                }
                return originalValue.toLong() as T
            }
            else -> {
                val value = mSharedPreferences.getString(key, defValue)
                if (value.isNullOrEmpty()) {
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

    fun <T> put(
        key: String,
        data: T
    ) {
        if (data is String ||
            data is Boolean ||
            data is Float ||
            data is Int ||
            data is Long
        ) {
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
