package vn.loitp.app.activity.customviews.dialog.customprogressdialog;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LUIUtil;

public class CustomProgressDialoglActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = LDialogUtil.showCustomProgressDialog(activity, 0.1f);
                LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
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
    protected int setLayoutResourceId() {
        return R.layout.activity_custom_progress_dialog;
    }
}
