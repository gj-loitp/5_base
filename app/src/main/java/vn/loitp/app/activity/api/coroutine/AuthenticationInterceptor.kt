package vn.loitp.app.activity.api.coroutine

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (AppAuthentication.value != null) {
            val url = chain.request().url()
                    .newBuilder()
                    .build()

            val newRequest = request.newBuilder()
                    .addHeader(
                            ApiConfiguration.AUTHORIZATION_HEADER,
                            "${ApiConfiguration.TOKEN_TYPE} ${AppAuthentication.value?.accessToken}"
                    ).url(url).build()
            //Logger.d(newRequest.headers().toString())
            return chain.proceed(newRequest)
        } else {
            return chain.proceed(request)
        }
    }
}