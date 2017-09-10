package vn.puresolutions.livestarv3.activity.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserLogin;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.login.param.LoginFBParam;
import vn.puresolutions.livestar.corev3.api.model.v3.login.param.LoginParam;
import vn.puresolutions.livestar.corev3.api.model.v3.login.param.LoginPhoneParam;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.WebActivity;
import vn.puresolutions.livestarv3.view.LEdittextClear;
import vn.puresolutions.livestarv3.view.LEdittextPassword;
import vn.puresolutions.livestarv3.activity.userprofile.RoomManagerActivity;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LRoomUtil;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

public class LoginActivity extends BaseActivity {
    private FacebookHelper facebookHelper;
    private Dialog dialog;
    @BindView(R.id.imvLoginScreen_Back)
    ImageView ivBack;
    @BindView(R.id.btnLoginScreen_Login)
    Button btnLogin;
    @BindView(R.id.lnlLoginScreen_LoginFb)
    LinearLayout lnlLoginScreen_LoginFb;
    @BindView(R.id.edtLoginScreen_Password)
    LEdittextPassword edtPassword;
    @BindView(R.id.edtLoginScreen_Username)
    LEdittextClear edtUsername;
    @BindView(R.id.tvLoginScreen_SignUp)
    TextView tvSignUp;
    @BindView(R.id.tvLoginScreen_ForgotPass)
    TextView tvForgot;

    private boolean isCalledFromHomeMainLiveStream;

