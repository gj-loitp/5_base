package vn.loitp.activity.api.coroutine.service

import com.loitp.sv.model.ApiResponse
import com.loitp.sv.model.UserTest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users/")
    fun getUserTestAsync(
        @Query("page") page: Int
    ): Deferred<Response<ApiResponse<ArrayList<UserTest>>>>
}
