package vn.puresolutions.livestar.core.api.service;

import java.util.List;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import vn.puresolutions.livestar.core.api.model.Broadcaster;
import vn.puresolutions.livestar.core.api.model.LoyalFan;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public interface RankingService {
    String CONTROLLER = "ranks";

    @GET(CONTROLLER + "/top-level-grow-broadcaster")
    Observable<List<Broadcaster>> getTopLevelBroadcaster();

    @GET(CONTROLLER + "/bct-share-fb")
    Observable<List<LoyalFan>> getTopShareBroadcaster(@Query("range") String range);

    @GET(CONTROLLER + "/top-heart-broadcaster")
    Observable<List<Broadcaster>> getTopHeartBroadcaster(@Query("range") String range);

    @GET(CONTROLLER + "/top-gift-broadcaster")
    Observable<List<LoyalFan>> getTopGiftBroadcaster(@Query("range") String range);

    @GET(CONTROLLER + "/{room}/top-user-use-in-room")
    Observable<List<LoyalFan>> getTopWeek(@Path("room") long roomId);

    @GET(CONTROLLER + "/{room}/top-user-use-money")
    Observable<List<LoyalFan>> getTopCurrent(@Path("room") long roomId);
}
