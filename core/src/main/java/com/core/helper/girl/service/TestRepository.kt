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
class TestRepository(private val apiService: ApiService) : BaseRepository() {

    suspend fun getUserTest(page: Int): ApiResponse<ArrayList<UserTest>> = makeApiCall {
        apiService.getUserTestAsync(page).await()
    }
}
