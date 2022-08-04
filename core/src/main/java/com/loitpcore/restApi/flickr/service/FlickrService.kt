package com.loitpcore.restApi.flickr.service

import com.loitpcore.restApi.flickr.model.photoSetGetList.WrapperPhotosetGetlist
import com.loitpcore.restApi.flickr.model.photoSetGetPhotos.WrapperPhotosetGetPhotos
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface FlickrService {
    @GET("rest/")
    fun getListPhotoset(
        @Query("method") method: String?,
        @Query("api_key") apiKey: String?,
        @Query("user_id") userId: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("primary_photo_extras") primaryPhotoExtras: String?,
        @Query("format") format: String?,
        @Query("nojsoncallback") noJsonCallback: Int
    ): Observable<WrapperPhotosetGetlist?>

    @GET("rest/")
    fun getPhotosetPhotos(
        @Query("method") method: String?,
        @Query("api_key") apiKey: String?,
        @Query("photoset_id") photosetId: String?,
        @Query("user_id") userId: String?,
        @Query("extras") extras: String?,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("format") format: String?,
        @Query("nojsoncallback") noJsonCallback: Int
    ): Observable<WrapperPhotosetGetPhotos?>
}
