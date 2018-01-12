package vn.loitp.app.activity.customviews.ariana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.ariana.iv.ArianaImageViewActivity;
import vn.loitp.app.activity.customviews.dialog.iosdialog.DialogIOSActivity;
import vn.loitp.app.activity.customviews.dialog.originaldialog.DialogOriginalActivity;
import vn.loitp.app.activity.customviews.dialog.prettydialog.PrettyDialogActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class ArianaMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_ariana_imageview).setOnClickListener(this);
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
        return R.layout.activity_menu_ariana;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_ariana_imageview:
                intent = new Intent(activity, ArianaImageViewActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
