package vn.puresolutions.livestar.custom.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import vn.puresolutions.livestar.R;

/**
 * Created by khanh on 4/18/16.
 */
public class MBFRegisterDialog extends LSDialog {

    protected TextView tvTutorial;
    protected TextView tvSMSSignIn;

    public MBFRegisterDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mbf_register);

        tvTutorial = (TextView) findViewById(R.id.ls_mbfSignUpDialog_tvTutorial);
        tvSMSSignIn = (TextView) findViewById(R.id.ls_mbfSignUpDialog_tvSignInSMS);

        init();
    }

    protected void init() {
        tvTutorial.setText(Html.fromHtml(getContext().getString(R.string.sign_up_sms_tutorial)));
        tvSMSSignIn.setText(Html.fromHtml(getContext().getString(R.string.sign_in_sms)));

        findViewById(R.id.ls_mbfSignUpDialog_btnComposite).setOnClickListener(v -> {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:9387"));
            sendIntent.putExtra("sms_body", "DK");
            getContext().startActivity(sendIntent);
        });
    }
}