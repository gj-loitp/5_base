package vn.loitp.livestar.corev3.api.service;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;
import vn.loitp.livestar.corev3.api.model.CommonResponse;
import vn.loitp.livestar.corev3.api.model.v3.audiences.Audiences;
import vn.loitp.livestar.corev3.api.model.v3.bank.MobilePackage;
import vn.loitp.livestar.corev3.api.model.v3.buyticket.BuyTicket;
import vn.loitp.livestar.corev3.api.model.v3.categoryget.CategoryGet;
import vn.loitp.livestar.corev3.api.model.v3.endlive.EndLive;
import vn.loitp.livestar.corev3.api.model.v3.followidol.FollowIdol;
import vn.loitp.livestar.corev3.api.model.v3.getposter.GetPoster;
import vn.loitp.livestar.corev3.api.model.v3.gifthistory.GiftHistory;
import vn.loitp.livestar.corev3.api.model.v3.gifthistory.SvGiftHistory;
import vn.loitp.livestar.corev3.api.model.v3.joinroom.JoinRoom;
import vn.loitp.livestar.corev3.api.model.v3.listgift.ListGift;
import vn.loitp.livestar.corev3.api.model.v3.login.Register;
import vn.loitp.livestar.corev3.api.model.v3.login.ResetPassOTP;
import vn.loitp.livestar.corev3.api.model.v3.login.UserLogin;
import vn.loitp.livestar.corev3.api.model.v3.login.UserProfile;
import vn.loitp.livestar.corev3.api.model.v3.login.VerifyResponse;
import vn.loitp.livestar.corev3.api.model.v3.rank.WrapperRank;
import vn.loitp.livestar.corev3.api.model.v3.roomfindbyID.RoomFindByID;
import vn.loitp.livestar.corev3.api.model.v3.roomgetbycategory.RoomGetByCategory;
import vn.loitp.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.loitp.livestar.corev3.api.model.v3.roomgetbyvieworfollow.RoomFollowOrByView;
import vn.loitp.livestar.corev3.api.model.v3.schedule.ScheduleItem;
import vn.loitp.livestar.corev3.api.model.v3.search.RoomResult;
import vn.loitp.livestar.corev3.api.model.v3.sendgift.SendGift;
import vn.loitp.livestar.corev3.api.model.v3.sendheart.SendHeart;
import vn.loitp.livestar.corev3.api.model.v3.startlive.StartLive;

/**
 * @author loitp
 */

public interface LSService {
    @GET("v1/app/poster")
    Observable<GetPoster[]> getPoster(@Query("number") int number);

    @GET("v1/room/category")
    Observable<CategoryGet[]> categoryGet(@Query("categoryId") String categoryId);

    @GET("v1/room/category")
    Observable<CategoryGet[]> categoryGet();

    @GET("v1/room/list-by-category")
    Observable<RoomGetByCategory> roomGetByCategory(@Query("categoryId") String categoryId, @Query("onair") Boolean onair, @Query("page") int page);

    @GET("v1/room/list-by-category")
    Observable<RoomGetByCategory> roomGetByCategory(@Query("categoryId") String categoryId, @Query("page") int page, @Query("limit") int limit);

    @GET("v1/room/follow-on-air")
    Observable<RoomFollowOrByView> roomFollowGetOnAir();

    @GET("v1/room/list-by-view")
    Observable<RoomFollowOrByView> roomGetByView();

    @GET("v1/room/:roomId/")
    Observable<RoomFindByID> roomFindByID(@Query("roomId") String roomId);

    @FormUrlEncoded
    @PUT("v1/room/follow")
    Observable<FollowIdol> followIdol(@Field("roomId") String roomId);

    @FormUrlEncoded
    @POST("v1/room/follow")
    Observable<FollowIdol> unfollowIdol(@Field("roomId") String roomId);

