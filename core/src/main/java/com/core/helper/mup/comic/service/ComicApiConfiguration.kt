package com.core.helper.mup.comic.service

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
