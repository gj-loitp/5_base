package vn.puresolutions.livestar.corev3.api.service;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import vn.puresolutions.livestar.corev3.api.model.old.Action;
import vn.puresolutions.livestar.corev3.api.model.old.Gift;
import vn.puresolutions.livestar.corev3.api.model.old.Lounge;
import vn.puresolutions.livestar.corev3.api.model.old.RoomDetail;
import vn.puresolutions.livestar.corev3.api.model.old.RoomType;
import vn.puresolutions.livestar.corev3.api.model.old.Rooms;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public interface RoomService {

    String CONTROLLER = "rooms";

    @GET(CONTROLLER + "/room-list")
    Observable<Rooms> getOnAir(@Query("page") int page);

    @GET(CONTROLLER + "/coming-soon")
    Observable<Rooms> getComingSoon(@Query("category_id") int categoryId);

    @GET(CONTROLLER + "/coming-soon")
    Observable<Rooms> getComingSoon();

    @GET(CONTROLLER + "/room-type")
    Observable<List<RoomType>> getRoomType();

    @GET(CONTROLLER + "/{id}")
    Observable<RoomDetail> getRoomDetail(@Path("id") int roomId);

    @GET(CONTROLLER + "/gifts")
    Observable<List<Gift>> getGifts(@Query("room_id") int roomId);

    @GET(CONTROLLER + "/actions")
    Observable<List<Action>> getActions(@Query("room_id") int roomId);

    @GET(CONTROLLER + "/lounges")
    Observable<List<Lounge>> getLounges(@Query("room_id") int roomId);
}
