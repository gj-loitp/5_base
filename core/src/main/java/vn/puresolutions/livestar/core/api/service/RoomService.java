package vn.puresolutions.livestar.core.api.service;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import vn.puresolutions.livestar.core.api.model.Action;
import vn.puresolutions.livestar.core.api.model.Gift;
import vn.puresolutions.livestar.core.api.model.Lounge;
import vn.puresolutions.livestar.core.api.model.RoomDetail;
import vn.puresolutions.livestar.core.api.model.RoomType;
import vn.puresolutions.livestar.core.api.model.Rooms;

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
