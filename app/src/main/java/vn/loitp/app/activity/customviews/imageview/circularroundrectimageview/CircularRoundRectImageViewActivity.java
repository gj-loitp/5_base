package vn.loitp.app.activity.customviews.imageview.circularroundrectimageview;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

//guide: https://github.com/sparrow007/CircularImageview?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6166
public class CircularRoundRectImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return R.layout.activity_circular_roundrect_imageview;
    }
}
