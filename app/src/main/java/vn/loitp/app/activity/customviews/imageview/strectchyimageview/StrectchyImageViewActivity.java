package vn.loitp.app.activity.customviews.imageview.strectchyimageview;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LImageUtil;
import com.views.imageview.strectchyimageview.lib.LStretchyImageView;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;

public class StrectchyImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LStretchyImageView lStretchyImageView = (LStretchyImageView) findViewById(R.id.iv);
        LImageUtil.load(getActivity(), Constants.INSTANCE.getURL_IMG_LONG(), lStretchyImageView);
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
