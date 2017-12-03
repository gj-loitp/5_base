package vn.loitp.app.activity.customviews.imageview.touchimageview;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.core.base.BaseActivity;
import vn.loitp.app.common.Constants;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.views.imageview.touchimageview.lib.LTouchImageView;

public class TouchImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LTouchImageView lTouchImageView = (LTouchImageView) findViewById(R.id.iv);
        LImageUtil.load(activity, Constants.URL_IMG, lTouchImageView);
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
        return R.layout.activity_touch_imageview;
    }
}
