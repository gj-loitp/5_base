package vn.loitp.app.activity.api.coroutine

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
interface ApiService {

    /**
     * authentication
     */
    // login
    @POST(ApiConfiguration.LOGIN)
    fun loginAsync(@Body request: LoginRequest): Deferred<Response<ApiResponse<LoginResponse>>>
}