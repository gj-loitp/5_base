package com.core.helper.girl.service

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class GirlRepository(private val girlApiService: GirlApiService) : GirlBaseRepository() {

    suspend fun getPage(pageIndex: Int, keyWord: String?): GirlApiResponse<Any> = makeApiCall {
        girlApiService.getPageAsync(
                pageIndex = pageIndex,
                pageSize = GirlApiConfiguration.PAGE_SIZE,
                keyword = keyWord
        ).await()
    }
}
