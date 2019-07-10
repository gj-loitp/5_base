package vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview;

import android.os.Bundle;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;

//https://github.com/Cutta/ContinuousScrollableImageView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6094
public class ContinuousScrollableImageViewActivity extends BaseFontActivity {

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
        /*image = new ContinuousScrollableImageView.Builder(MenuMotionLayoutActivity.this)
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
