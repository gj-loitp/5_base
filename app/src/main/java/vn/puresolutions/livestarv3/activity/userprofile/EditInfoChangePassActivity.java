package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.login.param.LoginPhoneParam;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.homescreen.HomeMainActivity;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LEdittextPassword;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

public class EditInfoChangePassActivity extends BaseActivity {
    @BindView(R.id.lepChangePass_OldPass)
    LEdittextPassword lepOldPass;
    @BindView(R.id.lepChangePass_NewPass)
    LEdittextPassword lepNewPass;
    @BindView(R.id.lepChangePass_ReNewPass)
    LEdittextPassword lepReNewPass;
    @BindView(R.id.btnChangePass_Update)
    Button btnUpdate;
    @BindView(R.id.labChangePass_Header)
    LActionBar labHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        labHeader.setTvTitle(getString(R.string.changepass_title));
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {

            }
        });
        lepOldPass.setHint(R.string.changepass_oldpass);
        lepNewPass.setHint(R.string.changepass_newpass);
        lepReNewPass.setHint(R.string.changepass_renewpass);

        lepOldPass.getEditext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0 && lepNewPass.getPassword().length() > 0 && lepReNewPass.getPassword().length() > 0) {
                    btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
                    btnUpdate.setEnabled(true);
                } else {
                    btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                    btnUpdate.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lepNewPass.getEditext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0 && lepOldPass.getPassword().length() > 0 && lepReNewPass.getPassword().length() > 0) {
                    btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
                    btnUpdate.setEnabled(true);
                } else {
                    btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                    btnUpdate.setEnabled(false);
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
                if (s.toString().trim().length() > 0 && lepOldPass.getPassword().length() > 0 && lepNewPass.getPassword().length() > 0) {
                    btnUpdate.setBackgroundResource(R.drawable.background_register_btnregister);
                    btnUpdate.setEnabled(true);
                } else {
                    btnUpdate.setBackgroundResource(R.drawable.background_button_non_enable);
                    btnUpdate.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btnChangePass_Update)
    void doUpdate() {
        if (validate()) {
            btnUpdate.setEnabled(false);
            LSService service = RestClient.createService(LSService.class);
            subscribe(service.changePassword(lepOldPass.getPassword(), lepNewPass.getPassword(), true), new ApiSubscriber() {
                @Override
                public void onSuccess(Object result) {
                    AlertMessage.showSuccess(EditInfoChangePassActivity.this, "Đổi mật khẩu thành công");
                    LDialogUtils.showDlg1Option(EditInfoChangePassActivity.this, 0, getString(R.string.noti), getString(R.string.dialog_content_changepass_success), getString(R.string.ok), new LDialogUtils.Callback1() {
                        @Override
                        public void onClickButton0() {
                            LoginPhoneParam loginPhoneParam = LPref.getLoginInfo(EditInfoChangePassActivity.this);
                            loginPhoneParam.setPassword(lepNewPass.getPassword());
                            LPref.setLoginInfo(EditInfoChangePassActivity.this, loginPhoneParam);

                            lepOldPass.clearText();
                            lepNewPass.clearText();
                            lepReNewPass.clearText();
                            lepOldPass.requestFocus();
                            btnUpdate.setEnabled(true);

                            Intent intent = new Intent(EditInfoChangePassActivity.this, HomeMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(EditInfoChangePassActivity.this);
                            finish();
                        }
                    });

                }

                @Override
                public void onFail(Throwable e) {
                    btnUpdate.setEnabled(true);
                    handleException(e);
                }
            });
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(lepOldPass.getPassword()) || TextUtils.isEmpty(lepNewPass.getPassword()) || TextUtils.isEmpty(lepReNewPass.getPassword())) {
            LDialogUtils.showDlg1Option(this, 0, getString(R.string.noti), getString(R.string.dialog_content_fill_invalid), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {

                }
            });
            return false;
        }
        if (TextUtils.isEmpty(lepNewPass.getPassword()) || lepReNewPass.getPassword().length() < 6) {
            LDialogUtils.showDlg1Option(this, 0, getString(R.string.noti), getString(R.string.dialog_content_fill_pass), getString(R.string.ok), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                }
            });
            return false;
        }

        if (!lepNewPass.getPassword().equals(lepReNewPass.getPassword())) {
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
        return R.layout.activity_edit_info_change_pass;
    }
}
