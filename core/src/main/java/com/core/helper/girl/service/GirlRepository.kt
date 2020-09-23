package com.core.helper.girl.service

import com.service.model.ApiResponse
import com.service.model.UserTest
import com.service.repository.BaseRepository

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class GirlRepository(private val girlApiService: GirlApiService) : BaseRepository() {

    suspend fun getUserTest(page: Int): ApiResponse<ArrayList<UserTest>> = makeApiCall {
        girlApiService.getUserTestAsync(page).await()
    }
}
