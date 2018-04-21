package vn.loitp.app.activity.customviews.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.dialog.iosdialog.DialogIOSActivity;
import vn.loitp.app.activity.customviews.dialog.originaldialog.DialogOriginalActivity;
import vn.loitp.app.activity.customviews.dialog.prettydialog.PrettyDialogActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class DialogMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_ios_dialog).setOnClickListener(this);
        findViewById(R.id.bt_original_dialog).setOnClickListener(this);
        findViewById(R.id.bt_pretty_dialog).setOnClickListener(this);
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
        return R.layout.activity_menu_dialog;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_ios_dialog:
                intent = new Intent(activity, DialogIOSActivity.class);
                break;
            case R.id.bt_original_dialog:
                intent = new Intent(activity, DialogOriginalActivity.class);
                break;
            case R.id.bt_pretty_dialog:
                intent = new Intent(activity, PrettyDialogActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
