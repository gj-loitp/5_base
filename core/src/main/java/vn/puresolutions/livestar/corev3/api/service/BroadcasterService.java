package vn.puresolutions.livestar.corev3.api.service;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import vn.puresolutions.livestar.corev3.api.model.old.Broadcaster;
import vn.puresolutions.livestar.corev3.api.model.old.Broadcasters;
import vn.puresolutions.livestar.corev3.api.model.old.param.FollowParam;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public interface BroadcasterService {

    String CONTROLLER = "broadcasters";

    @GET(CONTROLLER + "/followed")
    Observable<List<Broadcaster>> getFollowed();

    @GET(CONTROLLER + "/featured")
    Observable<Broadcasters> getFeatured();

    @GET(CONTROLLER + "/room-featured")
    Observable<List<Broadcaster>> getRoomFeatured();

    @GET(CONTROLLER + "/broadcasters")
    Observable<List<Broadcaster>> getBroadcasters();

    @GET(CONTROLLER + "/{id}")
    Observable<Broadcaster> getBroadcaster(@Path("id") long id);

    @GET(CONTROLLER + "/home-featured")
    Observable<List<Broadcaster>> getHomeFeatured();

    @GET(CONTROLLER + "/search")
    Observable<Broadcasters> search(@Query("q") String keyword, @Query("page") int page);

    @PUT(CONTROLLER + "/{id}/follow")
    Observable<Void> follow(@Path("id") long id, @Body FollowParam param);
}
