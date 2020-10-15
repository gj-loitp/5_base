package com.core.helper.mup.girl.service

import com.core.helper.mup.girl.model.GirlPage
import com.core.helper.mup.girl.model.GirlPageDetail
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
    ): Deferred<Response<GirlApiResponse<ArrayList<GirlPage>>>>

    @GET("news/detail/")
    fun getPageDetailAsync(
            @Query("PageIndex") pageIndex: Int,
            @Query("PageSize") pageSize: Int,
            @Query("Id") id: String?
    ): Deferred<Response<GirlApiResponse<ArrayList<GirlPageDetail>>>>
}
