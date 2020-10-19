package com.core.helper.mup.comic.service

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class ComicApiConfiguration {
    companion object {
        // authentication
        const val BASE_URL = "https://api.truyentranhvn.org/"

        // API Param
        const val AUTHORIZATION_HEADER = "Authorization"
        const val TOKEN_TYPE = "bearer"
        const val TIME_OUT = 20L // In second
        const val LIMIT_PAGE = 20

        const val PAGE_SIZE = 50
    }
}
