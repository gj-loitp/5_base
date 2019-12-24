package vn.loitp.app.activity.api.coroutine

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import vn.loitp.app.app.LApplication

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
object AppPreferences {

    private enum class KEY(val value: String) {
        TOKEN("token")
    }

    private lateinit var sharedPreferences: SharedPreferences

    private const val PREF_NAME = "pref_vinecofarm"

    fun init(app: Application) {
        sharedPreferences = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun clear() = sharedPreferences.edit().clear().commit()

    var token: AccessToken?
        get() = get<AccessToken>(KEY.TOKEN, null)
        set(value) = put(KEY.TOKEN, value)

    private inline fun <reified T : Any> put(key: KEY, t: T?) {
        sharedPreferences.edit().apply {
            when (t) {
                is Boolean -> putBoolean(key.value, t)
                is String -> putString(key.value, t)
                is Float -> putFloat(key.value, t)
                is Int -> putInt(key.value, t)
                is Long -> putLong(key.value, t)
                else -> putString(key.value, LApplication.gson.toJson(t))
            }.apply()
        }
    }

    private inline fun <reified T : Any> get(key: KEY, default: T?): T? =
            sharedPreferences.let {
                when (default) {
                    is Boolean -> it.getBoolean(key.value, default)
                    is String -> it.getString(key.value, default)
                    is Float -> it.getFloat(key.value, default)
                    is Int -> it.getInt(key.value, default)
                    is Long -> it.getLong(key.value, default)
                    else -> it.getString(key.value, null)?.let { json ->
                        LApplication.gson.fromJson(json, T::class.java)
                    } ?: run {
                        null
                    }
                } as T?
            }
}
