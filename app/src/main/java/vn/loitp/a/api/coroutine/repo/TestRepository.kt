package vn.loitp.a.api.coroutine.repo

import com.loitp.sv.model.ApiResponse
import com.loitp.sv.model.UserTest
import com.loitp.sv.repository.BaseRepository
import vn.loitp.a.api.coroutine.sv.ApiService

class TestRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun getUserTest(page: Int): ApiResponse<ArrayList<UserTest>> = makeApiCall {
        apiService.getUserTestAsync(page).await()
    }
}
