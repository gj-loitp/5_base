package com.core.helper.mup.comic.service

import com.core.helper.mup.comic.model.* // ktlint-disable no-wildcard-imports

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

    suspend fun getListComic(
        pageIndex: Int,
        keyword: String?
    ): ComicApiResponse<List<Comic>> = makeApiCall {
        comicApiService.getListComicAsync(
            pageIndex = pageIndex,
            pageSize = ComicApiConfiguration.PAGE_SIZE,
            keyword = keyword
        ).await()
    }

    suspend fun getListComicByCategory(
        categoryId: String?,
        pageIndex: Int,
        keyword: String?
    ): ComicApiResponse<List<Comic>> = makeApiCall {
        comicApiService.getListComicByCategoryAsync(
            categoryId = categoryId,
            pageIndex = pageIndex,
            pageSize = ComicApiConfiguration.PAGE_SIZE,
            keyword = keyword
        ).await()
    }

    suspend fun getListCategory(): ComicApiResponse<List<Category>> = makeApiCall {
        comicApiService.getListCategoryAsync().await()
    }

    suspend fun getListChapByComicId(
        comicId: String?,
        pageIndex: Int
    ): ComicApiResponse<List<Chap>> = makeApiCall {
        comicApiService.getListChapByComicIdAsync(
            comicId = comicId,
            pageIndex = pageIndex,
            pageSize = ComicApiConfiguration.PAGE_SIZE
        ).await()
    }

    suspend fun getChapterDetail(
        chapId: String?
    ): ComicApiResponse<ChapterDetail> = makeApiCall {
        comicApiService.getChapterDetailAsync(
            chapId = chapId
        ).await()
    }
}
