package com.core.helper.girl.service

import com.service.model.ApiResponse
import com.service.model.UserTest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users/")
    fun getUserTestAsync(@Query("page") page: Int):
            Deferred<Response<ApiResponse<ArrayList<UserTest>>>>
}
