package com.loitp.restApi.restClient

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.loitp.core.utilities.LLog
import com.loitp.restApi.DateTypeDeserializer
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.InvalidParameterException
import java.util.* // ktlint-disable no-wildcard-imports
import java.util.concurrent.TimeUnit

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object RestClient {
    @Suppress("unused")
    private const val TIMEOUT_TIME = 1
    private const val CONNECT_TIMEOUT_TIME = 20L
    private const val AUTHORIZATION = "Authorization"
    private var retrofit: Retrofit? = null
    private var restRequestInterceptor = RestRequestInterceptor()

    @JvmOverloads
    fun init(baseApiUrl: String, token: String = "") {
        if (TextUtils.isEmpty(baseApiUrl)) {
            throw InvalidParameterException("baseApiUrl cannot null or empty")
        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(CONNECT_TIMEOUT_TIME, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT_TIME, TimeUnit.SECONDS)
            .addInterceptor(restRequestInterceptor)
            .addInterceptor(CurlInterceptor(object : Logger {
                override fun log(message: String) {
                    LLog.e("Ok2Curl", message)
                }
            }))
            .retryOnConnectionFailure(true)
            .addInterceptor(logging) // <-- this is the important line!
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateTypeDeserializer())
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        if (!TextUtils.isEmpty(token)) {
            addAuthorization(token)
        }
    }

    fun <S> createService(serviceClass: Class<S>): S {
        checkNotNull(retrofit) {
            "Must call init() before use"
        }
        return retrofit!!.create(serviceClass)
    }

    @Suppress("unused")
    fun addHeader(name: String, value: String) {
        restRequestInterceptor.addHeader(name, value)
    }

    @Suppress("unused")
    fun addAuthorization(token: String) {
        addHeader(AUTHORIZATION, token)
    }

    @Suppress("unused")
    fun removeAuthorization() {
        removeHeader(AUTHORIZATION)
    }

    @Suppress("unused")
    fun removeHeader(name: String) {
        restRequestInterceptor.removeHeader(name)
    }

    @Suppress("unused")
    fun hasHeader(name: String): Boolean {
        return restRequestInterceptor.hasHeader(name)
    }
}
