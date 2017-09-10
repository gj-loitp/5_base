package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.Phone;
import vn.puresolutions.livestar.core.api.model.param.EmailParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.AccountService;
import vn.puresolutions.livestar.custom.dialog.MBFForgotPasswordDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.custom.view.LSProgressButton;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.old.ValidateUtils;
import vn.puresolutions.livestarv3.base.BaseActivity;

/**
 * @author hoangphu
 * @since 12/20/16
 */

public class ForgotPasswordActivity extends BaseActivity {
    @BindView(R.id.forgotPasswordActivity_edtUsername)
    EditText edtUsername;
    @BindView(R.id.forgotPasswordActivity_btnForgotPassword)
    LSProgressButton btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        edtUsername.setOnEditorActionListener((v, actionId, event) -> {
            int action = actionId & EditorInfo.IME_MASK_ACTION;
            if (action == EditorInfo.IME_ACTION_DONE) {
                submit();
            }
            return false;
        });
    }

    @OnClick(R.id.forgotPasswordActivity_btnForgotPassword)
    void submit() {
        String input = edtUsername.getText().toString().trim();
        if (ValidateUtils.validateEmail(input)) {
            if (btnSubmit.gettState() == LSProgressButton.ERROR) {
                btnSubmit.setState(LSProgressButton.IDLE);
            }
            btnSubmit.setState(LSProgressButton.LOADING);

            AccountService service = RestClient.createService(AccountService.class);
            subscribe(service.forgotPassword(new EmailParam(input)), new ApiSubscriber() {
                @Override
                public void onSuccess(Object result) {
                    finish();
                    AlertMessage.showSuccess(getApplicationContext(), R.string.forgot_password_successfully);
                }

                @Override
                public void onFail(Throwable e) {
                    btnSubmit.setState(LSProgressButton.ERROR);
                    handleException(e);
                }
            });
        } else if (ValidateUtils.validatePhoneNumber(input)) {
            if (btnSubmit.gettState() == LSProgressButton.ERROR) {
                btnSubmit.setState(LSProgressButton.IDLE);
            }
            btnSubmit.setState(LSProgressButton.LOADING);

            if (input.charAt(0) == '0') {
                input = String.format("84%s", input.substring(1));
            }
            AccountService service = RestClient.createService(AccountService.class);
            subscribe(service.checkUserMbf(new Phone(input)), new ApiSubscriber() {
                @Override
                public void onSuccess(Object result) {
                    btnSubmit.setState(LSProgressButton.IDLE);
                    showForgotPasswordDialog();
                }

                @Override
                public void onFail(Throwable e) {
                    btnSubmit.setState(LSProgressButton.ERROR);
                    handleException(e);
                }
            });
        } else {
            AlertMessage.showError(this, R.string.email_or_phone_numer_invalid);
        }
    }

    private void showForgotPasswordDialog() {
        MBFForgotPasswordDialog forgotPasswordDialog = new MBFForgotPasswordDialog(this);
        forgotPasswordDialog.setPositiveButton(R.string.sign_in, dialog -> finish());
        forgotPasswordDialog.show();
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
        return R.layout.activity_forgot_password;
    }
}
