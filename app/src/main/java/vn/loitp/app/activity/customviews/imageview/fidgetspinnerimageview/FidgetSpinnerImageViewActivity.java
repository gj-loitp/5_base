package vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.app.activity.customviews.imageview.strectchyimageview.lib.LStretchyImageView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.app.utilities.LImageUtil;
import vn.loitp.livestar.R;

public class FidgetSpinnerImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FidgetSpinner f = (FidgetSpinner)findViewById(R.id.fidgetspinner);
        f.setImageDrawable(R.drawable.spinner);
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
        return R.layout.activity_fidgetspinner_imageview;
    }
}
