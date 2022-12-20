package com.loitp.restApi.restClient

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class RestRequestInterceptor : Interceptor {
    private val headers = Hashtable<String, String>()

    fun addHeader(key: String, value: String) {
        headers[key] = value
    }

    fun removeHeader(key: String) {
        headers.remove(key)
    }

    fun hasHeader(key: String): Boolean {
        return headers.containsKey(key)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (headers.size > 0) {
            val keys = headers.keys()

            while (keys.hasMoreElements()) {
                val key = keys.nextElement()
                val value = headers[key]

                value?.let {
                    builder.header(key, it)
                }
            }
        }
        return chain.proceed(builder.build())
    }
}
