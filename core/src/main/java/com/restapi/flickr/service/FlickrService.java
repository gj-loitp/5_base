package com.restapi.flickr.service;

import com.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import com.restapi.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author loitp
 */

public interface FlickrService {
    @GET("rest/")
    Observable<WrapperPhotosetGetlist> photosetsGetList(@Query("method") String method,
                                                        @Query("api_key") String apiKey,
                                                        @Query("user_id") String userId,
                                                        @Query("page") int page,
                                                        @Query("per_page") int perPage,
                                                        @Query("primary_photo_extras") String primaryPhotoExtras,
                                                        @Query("format") String format,
                                                        @Query("nojsoncallback") int noJsonCallback);

    @GET("rest/")
    Observable<WrapperPhotosetGetPhotos> photosetsGetPhotos(@Query("method") String method,
                                                            @Query("api_key") String apiKey,
                                                            @Query("photoset_id") String photosetId,
                                                            @Query("user_id") String userId,
                                                            @Query("extras") String extras,
                                                            @Query("per_page") int perPage,
                                                            @Query("page") int page,
                                                            @Query("format") String format,
                                                            @Query("nojsoncallback") int noJsonCallback);
}
