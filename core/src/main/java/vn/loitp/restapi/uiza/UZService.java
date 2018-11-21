package vn.loitp.restapi.uiza;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;
import vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay.ResultGetLinkPlay;
import vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming.ResultGetTokenStreaming;
import vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming.SendGetTokenStreaming;
import vn.loitp.restapi.uiza.model.v3.livestreaming.gettimestartlive.ResultTimeStartLive;
import vn.loitp.restapi.uiza.model.v3.livestreaming.getviewalivefeed.ResultGetViewALiveFeed;
import vn.loitp.restapi.uiza.model.v3.livestreaming.retrievealive.ResultRetrieveALive;
import vn.loitp.restapi.uiza.model.v3.livestreaming.retrievealiveevent.ResultRetrieveALiveEvent;
import vn.loitp.restapi.uiza.model.v3.livestreaming.startALiveFeed.BodyStartALiveFeed;
import vn.loitp.restapi.uiza.model.v3.metadata.createmetadata.CreateMetadata;
import vn.loitp.restapi.uiza.model.v3.metadata.createmetadata.ResultCreateMetadata;
import vn.loitp.restapi.uiza.model.v3.metadata.deleteanmetadata.ResultDeleteAnMetadata;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.ResultGetDetailOfMetadata;
import vn.loitp.restapi.uiza.model.v3.metadata.getlistmetadata.ResultGetListMetadata;
import vn.loitp.restapi.uiza.model.v3.metadata.updatemetadata.ResultUpdateMetadata;
import vn.loitp.restapi.uiza.model.v3.videoondeman.listallentity.ResultListEntity;
import vn.loitp.restapi.uiza.model.v3.videoondeman.retrieveanentity.ResultRetrieveAnEntity;

/**
 * @author loitp
 */

public interface UZService {
    //http://dev-docs.uizadev.io/#get-list-metadata
    @GET("/api/public/v3/media/metadata")
    Observable<ResultGetListMetadata> getListMetadata();

    //http://dev-docs.uizadev.io/#get-list-metadata
    @GET("/api/public/v3/media/metadata")
    Observable<ResultGetListMetadata> getListMetadata(@Query("limit") int limit, @Query("page") int page);

    //http://dev-docs.uizadev.io/#create-metadata
    @POST("/api/public/v3/media/metadata")
    Observable<ResultCreateMetadata> createMetadata(@Body CreateMetadata createMetadata);

    //http://dev-docs.uizadev.io/#get-detail-of-metadata
    @GET("/api/public/v3/media/metadata")
    Observable<ResultGetDetailOfMetadata> getDetailOfMetadata(@Query("id") String id);

    //http://dev-docs.uizadev.io/#update-metadata
    @PUT("/api/public/v3/media/metadata")
    Observable<ResultUpdateMetadata> updateMetadata(@Body CreateMetadata createMetadata);

    //http://dev-docs.uizadev.io/#delete-an-metadata
    @DELETE("/api/public/v3/media/metadata")
    Observable<ResultDeleteAnMetadata> deleteAnMetadata(@Query("id") String id);

    //http://dev-docs.uizadev.io/#list-all-entity
    @GET("/api/public/v3/media/entity")
    Observable<ResultListEntity> getListAllEntity();

    //http://dev-docs.uizadev.io/#list-all-entity
    @GET("/api/public/v3/media/entity")
    Observable<ResultListEntity> getListAllEntity(@Query("metadataId") String metadataid,
                                                  @Query("limit") int limit,
                                                  @Query("page") int page,
                                                  @Query("orderBy") String orderBy,
                                                  @Query("orderType") String orderType,
                                                  @Query("publishToCdn") String publishToCdn);

    //http://dev-docs.uizadev.io/#list-all-entity
    @GET("/api/public/v3/media/entity")
    Observable<ResultListEntity> getListAllEntity(@Query("metadataId") String metadataid,
                                                  @Query("limit") int limit,
                                                  @Query("page") int page);

    //http://dev-docs.uizadev.io/#list-all-entity
    @GET("/api/public/v3/media/entity")
    Observable<ResultListEntity> getListAllEntity(@Query("metadataId") String metadataid);

    //http://dev-docs.uizadev.io/#retrieve-an-entity
    @GET("/api/public/v3/media/entity")
    Observable<ResultRetrieveAnEntity> retrieveAnEntity(@Query("id") String id);

    //http://dev-docs.uizadev.io/#search-entity
    @GET("/api/public/v3/media/entity/search")
    Observable<ResultListEntity> searchEntity(@Query("keyword") String keyword);

    @POST("/api/public/v3/media/entity/playback/token")
    Observable<ResultGetTokenStreaming> getTokenStreaming(@Body SendGetTokenStreaming sendGetTokenStreaming);

    @GET("/api/private/v1/cdn/linkplay")
    Observable<ResultGetLinkPlay> getLinkPlay(@Query("app_id") String appId,
                                              @Query("entity_id") String entityId,
                                              @Query("type_content") String typeContent);

    @GET("/api/private/v1/cdn/live/linkplay")
    Observable<ResultGetLinkPlay> getLinkPlayLive(@Query("app_id") String appId,
                                                  @Query("stream_name") String streamName);

    @GET("/api/public/v3/live/entity")
    Observable<ResultRetrieveALiveEvent> retrieveALiveEvent(@Query("limit") int limit,
                                                            @Query("page") int page,
                                                            @Query("orderBy") String orderBy,
                                                            @Query("orderType") String orderType);

    @GET("/api/public/v3/live/entity")
    Observable<ResultRetrieveALive> retrieveALiveEvent(@Query("id") String entityId);

    @POST("/api/public/v3/live/entity/feed")
    Observable<Object> startALiveEvent(@Body BodyStartALiveFeed bodyStartALiveFeed);

    @GET("/api/public/v3/live/entity/tracking/current-view")
    Observable<ResultGetViewALiveFeed> getViewALiveFeed(@Query("id") String id);

    @GET("/api/private/v3/live/entity/tracking/")
    Observable<ResultTimeStartLive> getTimeStartLive(@Query("entityId") String entityId, @Query("feedId") String feedId);
}
