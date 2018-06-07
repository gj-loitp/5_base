package vn.loitp.app.activity.customviews.imageview.touchimageview;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.views.imageview.touchimageview.lib.LTouchImageView;

//note when use with glide, must have placeholder
public class TouchImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LTouchImageView lTouchImageView = (LTouchImageView) findViewById(R.id.iv);
        LImageUtil.load(activity, Constants.URL_IMG, lTouchImageView, R.mipmap.ic_launcher);
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
        return R.layout.activity_touch_imageview;
    }
}
