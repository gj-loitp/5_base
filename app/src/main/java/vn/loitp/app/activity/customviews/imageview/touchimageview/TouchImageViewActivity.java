package vn.loitp.app.activity.customviews.imageview.touchimageview;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LImageUtil;
import com.views.imageview.touchimageview.lib.LTouchImageView;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;

//note when use with glide, must have placeholder
public class TouchImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LTouchImageView lTouchImageView = (LTouchImageView) findViewById(R.id.iv);
        LImageUtil.INSTANCE.load(getActivity(), Constants.INSTANCE.getURL_IMG(), lTouchImageView, R.mipmap.ic_launcher);
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
