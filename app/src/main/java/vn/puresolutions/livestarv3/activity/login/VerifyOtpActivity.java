package vn.puresolutions.livestarv3.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.login.Register;
import vn.puresolutions.livestar.corev3.api.model.v3.login.ResetPassOTP;
import vn.puresolutions.livestar.corev3.api.model.v3.login.VerifyResponse;
import vn.puresolutions.livestar.corev3.api.model.v3.login.param.LoginPhoneParam;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

import static vn.puresolutions.livestar.common.Constants.FORGOT_TO_VERIFY;
import static vn.puresolutions.livestar.common.Constants.LOGIN_DATA;
import static vn.puresolutions.livestar.common.Constants.OTP_VERIFY;
import static vn.puresolutions.livestar.common.Constants.PHONE_NUM;
import static vn.puresolutions.livestar.common.Constants.REGISTER_DATA;
import static vn.puresolutions.livestar.common.Constants.REGISTER_TO_VERIFY;
import static vn.puresolutions.livestar.common.Constants.RESET_CODE;

public class VerifyOtpActivity extends BaseActivity {
    private String num_one = "";
    private String num_two = "";
    private String num_three = "";
    private String num_four = "";
    private Register register;
    private LoginPhoneParam loginPhoneParam;
    private String getFlagIntent;
    @BindView(R.id.labOtpScreen_labHeader)
    LActionBar labHeader;
    @BindView(R.id.tvOtpScreen_OTP)
    TextView tvOtpCode;
    @BindView(R.id.edtOtpScreen_NumOne)
    EditText edtOtpScreen_NumOne;
    @BindView(R.id.edtOtpScreen_NumTwo)
    EditText edtOtpScreen_NumTwo;
    @BindView(R.id.edtOtpScreen_NumThree)
    EditText edtOtpScreen_NumThree;
    @BindView(R.id.edtOtpScreen_NumFour)
    EditText edtOtpScreen_NumFour;
    @BindView(R.id.btnOtpScreen_Resend)
    Button btnOtpScreen_Resend;
    @BindView(R.id.btnOtpScreen_Verify)
    Button btnOtpScreen_Verify;
    @BindView(R.id.tvOtpScreen_Detail)
    TextView tvOtpScreen_Detail;
    @BindView(R.id.tvOtpScreen_TopDetail)
    TextView tvOtpScreen_TopDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        loginPhoneParam = (LoginPhoneParam) getIntent().getSerializableExtra(LOGIN_DATA);
        register = (Register) getIntent().getSerializableExtra(REGISTER_DATA);
        getFlagIntent = (String) getIntent().getExtras().get(OTP_VERIFY);
        switch (getFlagIntent) {
            case REGISTER_TO_VERIFY:
                labHeader.setTvTitle(getString(R.string.loginscreen_signup_verify_phonenum_title));
                tvOtpCode.setText(String.format(getString(R.string.loginscreen_signup_verify_phonenum_label), register.getCode()));
                break;
            case FORGOT_TO_VERIFY:
                labHeader.setTvTitle(getString(R.string.loginscreen_forgot_title_otp));
                tvOtpCode.setText(String.format(getString(R.string.loginscreen_signup_verify_phonenum_label), register.getCode()));
                tvOtpScreen_Detail.setVisibility(View.GONE);
                tvOtpScreen_TopDetail.setVisibility(View.VISIBLE);
                tvOtpScreen_TopDetail.setText(getString(R.string.loginscreen_forgot_title_otp_topdetail));
                break;
        }
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
        autoFocus();
    }

    @OnClick(R.id.btnOtpScreen_Verify)
    void verifyOtp() {
        if (!validate()) {
            LDialogUtils.showDlg1Option(activity, 0, getString(R.string.noti), getString(R.string.dialog_content_fill_otp), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                    //do nothing
                }
            });
            return;
        }
        String otp = edtOtpScreen_NumOne.getText().toString() +
                edtOtpScreen_NumTwo.getText().toString() +
                edtOtpScreen_NumThree.getText().toString() +
                edtOtpScreen_NumFour.getText().toString();
        switch (getFlagIntent) {
            case REGISTER_TO_VERIFY:
                activeAccount(register, otp);
                break;
            case FORGOT_TO_VERIFY:
                verifyResetPass(register, otp);
                break;
        }
    }

    @OnClick(R.id.btnOtpScreen_Resend)
    void resendOtp() {
        switch (getFlagIntent) {
            case REGISTER_TO_VERIFY:
                LSService service = RestClient.createService(LSService.class);
                subscribe(service.resendOtpActive(register.getMobile()), new ApiSubscriber<Register>() {
                    @Override
                    public void onSuccess(Register result) {
                        if (result.getError() != null) {
                            if (result.getError() == 1304) {
                                LDialogUtils.showDlg1Option(activity, 0, getString(R.string.noti), getString(R.string.dialog_content_otp_error_limit), getString(R.string.ok), new LDialogUtils.Callback1() {
                                    @Override
                                    public void onClickButton0() {
                                        //do nothing
                                    }
                                });
                            }
                        } else {
                            tvOtpCode.setText(String.format(getString(R.string.loginscreen_signup_verify_phonenum_label), result.getCode()));
                            AlertMessage.showSuccess(activity, getString(R.string.resent_otp_success));
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        handleException(e);
                    }
                });
                break;
            case FORGOT_TO_VERIFY:
                LSService serviceChange = RestClient.createService(LSService.class);
                subscribe(serviceChange.requestResetPassword(register.getMobile()), new ApiSubscriber<Register>() {
                    @Override
                    public void onSuccess(Register result) {
                        tvOtpCode.setText(String.format(getString(R.string.loginscreen_signup_verify_phonenum_label), result.getCode()));
                    }

                    @Override
                    public void onFail(Throwable e) {
                        handleException(e);
                    }
                });
                break;
        }
    }

    private void activeAccount(Register register, String otp) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.activeAccount(register.getMobile(), otp), new ApiSubscriber<VerifyResponse>() {
            @Override
            public void onSuccess(VerifyResponse result) {
                if (result.getError() != null) {
                    //AlertMessage.showError(VerifyOtpActivity.this,);
                    if (result.getError() == 1303) {
                        LDialogUtils.showDlg1Option(activity, 0, getString(R.string.noti), getString(R.string.dialog_content_otp_error), getString(R.string.ok), new LDialogUtils.Callback1() {
                            @Override
                            public void onClickButton0() {
                                //do nothing
                            }
                        });
                    }
                } else {
                    if (result.isVerify()) {
                        LPref.setLoginInfo(activity, loginPhoneParam);
                        AlertMessage.showSuccess(activity, getString(R.string.register_complete_success));
                        onBackPressed();
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                int statusCode = ((HttpException) e).code();
                if (statusCode == 500) {
                    AlertMessage.showError(activity, getString(R.string.otp_code_is_invalid));
                } else {
                    handleException(e);
                }
            }
        });
    }

    private void autoFocus() {
        edtOtpScreen_NumOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtOtpScreen_NumOne.getText().toString().trim().length() == 1) {
                    edtOtpScreen_NumTwo.requestFocus();
                }
                num_one = s.toString().trim();
                if (num_one.length() >= 1 && num_two.length() >= 1 && num_three.length() >= 1 && num_four.length() >= 1) {
                    btnOtpScreen_Verify.setEnabled(true);
                    btnOtpScreen_Verify.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnOtpScreen_Verify.setBackgroundResource(R.drawable.background_button_non_enable);
                    btnOtpScreen_Verify.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        edtOtpScreen_NumTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtOtpScreen_NumTwo.getText().toString().trim().length() == 1) {
                    edtOtpScreen_NumThree.requestFocus();
                }
                num_two = s.toString().trim();
                if (num_one.length() >= 1 && num_two.length() >= 1 && num_three.length() >= 1 && num_four.length() >= 1) {
                    btnOtpScreen_Verify.setEnabled(true);
                    btnOtpScreen_Verify.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnOtpScreen_Verify.setBackgroundResource(R.drawable.background_button_non_enable);
                    btnOtpScreen_Verify.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        edtOtpScreen_NumThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtOtpScreen_NumThree.getText().toString().trim().length() == 1) {
                    edtOtpScreen_NumFour.requestFocus();
                }
                num_three = s.toString().trim();
                if (num_one.length() >= 1 && num_two.length() >= 1 && num_three.length() >= 1 && num_four.length() >= 1) {
                    btnOtpScreen_Verify.setEnabled(true);
                    btnOtpScreen_Verify.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnOtpScreen_Verify.setBackgroundResource(R.drawable.background_button_non_enable);
                    btnOtpScreen_Verify.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        edtOtpScreen_NumFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtOtpScreen_NumThree.getText().toString().trim().length() == 1) {
                    btnOtpScreen_Verify.requestFocus();
                }
                num_four = s.toString().trim();
                if (num_one.length() >= 1 && num_two.length() >= 1 && num_three.length() >= 1 && num_four.length() >= 1) {
                    btnOtpScreen_Verify.setEnabled(true);
                    btnOtpScreen_Verify.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnOtpScreen_Verify.setEnabled(false);
                    btnOtpScreen_Verify.setBackgroundResource(R.drawable.background_button_non_enable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

    }

    private void verifyResetPass(Register register, String otp) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.verifyResetPassword(register.getMobile(), otp), new ApiSubscriber<ResetPassOTP>() {
            @Override
            public void onSuccess(ResetPassOTP result) {
                if (result.getError() != null) {
                    if (result.getError() == 1303) {
                        LDialogUtils.showDlg1Option(activity, 0, getString(R.string.noti), getString(R.string.dialog_content_otp_error), getString(R.string.ok), new LDialogUtils.Callback1() {
                            @Override
                            public void onClickButton0() {
                                //do nothing
                            }
                        });
                    }
                } else {
                    Intent intent = new Intent(activity, ChangePassOtpActivity.class);
                    intent.putExtra(RESET_CODE, result.getCode());
                    intent.putExtra(PHONE_NUM, register.getMobile());
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(activity);
                    finish();
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edtOtpScreen_NumOne.getText().toString())) {
            return false;
        }
        if (TextUtils.isEmpty(edtOtpScreen_NumTwo.getText().toString())) {
            return false;
        }
        if (TextUtils.isEmpty(edtOtpScreen_NumThree.getText().toString())) {
            return false;
        }
        if (TextUtils.isEmpty(edtOtpScreen_NumFour.getText().toString())) {
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
        return R.layout.activity_verify_otp;
    }
}
