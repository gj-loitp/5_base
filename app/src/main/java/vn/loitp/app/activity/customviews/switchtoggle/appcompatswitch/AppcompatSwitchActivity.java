package vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import vn.loitp.core.base.BaseActivity;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LStoreUtil;

public class AppcompatSwitchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(LStoreUtil.readTxtFromRawFolder(activity, R.raw.lswitch));
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
        return R.layout.activity_appcompat_switch;
    }
}
