package vn.loitp.app.activity.customviews.imageview.panoramaimageview;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.imageview.panoramaimageview.GyroscopeObserver;
import vn.loitp.views.imageview.panoramaimageview.PanoramaImageView;

//https://github.com/gjiazhe/PanoramaImageView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5041
public class PanoramaImageViewActivity extends BaseActivity {
    private PanoramaImageView panoramaImageView;
    private GyroscopeObserver gyroscopeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gyroscopeObserver = new GyroscopeObserver();
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 9);
        panoramaImageView = (PanoramaImageView) findViewById(R.id.panorama_image_view);

        //panoramaImageView.setEnablePanoramaMode(true);
        //panoramaImageView.setEnableScrollbar(true);
        //panoramaImageView.setInvertScrollDirection(false);

        // Set GyroscopeObserver for PanoramaImageView.
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        panoramaImageView.setOnPanoramaScrollListener(new PanoramaImageView.OnPanoramaScrollListener() {
            @Override
            public void onScrolled(PanoramaImageView view, float offsetProgress) {
                LLog.d(TAG, "onScrolled offsetProgress " + offsetProgress);
            }
        });
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
        return R.layout.activity_panorama_imageview;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register GyroscopeObserver.
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister GyroscopeObserver.
        gyroscopeObserver.unregister();
    }
}
