package vn.loitp.restapi.flickr.service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import vn.loitp.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import vn.loitp.restapi.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos;

/**
 * @author loitp
 */

public interface FlickrService {
    @GET("rest/")
    Observable<WrapperPhotosetGetlist> photosetsGetList(@Query("method") String method,
                                                        @Query("api_key") String api_key,
                                                        @Query("user_id") String user_id,
                                                        @Query("page") int page,
                                                        @Query("per_page") int per_page,
                                                        @Query("primary_photo_extras") String primary_photo_extras,
                                                        @Query("format") String format,
                                                        @Query("nojsoncallback") int nojsoncallback);

    @GET("rest/")
    Observable<WrapperPhotosetGetPhotos> photosetsGetPhotos(@Query("method") String method,
                                                            @Query("api_key") String api_key,
                                                            @Query("photoset_id") String photoset_id,
                                                            @Query("user_id") String user_id,
                                                            @Query("extras") String extras,
                                                            @Query("per_page") int per_page,
                                                            @Query("page") int page,
                                                            @Query("format") String format,
                                                            @Query("nojsoncallback") int nojsoncallback);
}
