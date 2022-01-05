package com.core.helper.mup.girl.service

import com.core.helper.mup.girl.model.GirlPage
import com.core.helper.mup.girl.model.GirlPageDetail

class GirlRepository(private val girlApiService: GirlApiService) : GirlBaseRepository() {

    suspend fun getPage(pageIndex: Int, keyWord: String?): GirlApiResponse<ArrayList<GirlPage>> =
        makeApiCall {
            girlApiService.getPageAsync(
                pageIndex = pageIndex,
                pageSize = GirlApiConfiguration.PAGE_SIZE,
                keyword = keyWord
            ).await()
        }

    suspend fun getPageDetail(
        pageIndex: Int = 0,
        id: String?
    ): GirlApiResponse<ArrayList<GirlPageDetail>> = makeApiCall {
        girlApiService.getPageDetailAsync(
            pageIndex = pageIndex,
            pageSize = GirlApiConfiguration.PAGE_SIZE,
            id = id
        ).await()
    }
}