    @FormUrlEncoded
    @POST("v1/auth/register")
    Observable<Register> register(@Field("name") String name, @Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/auth/login")
    Observable<UserLogin> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/auth/fb-register")
    Observable<UserLogin> loginFB(@Field("access_token") String token);

    @GET("v1/user/profile")
    Observable<UserProfile> getProfile();

    @GET("v1/user/profile")
    Observable<UserProfile> getProfile(@Query("userId") String userId);

    @FormUrlEncoded
    @POST("v1/user/profile")
    Observable<UserProfile> updateProfile(@Field("name") String name, @Field("birthday") String birthday, @Field("gender") String gender);

    @FormUrlEncoded
    @POST("v1/auth/active")
    Observable<VerifyResponse> activeAccount(@Field("phone") String phone, @Field("code") String code);

    @FormUrlEncoded
    @POST("v1/auth/send-otp-active")
    Observable<Register> resendOtpActive(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("v1/auth/change-password")
    Observable<Void> changePassword(@Field("password") String currentPassword, @Field("new_password") String newPassword, @Field("flush_token") boolean flush_token);

    @FormUrlEncoded
    @POST("v1/auth/request-reset-password")
    Observable<Register> requestResetPassword(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("v1/auth/reset-password")
    Observable<Void> resetPassword(@Field("code") String code, @Field("password") String password, @Field("phone") String phone, @Field("flush_token") boolean flush_token);

    @FormUrlEncoded
    @POST("v1/auth/verify-reset-password")
    Observable<ResetPassOTP> verifyResetPassword(@Field("phone") String phone, @Field("code") String code);


    @GET("v1/user/follower")
    Observable<RoomResult> getFollower(@Query("page") int page, @Query("userId") String userId);

    @GET("v1/user/following")
    Observable<RoomResult> getFollowing(@Query("page") int page, @Query("userId") String userId);

    @GET("v1/room/schedule")
    Observable<ArrayList<ScheduleItem>> getSchedule();

    @DELETE("v1/room/schedule")
    Observable<Void> deleteSchedule(@Query("scheduleId") String id);

    @FormUrlEncoded
    @PUT("v1/room/schedule")
    Observable<ScheduleItem> createSchedule(@Field("start") long star);

    @FormUrlEncoded
    @POST("v1/room/schedule")
    Observable<ScheduleItem> updateSchedule(@Field("scheduleId") String id, @Field("start") long star);

    @GET("v1/room/search")
    Observable<RoomResult> search(@Query("keyword") String keyword, @Query("page") int page);

    @GET("v1/room/list-user")
    Observable<Audiences> getAudiences(@Query("roomId") String roomId, @Query("page") int page);

    @Multipart
    @POST("v1/room/banner")
    Observable<Room> updateBanner(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("v1/room/change-name")
    Observable<UserProfile> updateRoomInfo(@Field("name") String name, @Field("description") String description);

    @FormUrlEncoded
    @POST("v1/room/change-category")
    Observable<Room> updateCategory(@Field("categoryId") String categoryId);

    @Multipart
    @PUT("v1/user/avatar")
    Observable<Void> changeAvatar(@Part MultipartBody.Part file);

    @POST("v1/auth/logout")
    Observable<Void> logout();

    @FormUrlEncoded
    @PUT("v1/stream/send-heart")
    Observable<SendHeart> sendHeart(@Field("roomId") String roomId);

    @GET("v1/gift/list")
    Observable<ListGift> listGift();

    @FormUrlEncoded
    @POST("v1/gift/send")
    Observable<SendGift> sendGift(@Field("userId") String userId, @Field("giftId") String giftId, @Field("quantity") int quantity);

    @POST("v1/gift/history")
    Observable<GiftHistory> giftHistory(@Body SvGiftHistory svGiftHistory);

    @GET("v1/payment/package")
    Observable<MobilePackage> getPackageCoin();

    @FormUrlEncoded
    @POST("v1/ticket/buy")
    Observable<BuyTicket> buyTicket(@Field("roomId") String roomId, @Field("sessionId") String sessionId);

    @FormUrlEncoded
    @POST("v1/stream/restrict")
    Observable<Room> setModeRoom(@Field("mode") String mode);

    @FormUrlEncoded
    @POST("v1/stream/start-live")
    Observable<StartLive> startLive(@Field("mode") String mode);

    @POST("v1/stream/end-live")
    Observable<EndLive> endLive();

    @FormUrlEncoded
    @POST("v1/room/join")
    Observable<JoinRoom> joinRoom(@Field("roomId") String roomId, @Field("sessionId") String sessionId, @Field("userId") String userId);

    @GET("v1/user/rank-heart")
    Observable<WrapperRank[]> rankHeart();

    @GET("v1/user/rank-coin")
    Observable<WrapperRank[]> rankCoin();

    @GET("v1/user/rank-share-fb")
    Observable<WrapperRank[]> rankShareFB();

    @FormUrlEncoded
    @POST("v1/user/share-fb")
    Observable<CommonResponse> shareFB(@Field("access_token") String access_token, @Field("roomId") String roomId);
}
