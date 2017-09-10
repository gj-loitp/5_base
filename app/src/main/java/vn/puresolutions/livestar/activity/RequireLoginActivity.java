package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.custom.dialog.MBFRegisterDialog;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/20/2015
 */
public class RequireLoginActivity extends FacebookSignInActivity {
    public static final String BUNDLE_KEY_NOTE = "note";

    @BindView(R.id.requireLoginActivity_btnSignIn)
    Button btnSignIn;

    @BindView(R.id.requireLoginActivity_btnSignUp)
    Button btnSignUp;

    @BindView(R.id.requireLoginActivity_btnMBF)
    View btnMBF;

    @BindView(R.id.requireLoginActivity_tvWelcome)
    TextView tvWelcome;

    @BindView(R.id.requireLoginActivity_tvNote)
    TextView tvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        String note = getIntent().getStringExtra(BUNDLE_KEY_NOTE);
        if (!TextUtils.isEmpty(note)) {
            tvNote.setText(note);
        }

        if (TextUtils.isEmpty(UserInfo.getPhoneNumber())) {
            tvWelcome.setVisibility(View.GONE);
            btnMBF.setVisibility(View.GONE);

            btnSignIn.setVisibility(View.VISIBLE);
            btnSignUp.setVisibility(View.VISIBLE);
        } else {
            tvWelcome.setText(getString(R.string.welcome_phone_number, UserInfo.getPhoneNumber()));
            tvWelcome.setVisibility(View.VISIBLE);
            btnMBF.setVisibility(View.VISIBLE);

            btnSignIn.setVisibility(View.GONE);
            btnSignUp.setVisibility(View.GONE);
        }

        overridePendingTransition(R.anim.enter_up, R.anim.exit_up);
    }

    @OnClick(R.id.requireLoginActivity_btnSignInFB)
    public void onSignInFbClick(View view) {
        signInFacebook();
    }

    @OnClick({R.id.requireLoginActivity_vwEmpty,
            R.id.requireLoginActivity_imgClose})
    public void onEmptyViewClick(View view) {
        finish();
    }

    @OnClick(R.id.requireLoginActivity_btnSignIn)
    public void onSignInClick(View view) {
        startActivity(SignInActivity.class);
    }

    @OnClick(R.id.requireLoginActivity_btnSignUp)
    public void onSignUpClick(View view) {
        startActivity(SignUpActivity.class);
    }

    @OnClick(R.id.requireLoginActivity_btnMBF)
    void onBtnMBFClick() {
        MBFRegisterDialog registerDialog = new MBFRegisterDialog(this);
        registerDialog.setPositiveButton(R.string.sign_in, dialog -> startActivity(SignInActivity.class));
        registerDialog.show();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onSignInCompleted() {
        setResult(RESULT_OK);
        super.onSignInCompleted();
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
        return R.layout.dialog_login;
    }
}
