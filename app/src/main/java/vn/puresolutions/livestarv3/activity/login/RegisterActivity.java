package vn.puresolutions.livestarv3.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.login.Register;
import vn.puresolutions.livestar.corev3.api.model.v3.login.param.LoginPhoneParam;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LEdittextClear;
import vn.puresolutions.livestarv3.view.LEdittextPassword;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

import static vn.puresolutions.livestar.common.Constants.LOGIN_DATA;
import static vn.puresolutions.livestar.common.Constants.OTP_VERIFY;
import static vn.puresolutions.livestar.common.Constants.REGISTER_DATA;
import static vn.puresolutions.livestar.common.Constants.REGISTER_TO_VERIFY;

public class RegisterActivity extends BaseActivity {
    private String name = "";
    private String phone = "";
    private String pass = "";
    private String rePass = "";
    @BindView(R.id.labRegisterScreen_labHeader)
    LActionBar labRegister;
    @BindView(R.id.lecRegisterScreen_Name)
    LEdittextClear lecName;
    @BindView(R.id.lecRegisterScreen_Phone)
    LEdittextClear lecPhone;
    @BindView(R.id.lepRegisterScreen_Password)
    LEdittextPassword lepPass;
    @BindView(R.id.lepRegisterScreen_RePassword)
    LEdittextPassword lepRePass;
    @BindView(R.id.tvRegisterScreen_ErrorName)
    TextView tvErrorName;
    @BindView(R.id.tvRegisterScreen_ErrorPass)
    TextView tvErrorPass;
    @BindView(R.id.btnRegisterScreen_SignUp)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        labRegister.setTvTitle(getString(R.string.loginscreen_signup_title));
        lepPass.setHint(R.string.loginscreen_signup_password);
        lecName.setHint(R.string.loginscreen_signup_name_hint);
        lecPhone.setHint(R.string.loginscreen_signup_phonenum);
        lecPhone.setNumType();
        labRegister.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });
        lecName.getEditext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString().trim();
                if (name.length() >= 6 && phone.length() >= 10 && pass.length() >= 6 && rePass.length() >= 6) {
                    btnSignUp.setEnabled(true);
                    btnSignUp.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnSignUp.setEnabled(false);
                    btnSignUp.setBackgroundResource(R.drawable.background_button_non_enable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });
        lecName.getEditext().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (lecName.getText().length() < 6) {
                        tvErrorName.setTextColor(Color.RED);
                    } else {
                        tvErrorName.setTextColor(Color.BLACK);
                    }
                }
            }
        });

        lecPhone.getEditext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = s.toString().trim();
                if (phone.length() >= 10 && name.length() >= 6 && pass.length() >= 6 && rePass.length() >= 6) {
                    btnSignUp.setEnabled(true);
                    btnSignUp.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnSignUp.setEnabled(false);
                    btnSignUp.setBackgroundResource(R.drawable.background_button_non_enable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        lepPass.getEditext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass = s.toString().trim();
                if (name.length() >= 6 && phone.length() >= 10 && pass.length() >= 6 && rePass.length() >= 6) {
                    btnSignUp.setEnabled(true);
                    btnSignUp.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnSignUp.setEnabled(false);
                    btnSignUp.setBackgroundResource(R.drawable.background_button_non_enable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

        lepPass.getEditext().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (lepPass.getPassword().length() < 6) {
                        tvErrorPass.setTextColor(Color.RED);
                    } else {
                        tvErrorPass.setTextColor(Color.BLACK);
                    }
                }
            }
        });
        lepRePass.setHint(R.string.type_pass_again);
        lepRePass.getEditext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rePass = s.toString().trim();
                if (name.length() >= 6 && phone.length() >= 10 && pass.length() >= 6 && rePass.length() >= 6) {
                    btnSignUp.setEnabled(true);
                    btnSignUp.setBackgroundResource(R.drawable.background_register_btnregister);
                } else {
                    btnSignUp.setEnabled(false);
                    btnSignUp.setBackgroundResource(R.drawable.background_button_non_enable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });
    }

    @OnClick(R.id.btnRegisterScreen_SignUp)
    void doSignUp() {
        if (validate()) {
            signUp();
        }
    }

    private void signUp() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.register(lecName.getText(), lecPhone.getText(), lepPass.getPassword()), new ApiSubscriber<Register>() {
            @Override
            public void onSuccess(Register result) {
                if (result.getError() != null) {
                    if (result.getError() == 1002) {
                        AlertMessage.showError(RegisterActivity.this, R.string.dialog_content_register_phone_used);
                    }
                    if (result.getError() == 1003) {
                        AlertMessage.showError(RegisterActivity.this, R.string.dialog_content_register_phone_invalid);
                    }
                } else {
                    LoginPhoneParam param = new LoginPhoneParam();
                    param.setPhone(lecPhone.getText());
                    param.setPassword(lepPass.getPassword());
                    Intent intent = new Intent(RegisterActivity.this, VerifyOtpActivity.class);
                    intent.putExtra(OTP_VERIFY, REGISTER_TO_VERIFY);
                    intent.putExtra(REGISTER_DATA, result);
                    intent.putExtra(LOGIN_DATA, param);
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
        if (TextUtils.isEmpty(lecName.getText()) || lecName.getText().length() < 6) {
            LDialogUtils.showDlg1Option(this, 0, getString(R.string.noti), getString(R.string.dialog_content_fill_invalid), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                }
            });
            return false;
        }

        if (TextUtils.isEmpty(lecPhone.getText()) || lecPhone.getText().length() < 10) {
            LDialogUtils.showDlg1Option(this, 0, getString(R.string.noti), getString(R.string.dialog_content_phone_invalid), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                }
            });
            return false;
        }

        if (TextUtils.isEmpty(lepPass.getPassword()) && lepPass.getPassword().length() < 6) {
            return false;
        }

        if (!lepPass.getPassword().equalsIgnoreCase(lepRePass.getPassword())) {
            LDialogUtils.showDlg1Option(this, 0, getString(R.string.noti), getString(R.string.dialog_content_pass_not_match), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
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
        return R.layout.activity_register;
    }
}
