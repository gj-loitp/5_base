package vn.loitp.app.activity.customviews.imageview.strectchyimageview;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.core.base.BaseActivity;
import vn.loitp.app.common.Constants;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.views.imageview.strectchyimageview.lib.LStretchyImageView;

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
    protected int setLayoutResourceId() {
        return R.layout.activity_strectchy_imageview;
    }
}
