package com.core.helper.mup.comic.service

import com.core.helper.mup.comic.model.Login
import com.core.helper.mup.comic.model.RequestLogin

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class ComicRepository(private val comicApiService: ComicApiService) : ComicBaseRepository() {

    suspend fun login(email: String, password: String): ComicApiResponse<Login> = makeApiCall {
        val requestLogin = RequestLogin(
                email = email,
                password = password
        )
        comicApiService.loginAsync(
                requestLogin = requestLogin
        ).await()
    }
}
