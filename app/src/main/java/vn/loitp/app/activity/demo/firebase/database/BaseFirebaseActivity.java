package vn.loitp.app.activity.demo.firebase.database;

import android.app.ProgressDialog;

import com.google.firebase.auth.FirebaseAuth;

import vn.loitp.core.base.BaseFontActivity;

/**
 * Created by LENOVO on 6/5/2018.
 */

public class BaseFirebaseActivity extends BaseFontActivity {
    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
