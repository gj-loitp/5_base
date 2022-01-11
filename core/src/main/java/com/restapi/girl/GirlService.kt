package com.restapi.girl

import com.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist
import com.restapi.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author loitp
 */
interface GirlService {
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
