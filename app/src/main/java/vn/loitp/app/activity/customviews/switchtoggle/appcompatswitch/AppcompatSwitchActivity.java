package vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LStoreUtil;

import loitp.basemaster.R;

public class AppcompatSwitchActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(LStoreUtil.readTxtFromRawFolder(getActivity(), R.raw.lswitch));
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
