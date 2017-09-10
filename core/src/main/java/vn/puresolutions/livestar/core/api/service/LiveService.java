package vn.puresolutions.livestar.core.api.service;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import vn.puresolutions.livestar.core.api.model.BaseModel;
import vn.puresolutions.livestar.core.api.model.param.HeartParam;
import vn.puresolutions.livestar.core.api.model.param.MessageParam;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/25/2015
 */
public interface LiveService {
    String CONTROLLER = "live";

    @POST(CONTROLLER + "/send-message")
    Observable<Void> sendMessage(@Query("room_id") long roomId, @Body MessageParam messageParam);

    @POST(CONTROLLER + "/send-gifts")
    Observable<Void> sendGift(@Query("room_id") long roomId, @Query("gift_id") long giftId, @Query("quantity") int quantity, @Body String dummyBody);

    @POST(CONTROLLER + "/vote-action")
    Observable<Void> voteAction(@Query("room_id") long roomId, @Query("action_id") long actionId, @Body String dummyBody);

    @POST(CONTROLLER + "/buy-lounge")
    Observable<Void> buyLounge(@Query("room_id") long roomId, @Query("lounge") int index, @Query("cost") long cost, @Body String dummyBody);

    @POST(CONTROLLER + "/add-heart")
    Observable<Void> addHeart(@Body HeartParam param);

    @POST(CONTROLLER + "/send-hearts")
    Observable<Void> sendHeart(@Body HeartParam param);
}
