package vn.loitp.activity.api.coroutine.repository

import com.loitp.service.model.ApiResponse
import com.loitp.service.model.UserTest
import com.loitp.service.repository.BaseRepository
import vn.loitp.activity.api.coroutine.service.ApiService

class TestRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun getUserTest(page: Int): ApiResponse<ArrayList<UserTest>> = makeApiCall {
        apiService.getUserTestAsync(page).await()
    }
}
