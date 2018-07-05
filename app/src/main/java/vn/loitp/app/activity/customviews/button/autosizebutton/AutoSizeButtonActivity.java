package vn.loitp.app.activity.customviews.button.autosizebutton;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.LToast;
import vn.loitp.views.autosize.imagebuttonwithsize.ImageButtonWithSize;

public class AutoSizeButtonActivity extends BaseFontActivity implements OnClickListener {
    private ImageButtonWithSize bt0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LLog.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_rotate).setOnClickListener(this);
        bt0 = (ImageButtonWithSize) findViewById(R.id.bt_0);
        bt0.setPortraitSizeWInDp(50);
        bt0.setPortraitSizeHInDp(50);
        bt0.setLandscapeSizeWInDp(150);
        bt0.setLandscapeSizeHInDp(150);
        bt0.setOnClickListener(this);
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
        return R.layout.activity_autosize_button;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_rotate:
                LActivityUtil.toggleScreenOritation(activity);
                break;
            case R.id.bt_0:
            case R.id.bt_1:
            case R.id.bt_2:
                LToast.show(activity, "Click");
                break;
        }
    }
}
