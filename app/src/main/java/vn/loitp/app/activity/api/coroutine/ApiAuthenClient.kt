package vn.loitp.app.activity.api.coroutine

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.restapi.DateTypeDeserializer
import okhttp3.OkHttpClient
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
object ApiAuthenClient {

    private fun getBaseUrl() = ApiConfiguration.BASE_AUTHEN_URL

    private fun getClient(url: String): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
        return getRetrofit(url, okHttpClient)
    }

    private fun getRetrofit(url: String, builder: OkHttpClient.Builder): Retrofit {

        builder.apply {

            connectTimeout(ApiConfiguration.TIME_OUT, TimeUnit.SECONDS)
            readTimeout(ApiConfiguration.TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(ApiConfiguration.TIME_OUT, TimeUnit.SECONDS)
            addInterceptor(AuthenticationInterceptor())
            //retryOnConnectionFailure(false)
        }

        //val moshi = Moshi.Builder()
        //        .build()

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

    val apiService = getClient(getBaseUrl()).create(ApiService::class.java)
}