package vn.puresolutions.livestarv3.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.login.Register;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LEdittextClear;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

import static vn.puresolutions.livestar.common.Constants.FORGOT_TO_VERIFY;
import static vn.puresolutions.livestar.common.Constants.OTP_VERIFY;
import static vn.puresolutions.livestar.common.Constants.REGISTER_DATA;

public class ForgotPassActivity extends BaseActivity {
    @BindView(R.id.labForgotScreen_labHeader)
    LActionBar labHeader;
    @BindView(R.id.edtForgotScreen_Phone)
    LEdittextClear edtForgotScreen_Phone;
    @BindView(R.id.btnForgotScreen_GetPass)
    TextView btnForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        labHeader.setTvTitle(getString(R.string.loginscreen_forgot_title));
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
        edtForgotScreen_Phone.setCallback(new LEdittextClear.Callback() {
            @Override
            public void onTextChange(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    btnForgot.setEnabled(false);
                    btnForgot.setBackgroundResource(R.drawable.background_button_non_enable);
                } else {
                    btnForgot.setEnabled(true);
                    btnForgot.setBackgroundResource(R.drawable.background_register_btnregister);
                }
            }
        });
        edtForgotScreen_Phone.setHint(R.string.loginscreen_forgot_hint);
    }

    @OnClick(R.id.btnForgotScreen_GetPass)
    void getPass() {
        if (validate()) {
            btnForgot.setEnabled(false);
            LSService service = RestClient.createService(LSService.class);
            subscribe(service.requestResetPassword(edtForgotScreen_Phone.getText()), new ApiSubscriber<Register>() {
                @Override
                public void onSuccess(Register result) {
                    if (result.getError() != null) {
                        LDialogUtils.showDlg1Option(activity, 0, getString(R.string.noti), getString(R.string.dialog_content_forgot_error), getString(R.string.ok), new LDialogUtils.Callback1() {
                            @Override
                            public void onClickButton0() {
                                //do nothing
                            }
                        });
                    } else {
                        Intent intent = new Intent(activity, VerifyOtpActivity.class);
                        intent.putExtra(OTP_VERIFY, FORGOT_TO_VERIFY);
                        intent.putExtra(REGISTER_DATA, result);
                        startActivity(intent);
                        LUIUtil.transActivityFadeIn(activity);
                        finish();
                    }
                    btnForgot.setEnabled(true);
                }

                @Override
                public void onFail(Throwable e) {
                    btnForgot.setEnabled(true);
                    handleException(e);
                }
            });
        } else {
            LDialogUtils.showDlg1Option(this, 0, getString(R.string.noti), getString(R.string.dialog_content_phone_empty), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                    //do nothing
                }
            });
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edtForgotScreen_Phone.getText())) {
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
        return R.layout.activity_forgot_pass;
    }
}
