package vn.puresolutions.livestarv3.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserLogin;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.login.param.LoginPhoneParam;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LEdittextPassword;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LPref;

import static vn.puresolutions.livestar.common.Constants.PHONE_NUM;
import static vn.puresolutions.livestar.common.Constants.RESET_CODE;

public class ChangePassOtpActivity extends BaseActivity {
    private String code;
    private String phone;
    @BindView(R.id.labChangPassOtpScreen_labHeader)
    LActionBar labHeader;
    @BindView(R.id.lepChangPassOtpScreen_NewPass)
    LEdittextPassword lepNewPass;
    @BindView(R.id.lepChangPassOtpScreen_ReNewPass)
    LEdittextPassword lepReNewPass;
    @BindView(R.id.btnChangPassOtpScreen_Update)
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        code = getIntent().getStringExtra(RESET_CODE);
        phone = getIntent().getStringExtra(PHONE_NUM);
        labHeader.setTvTitle(getString(R.string.loginscreen_forgot_title_newpass));
        lepNewPass.setHint(R.string.changepass_otp_edt_pass);
        lepReNewPass.setHint(R.string.changepass_otp_edt_repass);
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });
        lepNewPass.getEditext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && lepReNewPass.getPassword().length() >= 6) {
                    btnUpdate.setEnabled(true);
                    btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnUpdate.setEnabled(false);
                    btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lepReNewPass.getEditext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && lepNewPass.getPassword().length() >= 6) {
                    btnUpdate.setEnabled(true);
                    btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnUpdate.setEnabled(false);
                    btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btnChangPassOtpScreen_Update)
    void doUpdatePass() {
        if (validate()) {
            /*LSService service = RestClient.createService(LSService.class);
            subscribe(service.resetPassword(code, lepNewPass.getPassword(), phone, false), new ApiSubscriber() {
                @Override
                public void onSuccess(Object result) {
                    LoginPhoneParam loginPhoneParam = new LoginPhoneParam();
                    loginPhoneParam.setPhone(phone);
                    loginPhoneParam.setPassword(lepNewPass.getPassword());
                    LPref.setLoginInfo(activity, loginPhoneParam);

                    LPref.setLoginInfo(activity, loginPhoneParam);
                    AlertMessage.showSuccess(activity, getString(R.string.changepass_success));
                    onBackPressed();
                }

                @Override
                public void onFail(Throwable e) {
                    handleException(e);
                }
            });*/
            LSService service = RestClient.createService(LSService.class);
            Observable<Void> changepass = service.resetPassword(code, lepNewPass.getPassword(), phone, false);
            Observable<UserLogin> login = changepass.flatMap(requestId -> service.login(phone, lepNewPass.getPassword()));
            Observable<UserProfile> getUserProfile = login.doOnNext(token -> LPref.setToken(getBaseContext(), token.getToken()))
                    .flatMap(token -> service.getProfile());
            subscribe(getUserProfile, new ApiSubscriber<UserProfile>() {
                @Override
                public void onSuccess(UserProfile result) {
                    LPref.setUser(getBaseContext(), result);
                    LoginPhoneParam loginPhoneParam = new LoginPhoneParam();
                    loginPhoneParam.setPhone(phone);
                    loginPhoneParam.setPassword(lepNewPass.getPassword());
                    LPref.setLoginInfo(activity, loginPhoneParam);

                    LPref.setLoginInfo(activity, loginPhoneParam);
                    setChangedPassSuccess();
                    onBackPressed();
                    AlertMessage.showSuccess(activity, "Bạn đã đổi mật khẩu và đăng nhập thành công !");
                }

                @Override
                public void onFail(Throwable e) {
                    handleException(e);
                }
            });

        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(lepNewPass.getPassword()) || lepNewPass.getPassword().length() < 6) {
            LDialogUtils.showDlg1Option(activity, 0, getString(R.string.noti), getString(R.string.dialog_content_fill_pass), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                    //do nothing
                }
            });
            return false;
        }

        if (!lepNewPass.getPassword().equals(lepReNewPass.getPassword())) {
            LDialogUtils.showDlg1Option(activity, 0, getString(R.string.noti), getString(R.string.dialog_content_pass_not_match), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                    //do nothing
                }
            });
            return false;
        }
        return true;
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
        return R.layout.activity_change_pass_otp;
    }

    public static class ChangePassMessage {
        private boolean isChanged;

        public boolean isChanged() {
            return isChanged;
        }

        public void setChanged(boolean changed) {
            isChanged = changed;
        }
    }

    private void setChangedPassSuccess() {
        ChangePassMessage logginMessage = new ChangePassMessage();
        logginMessage.setChanged(true);
        EventBus.getDefault().post(logginMessage);
    }
}
