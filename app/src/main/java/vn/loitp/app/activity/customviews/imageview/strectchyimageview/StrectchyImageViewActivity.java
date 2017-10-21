package vn.loitp.app.activity.customviews.imageview.strectchyimageview;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.app.activity.customviews.imageview.strectchyimageview.lib.LStretchyImageView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.app.utilities.LImageUtil;
import vn.loitp.livestar.R;

public class StrectchyImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LStretchyImageView lStretchyImageView = (LStretchyImageView) findViewById(R.id.iv);
        LImageUtil.load(activity, Constants.URL_IMG_LONG, lStretchyImageView);
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
        return R.layout.activity_strectchy_imageview;
    }
}
