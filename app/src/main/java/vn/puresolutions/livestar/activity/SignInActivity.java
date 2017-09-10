package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.param.SignInLSParam;
import vn.puresolutions.livestar.custom.dialog.AlertDialog;
import vn.puresolutions.livestar.custom.dialog.MBFRegisterDialog;
import vn.puresolutions.livestar.custom.view.LSProgressButton;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;

/**
 * Created by khanh on 8/19/16.
 */
public class SignInActivity extends FacebookSignInActivity implements NotificationCenter.NotificationCenterListener {
    public static final int REQUEST_CODE_REGISTER = 0x123;

    @BindView(R.id.signInActivity_edtUsername)
    EditText edtEmail;

    @BindView(R.id.signInActivity_edtPassword)
    EditText edtPassword;

    @BindView(R.id.signInActivity_btnSignIn)
    LSProgressButton btnSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        NotificationCenter.getInstance().addObserver(this, NotificationCenter.EnterMainScreen);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AlertMessage.closeAll();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.EnterMainScreen);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK) {
            showActiveEmailPopup();
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError(getString(R.string.email_empty_error));
            return false;
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            edtPassword.setError(getString(R.string.password_empty_error));
            return false;
        }

        return true;
    }

    @OnClick(R.id.signInActivity_btnSignIn)
    void signIn() {
        if (validate()) {
            if (btnSignIn.gettState() == LSProgressButton.ERROR) {
                btnSignIn.setState(LSProgressButton.IDLE);
            }
            btnSignIn.setState(LSProgressButton.LOADING);

            SignInLSParam param = new SignInLSParam();
            param.setEmail(edtEmail.getText().toString());
            param.setPassword(edtPassword.getText().toString());
            signIn(param);
        }
    }

//    @OnClick(R.id.ls_signInActivity_tvSkip)
//    public void skip(View view) {
//        if (!getIntent().getBooleanExtra(BUNDLE_KEY_IS_FINISH_WHEN_COMPLETED, false)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
//        finish();
//    }

    @OnClick(R.id.signInActivity_tvForgotPassword)
    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onLoginFail(boolean isSignInFB) {
        super.onLoginFail(isSignInFB);
        if (!isSignInFB) {
            btnSignIn.setState(LSProgressButton.ERROR);
        }
    }

    @OnClick(R.id.signInActivity_btnSignInFB)
    void signInFB() {
        signInFacebook();
    }

    @OnClick(R.id.signInActivity_btnSignInMBF)
    void signInMBF() {
        MBFRegisterDialog registerDialog = new MBFRegisterDialog(this);
        registerDialog.setPositiveButton(R.string.sign_in, Dialog::dismiss);
        registerDialog.show();
    }


    @OnClick(R.id.signInActivity_tvRegister)
    void register() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    private void showActiveEmailPopup() {
        AlertDialog dialog = new AlertDialog(this);
        dialog.setMessage(R.string.signUpSuccess);
        dialog.setButton(R.string.ok);
        dialog.show();
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.EnterMainScreen) {
            finish();
        }
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
        return R.layout.activity_sign_in;
    }
}
