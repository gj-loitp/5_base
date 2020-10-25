package com.core.helper.mup.comic.service

import com.core.helper.mup.comic.model.Category
import com.core.helper.mup.comic.model.Comic
import com.core.helper.mup.comic.model.Login
import com.core.helper.mup.comic.model.RequestLogin
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ComicApiService {

    @POST("login/")
    fun loginAsync(
            @Body requestLogin: RequestLogin
    ): Deferred<Response<ComicApiResponse<Login>>>

    @GET("comics/")
    fun getListComicAsync(
            @Query("PageIndex") pageIndex: Int,
            @Query("PageSize") pageSize: Int,
            @Query("Keyword") keyword: String?
    ): Deferred<Response<ComicApiResponse<List<Comic>>>>

    @GET("comics-category/{categoryId}/")
    fun getListComicByCategoryAsync(
            @Path("categoryId") categoryId: String?,
            @Query("PageIndex") pageIndex: Int,
            @Query("PageSize") pageSize: Int,
            @Query("Keyword") keyword: String?
    ): Deferred<Response<ComicApiResponse<List<Comic>>>>

    @GET("category/")
    fun getListCategoryAsync(
    ): Deferred<Response<ComicApiResponse<List<Category>>>>
}
