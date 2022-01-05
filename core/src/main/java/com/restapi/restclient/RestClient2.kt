package com.restapi.restclient

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.restapi.DateTypeDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.InvalidParameterException
import java.util.*
import java.util.concurrent.TimeUnit

object RestClient2 {
    private const val TIMEOUT_TIME = 1
    private const val CONNECT_TIMEOUT_TIME = 20L
    private const val AUTHORIZATION = "Authorization"
    private var retrofit: Retrofit? = null
    private var restRequestInterceptor = RestRequestInterceptor()

    @JvmOverloads
    fun init(baseApiUrl: String, token: String? = "") {
        if (TextUtils.isEmpty(baseApiUrl)) {
            throw InvalidParameterException("baseApiUrl cannot null or empty")
        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(CONNECT_TIMEOUT_TIME, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT_TIME, TimeUnit.SECONDS)
            .addInterceptor(restRequestInterceptor)
            .retryOnConnectionFailure(true)
            .addInterceptor(logging) // <-- this is the important line!
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateTypeDeserializer())
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(baseApiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

    fun addHeader(name: String, value: String) {
        restRequestInterceptor.addHeader(name, value)
    }

    fun addAuthorization(token: String?) {
        token?.let {
            addHeader(AUTHORIZATION, it)
        }
    }

    fun removeAuthorization() {
        removeHeader(AUTHORIZATION)
    }

    fun removeHeader(name: String) {
        restRequestInterceptor.removeHeader(name)
    }

    fun hasHeader(name: String): Boolean {
        return restRequestInterceptor.hasHeader(name)
    }
}
