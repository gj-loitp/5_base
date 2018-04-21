package vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.imageview.fidgetspinnerimageview.FidgetSpinner;

//https://github.com/Cutta/ContinuousScrollableImageView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6094
public class ContinuousScrollableImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //XML way

        //Classic Way
        /*image = new ContinuousScrollableImageView(this);
        image.setResourceId(R.drawable.bg_sample);
        image.setDirection(ContinuousScrollableImageView.DOWN);
        image.setScaleType(ContinuousScrollableImageView.FIT_XY);
        image.setDuration(3000);
        rootLayout.addView(image);*/

        //Builder Way
        /*image = new ContinuousScrollableImageView.Builder(MainActivity.this)
                .setResourceId(R.drawable.bg_sample)
                .setDirection(ContinuousScrollableImageView.UP)
                .setDuration(3000)
                .setScaleType(ContinuousScrollableImageView.FIT_XY)
                .build();
        rootLayout.addView(image);*/

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
        return R.layout.activity_continuousscrollable_imageview;
    }
}
