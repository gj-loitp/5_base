package vn.loitp.app.activity.api.coroutine.repository

import com.service.model.ApiResponse
import com.service.model.UserTest
import com.service.repository.BaseRepository
import vn.loitp.app.activity.api.coroutine.service.ApiService

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class TestRepository(private val apiService: ApiService) : BaseRepository() {
    /*suspend fun photosetsGetList(method: String,
                                 apiKey: String,
                                 userId: String,
                                 page: Int,
                                 perPage: Int,
                                 primaryPhotoExtras: String,
                                 format: String,
                                 noJsonCallback: Int): ApiResponse<WrapperPhotosetGetlist> = makeApiCall {
        apiService.photosetsGetList(method,
                apiKey,
                userId,
                page,
                perPage,
                primaryPhotoExtras,
                format,
                noJsonCallback).await()
    }*/

    suspend fun getUserTest(page: Int): ApiResponse<ArrayList<UserTest>> = makeApiCall {
        apiService.getUserTestAsync(page).await()
    }
}