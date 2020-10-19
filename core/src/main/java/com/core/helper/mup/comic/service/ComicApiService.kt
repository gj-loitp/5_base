package com.core.helper.mup.comic.service

import com.core.helper.mup.comic.model.Login
import com.core.helper.mup.comic.model.RequestLogin
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ComicApiService {

    @POST("login/")
    fun loginAsync(
            @Body requestLogin: RequestLogin
    ): Deferred<Response<ComicApiResponse<Login>>>
}
