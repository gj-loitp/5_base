package vn.loitp.app.activity.api.coroutine

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
object ApiConfiguration {
    // authentication
    const val BASE_AUTHEN_URL = "https://reqres.in/api/"

    // API Param
    const val AUTHORIZATION_HEADER = "Authorization"
    const val TOKEN_TYPE = "bearer"
    const val TIME_OUT = 20L // In second
    const val LIMIT_PAGE = 20
}
