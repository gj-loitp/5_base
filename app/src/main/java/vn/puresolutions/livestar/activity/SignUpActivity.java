package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.param.SignInLSParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.AccountService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.custom.view.LSProgressButton;
import vn.puresolutions.livestarv3.utilities.old.ValidateUtils;
import vn.puresolutions.livestarv3.base.BaseActivity;

/**
 * Created by khanh on 8/19/16.
 */
public class SignUpActivity extends BaseActivity {

    @BindView(R.id.signUpActivity_tvTermOfUser)
    TextView tvTermOfUser;

    @BindView(R.id.signUpActivity_chbAgreeTOS)
    CheckBox cbTermOfUser;

    @BindView(R.id.signUpActivity_edtUsername)
    EditText edtEmail;

    @BindView(R.id.signUpActivity_edtPassword)
    EditText edtPassword;

    @BindView(R.id.signUpActivity_edtConfirmPassword)
    EditText edtConfirmPassword;

    @BindView(R.id.signUpActivity_btnSignUp)
    LSProgressButton btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError(getString(R.string.email_empty_error));
            return false;
        }

        if (!ValidateUtils.validateEmail(edtEmail.getText().toString())) {
            edtEmail.setError(getString(R.string.email_invalid_error));
            return false;
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            edtPassword.setError(getString(R.string.password_empty_error));
            return false;
        }

        if (TextUtils.isEmpty(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError(getString(R.string.confirm_password_empty_error));
            return false;
        }

        if (!edtConfirmPassword.getText().toString()
                .equals(edtPassword.getText().toString())) {
            edtConfirmPassword.setError(getString(R.string.password_not_match));
            return false;
        }
        if (!cbTermOfUser.isChecked()) {
            Toast.makeText(this, getString(R.string.accept_term_of_service), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.signUpActivity_tvTermOfUser)
    void openPolicy() {
        String url = getString(R.string.livestar_policy);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @OnClick(R.id.signUpActivity_btnSignUp)
    void signUp() {
        if (validate()) {
            if (btnSignUp.gettState() == LSProgressButton.ERROR) {
                btnSignUp.setState(LSProgressButton.IDLE);
            }
            btnSignUp.setState(LSProgressButton.LOADING);

            SignInLSParam param = new SignInLSParam();
            param.setEmail(edtEmail.getText().toString());
            param.setPassword(edtPassword.getText().toString());

            AccountService service = RestClient.createService(AccountService.class);
            subscribe(service.register(param), new ApiSubscriber() {
                @Override
                public void onSuccess(Object result) {
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onFail(Throwable e) {
                    btnSignUp.setState(LSProgressButton.ERROR);
                    Log.i("SignUpActivity", "e: " + e.toString());
                    handleException(e);
                }
            });
        }
    }

    //    @OnClick(R.id.ls_signUpActivity_tvSkip)
//    public void skip(View view) {
//        if (!getIntent().getBooleanExtra(BUNDLE_KEY_IS_FINISH_WHEN_COMPLETED, false)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
//        finish();
//    }
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
        return R.layout.activity_sign_up;
    }
}
