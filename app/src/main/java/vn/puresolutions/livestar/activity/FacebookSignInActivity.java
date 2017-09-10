package vn.puresolutions.livestar.activity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.FacebookException;

import rx.Observable;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.Token;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.model.param.SignInFBParam;
import vn.puresolutions.livestar.core.api.model.param.SignInLSParam;
import vn.puresolutions.livestar.core.api.model.param.SignInParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.AccountService;
import vn.puresolutions.livestar.custom.dialog.ProgressDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.helper.FacebookHelper;
import vn.puresolutions.livestar.notification.gcm.LSRegistrationIntentService;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.base.BaseActivity;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/14/2015
 */
public abstract class FacebookSignInActivity extends BaseActivity {
    public static final String BUNDLE_KEY_IS_FINISH_WHEN_COMPLETED = "isFinishWhenCompleted";

    private FacebookHelper facebookHelper;
    private ProgressDialog progressDialog;

    private FacebookHelper.OnLoginFacebookListener onLoginFacebookListener = new FacebookHelper.OnLoginFacebookListener() {
        @Override
        public void onSuccess(String... params) {
            SignInFBParam param = new SignInFBParam();
            param.setEmail(params[0]);
            param.setToken(params[1]);
            signIn(param);
        }

        @Override
        public void onFail(FacebookException error) {
            if (error.getMessage().toLowerCase().contains(getString(R.string.connection_failure).toLowerCase())) {
                AlertMessage.showError(FacebookSignInActivity.this, R.string.cant_connect_to_internet);
            } else {
                AlertMessage.showError(FacebookSignInActivity.this, R.string.error_fb_login);
            }
            //AlertMessage.showError(FacebookSignInActivity.this, error.getMessage().toString());
            hideProgressDialog();
        }

        @Override
        public void onCancel() {
            hideProgressDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookHelper = new FacebookHelper(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebookHelper.onActivityResult(requestCode, resultCode, data);
    }

    protected void signInFacebook() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.signing_in));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
        facebookHelper.performLoginFacebook(onLoginFacebookListener);
    }

    protected void signIn(SignInParam param) {
        AccountService service = RestClient.createService(AccountService.class);
        Observable<Token> loginObservable = param instanceof SignInLSParam
                ? service.login((SignInLSParam) param)
                : service.loginFB((SignInFBParam) param);

        Observable<User> userObservable = loginObservable
                .doOnNext(token -> UserInfo.setToken(token.getToken()))
                .flatMap(token -> service.getProfile());
        subscribe(userObservable, new ApiSubscriber<User>() {
            @Override
            public void onSuccess(User user) {
                UserInfo.setUserLoggedIn(user);
                onSignInCompleted();
            }

            @Override
            public void onFail(Throwable e) {
                onLoginFail(param instanceof SignInFBParam);
                hideProgressDialog();
                handleException(e);
            }
        });
    }

    protected void onSignInCompleted() {
        hideProgressDialog();
        if (!getIntent().getBooleanExtra(BUNDLE_KEY_IS_FINISH_WHEN_COMPLETED, false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        Intent intentService = new Intent(this, LSRegistrationIntentService.class);
        startService(intentService);

        finish();
    }

    protected void onLoginFail(boolean isSignInFB) {
        if (isSignInFB) {
            hideProgressDialog();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                // avoid view not attached to window manager
            }
        }
    }
}
