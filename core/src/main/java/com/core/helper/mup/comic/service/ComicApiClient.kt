package com.core.helper.mup.comic.service

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.restapi.DateTypeDeserializer
import com.restapi.restclient.RestRequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
object ComicApiClient {

    private fun getBaseUrl() = ComicApiConfiguration.BASE_URL
    private var restRequestInterceptor: RestRequestInterceptor? = null

    private fun getClient(url: String): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
        return getRetrofit(url, okHttpClient)
    }

    private fun getRetrofit(url: String, builder: OkHttpClient.Builder): Retrofit {

        restRequestInterceptor = RestRequestInterceptor()

        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        builder.apply {
            connectTimeout(ComicApiConfiguration.TIME_OUT, TimeUnit.SECONDS)
            readTimeout(ComicApiConfiguration.TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(ComicApiConfiguration.TIME_OUT, TimeUnit.SECONDS)
            //addInterceptor(AuthenticationInterceptor())
            addInterceptor(logging)
            restRequestInterceptor?.let { rri ->
                addInterceptor(rri)
            }
            //retryOnConnectionFailure(false)
        }

        val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateTypeDeserializer())
                .create()

        return Retrofit.Builder().apply {
            baseUrl(url)
            addCallAdapterFactory(CoroutineCallAdapterFactory())
            //addConverterFactory(MoshiConverterFactory.create(moshi))
            addConverterFactory(GsonConverterFactory.create(gson))
            client(builder.build())
        }.build()
    }

    val apiService = getClient(getBaseUrl()).create(ComicApiService::class.java)

    fun addHeader(name: String, value: String) {
        restRequestInterceptor?.addHeader(name, value)
    }

    fun addAuthorization(token: String) {
        addHeader(ComicApiConfiguration.AUTHORIZATION_HEADER, token)
    }

    fun removeAuthorization() {
        removeHeader(ComicApiConfiguration.AUTHORIZATION_HEADER)
    }

    fun removeHeader(name: String) {
        restRequestInterceptor?.removeHeader(name)
    }

    fun hasHeader(name: String): Boolean {
        val hasHeader = restRequestInterceptor?.hasHeader(name)
        return hasHeader == true
    }
}
