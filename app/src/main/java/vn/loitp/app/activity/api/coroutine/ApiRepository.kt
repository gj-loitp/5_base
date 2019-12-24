package vn.loitp.app.activity.api.coroutine

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class ApiRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> = makeApiCall {
        apiService.loginAsync(request).await()
    }

}