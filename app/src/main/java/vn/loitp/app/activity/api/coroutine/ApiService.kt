package vn.loitp.app.activity.api.coroutine

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
interface ApiService {

    /*@GET("rest/")
    fun photosetsGetList(@Query("method") method: String,
                         @Query("api_key") apiKey: String,
                         @Query("user_id") userId: String,
                         @Query("page") page: Int,
                         @Query("per_page") perPage: Int,
                         @Query("primary_photo_extras") primaryPhotoExtras: String,
                         @Query("format") format: String,
                         @Query("nojsoncallback") noJsonCallback: Int):
            Deferred<Response<ApiResponse<WrapperPhotosetGetlist>>>*/

    @GET("users/")
    fun getUserTest(@Query("page") page: Int):
            Deferred<Response<ApiResponse<List<UserTest>>>>
}