package vn.puresolutions.livestar.core.api.service;


import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;
import vn.puresolutions.livestar.core.api.model.Message;
import vn.puresolutions.livestar.core.api.model.Phone;
import vn.puresolutions.livestar.core.api.model.Token;
import vn.puresolutions.livestar.core.api.model.param.OTPParam;
import vn.puresolutions.livestar.core.api.model.param.SyncParam;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public interface MBFService {
    String CONTROLLER = "auth/mobifone";

    @GET(CONTROLLER)
    Observable<Phone> detect();

    @POST(CONTROLLER)
    Observable<Message> register(@Body String dummy);

    @POST(CONTROLLER)
    Observable<Void> register(@Body Phone phone, Callback<Message> callback);

    @PATCH(CONTROLLER)
    Observable<Token> verifyOTP(@Body OTPParam param);

    @PUT(CONTROLLER)
    Observable<Token> syncAccount(@Body SyncParam param);
}
