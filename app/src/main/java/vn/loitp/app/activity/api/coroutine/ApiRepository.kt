package vn.loitp.app.activity.api.coroutine

import com.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class ApiRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun photosetsGetList(method: String,
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
    }
}