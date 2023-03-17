package com.loitp.core.ext

import android.content.Context
import android.content.SharedPreferences
import com.loitp.core.base.BaseApplication
import java.lang.reflect.Type

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

private const val PREFS_NAME = "PrefSecurity"
private const val pw = "roy93group"

fun Context.getStringSecurityPref(
    key: String, defaultValue: String = ""
): String {
    return getPrefSecurityPref(
        key = key,
        anonymousClass = String::class.java,
        defaultValue = defaultValue
    )
}

fun Context.getBooleanSecurityPref(
    key: String, defaultValue: Boolean = false
): Boolean {
    return getPrefSecurityPref(
        key = key,
        anonymousClass = Boolean::class.java,
        defaultValue = defaultValue
    )
}

fun Context.getFloatSecurityPref(
    key: String, defaultValue: Float = 0f
): Float {
    return getPrefSecurityPref(
        key = key,
        anonymousClass = Float::class.java,
        defaultValue = defaultValue
    )
}

fun Context.getIntSecurityPref(
    key: String, defaultValue: Int = 0
): Int {
    return getPrefSecurityPref(key = key, anonymousClass = Int::class.java, defaultValue = defaultValue)
}

fun Context.getLongSecurityPref(
    key: String, defaultValue: Long = 0L
): Long {
    return getPrefSecurityPref(
        key = key,
        anonymousClass = Long::class.java,
        defaultValue = defaultValue
    )
}

fun <T> Context.getObjectSecurityPref(
    key: String, anonymousClass: Class<T>
): T {
    return getPrefSecurityPref(key = key, anonymousClass = anonymousClass, defaultValue = "")
}

fun <T> Context.getObjectListSecurityPref(
    key: String, typeOfT: Type
): ArrayList<T> {
    val mSharedPreferences: SharedPreferences =
        this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val value = mSharedPreferences.getString(key, "")
    if (value?.isEmpty() == true) {
        return ArrayList()
    }
    val originalValue = value.decrypt(password = pw)
    if (originalValue.isNullOrEmpty()) {
        return ArrayList()
    }
    return BaseApplication.gson.fromJson(originalValue, typeOfT)
}

@Suppress("UNCHECKED_CAST")
private fun <T> Context.getPrefSecurityPref(
    key: String, anonymousClass: Class<T>, defaultValue: Any
): T {
    val mSharedPreferences: SharedPreferences =
        this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val defValue = ""
    when (anonymousClass) {
        String::class.java -> {
            val value = mSharedPreferences.getString(key, defValue)
            if (value.isNullOrEmpty()) {
                return defaultValue as T
            }
            val originalValue = value.decrypt(password = pw)
            return originalValue as T
        }
        Boolean::class.java -> {
            val value = mSharedPreferences.getString(key, defValue)
            if (value.isNullOrEmpty()) {
                return defaultValue as T
            }
            val originalValue = value.decrypt(password = pw)
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
            val originalValue = value.decrypt(password = pw)
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
            val originalValue = value.decrypt(password = pw)
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
            val originalValue = value.decrypt(password = pw)
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
            val originalValue = value.decrypt(password = pw)
            if (originalValue.isNullOrEmpty()) {
                return null as T
            }
            return BaseApplication.gson.fromJson(originalValue, anonymousClass)
        }
    }
}

fun <T> Context.putPrefSecurityPref(
    key: String, data: T
) {
    val mSharedPreferences: SharedPreferences =
        this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    if (data is String || data is Boolean || data is Float || data is Int || data is Long) {
        val s = data.toString()
        val newS = s.encrypt(pw)
        val editor = mSharedPreferences.edit()
        editor.putString(key, newS)
        editor.apply()
    } else {
        val s = BaseApplication.gson.toJson(data)
        val newS = s.encrypt(password = pw)
        val editor = mSharedPreferences.edit()
        editor.putString(key, newS)
        editor.apply()
    }
}

fun Context.clearPrefSecurityPref() {
    val mSharedPreferences: SharedPreferences =
        this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    mSharedPreferences.edit().clear().apply()
}
