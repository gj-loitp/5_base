package vn.loitp.app.activity.customviews.imageview.circleimageview;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

public class CircleImageViewActivity extends BaseFontActivity {

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
        return R.layout.activity_circle_imageview;
    }
}
