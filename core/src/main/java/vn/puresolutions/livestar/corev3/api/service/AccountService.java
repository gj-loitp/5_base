package vn.puresolutions.livestar.corev3.api.service;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import vn.puresolutions.livestar.corev3.api.model.old.Payment;
import vn.puresolutions.livestar.corev3.api.model.old.Phone;
import vn.puresolutions.livestar.corev3.api.model.old.Token;
import vn.puresolutions.livestar.corev3.api.model.old.User;
import vn.puresolutions.livestar.corev3.api.model.old.param.EmailParam;
import vn.puresolutions.livestar.corev3.api.model.old.param.ShareResponse;
import vn.puresolutions.livestar.corev3.api.model.old.param.SignInFBParam;
import vn.puresolutions.livestar.corev3.api.model.old.param.SignInLSParam;
import vn.puresolutions.livestar.corev3.api.model.old.param.SubmitShareParam;
import vn.puresolutions.livestar.corev3.api.model.old.param.TokenParam;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/25/2015
 */
public interface AccountService {
    String AUTH_CONTROLLER = "auth";
    String USER_CONTROLLER = "users";

    @POST(AUTH_CONTROLLER + "/register")
    Observable<Void> register(@Body SignInLSParam signInParam);

    @POST(AUTH_CONTROLLER + "/login")
    Observable<Token> login(@Body SignInLSParam signInParam);

    @POST(AUTH_CONTROLLER + "/fb-register")
    Observable<Token> loginFB(@Body SignInFBParam signInParam);

    @POST(AUTH_CONTROLLER + "/check-user-mbf")
    Observable<Void> checkUserMbf(@Body Phone param);

    @GET(USER_CONTROLLER)
    Observable<User> getProfile();

    @GET(USER_CONTROLLER + "/expense-records")
    Observable<List<Payment>> getPaymentHistory();

    @POST(AUTH_CONTROLLER + "/verify-token")
    Observable<Void> verifyToken(@Body TokenParam signInParam);

    @POST(AUTH_CONTROLLER + "/update-forgot-code")
    Observable<Void> forgotPassword(@Body EmailParam param);

    @GET(AUTH_CONTROLLER + "/mobifone")
    Observable<Token> loginMBF();

    /*@POST(USER_CONTROLLER + "/app-share-fb-received-coin")
    Observable<Void> shareFB(@Body SubmitShareParam param);*/
    @POST(USER_CONTROLLER + "/app-share-fb-received-coin")
    Observable<ShareResponse> shareFB(@Body SubmitShareParam param);
}
