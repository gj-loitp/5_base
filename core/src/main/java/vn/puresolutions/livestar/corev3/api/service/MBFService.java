package vn.puresolutions.livestar.corev3.api.service;


import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;
import vn.puresolutions.livestar.corev3.api.model.old.Message;
import vn.puresolutions.livestar.corev3.api.model.old.Phone;
import vn.puresolutions.livestar.corev3.api.model.old.Token;
import vn.puresolutions.livestar.corev3.api.model.old.param.OTPParam;
import vn.puresolutions.livestar.corev3.api.model.old.param.SyncParam;

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
