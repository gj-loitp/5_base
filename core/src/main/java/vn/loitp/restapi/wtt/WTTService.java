package vn.loitp.restapi.wtt;

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
import vn.loitp.restapi.livestar.corev3.api.model.CommonResponse;
import vn.loitp.restapi.livestar.corev3.api.model.v3.audiences.Audiences;
import vn.loitp.restapi.livestar.corev3.api.model.v3.bank.MobilePackage;
import vn.loitp.restapi.livestar.corev3.api.model.v3.buyticket.BuyTicket;
import vn.loitp.restapi.livestar.corev3.api.model.v3.categoryget.CategoryGet;
import vn.loitp.restapi.livestar.corev3.api.model.v3.endlive.EndLive;
import vn.loitp.restapi.livestar.corev3.api.model.v3.followidol.FollowIdol;
import vn.loitp.restapi.livestar.corev3.api.model.v3.getposter.GetPoster;
import vn.loitp.restapi.livestar.corev3.api.model.v3.gifthistory.GiftHistory;
import vn.loitp.restapi.livestar.corev3.api.model.v3.gifthistory.SvGiftHistory;
import vn.loitp.restapi.livestar.corev3.api.model.v3.joinroom.JoinRoom;
import vn.loitp.restapi.livestar.corev3.api.model.v3.listgift.ListGift;
import vn.loitp.restapi.livestar.corev3.api.model.v3.login.Register;
import vn.loitp.restapi.livestar.corev3.api.model.v3.login.ResetPassOTP;
import vn.loitp.restapi.livestar.corev3.api.model.v3.login.UserLogin;
import vn.loitp.restapi.livestar.corev3.api.model.v3.login.UserProfile;
import vn.loitp.restapi.livestar.corev3.api.model.v3.login.VerifyResponse;
import vn.loitp.restapi.livestar.corev3.api.model.v3.rank.WrapperRank;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomfindbyID.RoomFindByID;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory.RoomGetByCategory;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow.RoomFollowOrByView;
import vn.loitp.restapi.livestar.corev3.api.model.v3.schedule.ScheduleItem;
import vn.loitp.restapi.livestar.corev3.api.model.v3.search.RoomResult;
import vn.loitp.restapi.livestar.corev3.api.model.v3.sendgift.SendGift;
import vn.loitp.restapi.livestar.corev3.api.model.v3.sendheart.SendHeart;
import vn.loitp.restapi.livestar.corev3.api.model.v3.startlive.StartLive;

/**
 * @author loitp
 */

public interface WTTService {
    @POST("/api/data/v1/metadata/list")
    Observable<Object> getMetadatList(@Query("number") int number);
}
