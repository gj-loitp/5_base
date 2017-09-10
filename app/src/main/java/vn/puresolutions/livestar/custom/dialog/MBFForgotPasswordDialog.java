package vn.puresolutions.livestar.custom.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;

import vn.puresolutions.livestar.R;

/**
 * @author hoangphu
 * @since 12/20/16
 */

public class MBFForgotPasswordDialog extends MBFRegisterDialog {

    public MBFForgotPasswordDialog(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        tvTutorial.setText(Html.fromHtml(getContext().getString(R.string.forgot_password_sms_tutorial)));
        tvSMSSignIn.setText(Html.fromHtml(getContext().getString(R.string.sign_in_sms_forgot_password)));

        findViewById(R.id.ls_mbfSignUpDialog_btnComposite).setOnClickListener(v -> {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:9387"));
            sendIntent.putExtra("sms_body", "MK");
            getContext().startActivity(sendIntent);
        });
    }
}
