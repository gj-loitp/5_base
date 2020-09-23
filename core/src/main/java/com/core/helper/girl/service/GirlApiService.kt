package com.core.helper.girl.service

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GirlApiService {

    @GET("news/")
    fun getPageAsync(
            @Query("PageIndex") pageIndex: Int,
            @Query("PageSize") pageSize: Int,
            @Query("Keyword") keyword: String?
    ): Deferred<Response<GirlApiResponse<Any>>>
}