    private FacebookHelper.OnLoginFacebookListener onLoginFacebookListener = new FacebookHelper.OnLoginFacebookListener() {
        @Override
        public void onSuccess(String... params) {
            LoginFBParam param = new LoginFBParam();
            param.setEmail(params[0]);
            param.setToken(params[1]);
            signIn(param);
        }

        @Override
        public void onFail(FacebookException error) {
            if (error.getMessage().toLowerCase().contains(getString(R.string.connection_failure).toLowerCase())) {
                AlertMessage.showError(activity, R.string.cant_connect_to_internet);
            } else {
                AlertMessage.showError(activity, R.string.error_fb_login);
            }
            //hideProgressDialog();
        }

        @Override
        public void onCancel() {
            //hideProgressDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        facebookHelper = new FacebookHelper(this);
        isCalledFromHomeMainLiveStream = getIntent().getBooleanExtra(Constants.LOGIN_IS_CALL_FROM_HOME_MAIN_LIVE_STREAM, false);
        LLog.d(TAG, "isCalledFromHomeMainLiveStream " + isCalledFromHomeMainLiveStream);
        init();
        //setFakeAccount();
    }

    private void init() {
        dialog = LDialogUtils.loadingMultiColor(activity, true);
        edtUsername.setCallback(new LEdittextClear.Callback() {
            @Override
            public void onTextChange(CharSequence s, int start, int before, int count) {
                enableUIButtonLogin();
            }
        });
        edtUsername.setImageLeft(R.drawable.user);
        edtUsername.setHint(R.string.loginscreen_editsigin_username);
        edtPassword.setCallback(new LEdittextClear.Callback() {
            @Override
            public void onTextChange(CharSequence s, int start, int before, int count) {
                enableUIButtonLogin();
            }
        });
        edtPassword.setImageLeft(R.drawable.key);
        edtPassword.setHint(R.string.loginscreen_signup_repassword);
        LUIUtil.setMarquee((TextView) findViewById(R.id.textView3));
    }

    private void enableUIButtonLogin() {
        if (!edtUsername.getText().isEmpty() && !edtPassword.getText().isEmpty()) {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.background_loginscreen_btnlogin);
        } else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.background_button_non_enable);
        }
    }

    @OnClick(R.id.btnLoginScreen_Login)
    void signIn() {
        if (validate()) {
            LoginPhoneParam param = new LoginPhoneParam();
            param.setPhone(edtUsername.getText().toString());
            param.setPassword(edtPassword.getEditext().getText().toString());
            signIn(param);
        }
    }

    @OnClick(R.id.imvLoginScreen_Back)
    void doBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_policy)
    void onClickPolicy() {
        Intent intent = new Intent(activity, WebActivity.class);
        //TODO change to correct url
        intent.putExtra(Constants.URL, "http://www.livestar.vn/policy");
        startActivity(intent);
        LUIUtil.transActivityFadeIn(activity);
    }

    @OnClick(R.id.tvLoginScreen_SignUp)
    void signUp() {
        Intent intent = new Intent(activity, RegisterActivity.class);
        startActivity(intent);
        LUIUtil.transActivityFadeIn(activity);
    }

    @OnClick(R.id.lnlLoginScreen_LoginFb)
    void loginFb() {
        //signIn(pa);
        facebookHelper.performLoginFacebook(onLoginFacebookListener);
    }

    @OnClick(R.id.tvLoginScreen_ForgotPass)
    void doForgot() {
        Intent intent = new Intent(this, ForgotPassActivity.class);
        startActivity(intent);
        LUIUtil.transActivityFadeIn(this);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edtUsername.getText().toString())) {
            edtUsername.getEditext().setError(getString(R.string.email_empty_error));
            return false;
        }

        if (TextUtils.isEmpty(edtPassword.getEditext().getText().toString())) {
            edtPassword.getEditext().setError(getString(R.string.password_empty_error));
            return false;
        }

        return true;
    }

    protected void signIn(LoginParam param) {
        LDialogUtils.showDialog(dialog);
        btnLogin.setEnabled(false);
        LSService service = RestClient.createService(LSService.class);
        Observable<UserLogin> loginObservable = param instanceof LoginPhoneParam
                ? service.login(((LoginPhoneParam) param).getPhone(), ((LoginPhoneParam) param).getPassword())
                : service.loginFB(((LoginFBParam) param).getToken());
        subscribe(loginObservable, new ApiSubscriber<UserLogin>() {
            @Override
            public void onSuccess(UserLogin result) {
                if (result.getError() != null) {
                    if (result.getError() == 1006 || result.getError() == 1001) {
                        LDialogUtils.showDlg1Option(activity, 0, getString(R.string.noti), getString(R.string.dialog_content_signin_phone_invalid), getString(R.string.ok), new LDialogUtils.Callback1() {
                            @Override
                            public void onClickButton0() {
                                //do nothing
                            }
                        });
                    }
                }
                btnLogin.setEnabled(true);
            }

            @Override
            public void onFail(Throwable e) {
                btnLogin.setEnabled(true);
                LDialogUtils.hideDialog(dialog);
                //do nothing
            }
        });
        Observable<UserProfile> userObservable = loginObservable
                //.doOnNext(token -> UserInfo.setToken(token.getToken()))
                .doOnNext(token -> LPref.setToken(getBaseContext(), token.getToken()))
                .flatMap(token -> service.getProfile());
        subscribe(userObservable, new ApiSubscriber<UserProfile>() {
            @Override
            public void onSuccess(UserProfile user) {
                LPref.setUser(getBaseContext(), user);
                if (param instanceof LoginPhoneParam) {
                    LPref.setLoginInfo(activity, (LoginPhoneParam) param);
                }
                onSignInCompleted(user);
                btnLogin.setEnabled(true);
                LDialogUtils.showDialog(dialog);
            }

            @Override
            public void onFail(Throwable e) {
                btnLogin.setEnabled(true);
                LDialogUtils.hideDialog(dialog);
                //onLoginFail(param instanceof SignInFBParam);
                //hideProgressDialog();
                //handleException(e);
            }
        });
    }


    public static class LogginMessage {
        private boolean isLogged;

        public boolean isLogged() {
            return isLogged;
        }

        public void setLogged(boolean logged) {
            isLogged = logged;
        }
    }

    protected void onSignInCompleted(UserProfile user) {
        AlertMessage.showSuccess(activity, getString(R.string.loggin_successfully));
        if (isCalledFromHomeMainLiveStream) {
            //check room info is edited or not
            String createAt = user.getRoom().getCreatedAt();
            String updateAt = user.getRoom().getUpdatedAt();
            boolean isRoomUpdateInfoBefore = LRoomUtil.isRoomUpdateInfoBefore(createAt, updateAt);
            if (isRoomUpdateInfoBefore) {
                sendEventLoginSuccess();
                onBackPressed();
            } else {
                Intent intent = new Intent(activity, RoomManagerActivity.class);
                intent.putExtra(Constants.ROOM_MANAGER_IS_CALL_FROM_LOGIN_ACTIVITY, true);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
                finish();
            }
        } else {
            sendEventLoginSuccess();
            onBackPressed();
        }
    }

    private void sendEventLoginSuccess() {
        //notify FrmHome user is logged in successfully
        LogginMessage logginMessage = new LogginMessage();
        logginMessage.setLogged(true);
        EventBus.getDefault().post(logginMessage);
        //end notify
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginPhoneParam loginPhoneParam = LPref.getLoginInfo(activity);
        if (loginPhoneParam != null) {
            edtUsername.getEditext().setText(loginPhoneParam.getPhone());
            edtUsername.getEditext().setSelection(edtUsername.getText().length());
            edtPassword.getEditext().setText(loginPhoneParam.getPassword());
        }
    }

    @Override
    protected void onDestroy() {
        LDialogUtils.hideDialog(dialog);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //event bus listen if user login success
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChangePassOtpActivity.ChangePassMessage changePassMessage) {
        if (changePassMessage != null) {
            if (changePassMessage.isChanged()) {
                finish();
            }
        }
    }
}
