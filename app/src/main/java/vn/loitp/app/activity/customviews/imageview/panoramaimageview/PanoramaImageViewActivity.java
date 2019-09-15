package vn.loitp.app.activity.customviews.imageview.panoramaimageview;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.views.imageview.panorama.GyroscopeObserver;
import com.views.imageview.panorama.LPanoramaImageView;

import loitp.basemaster.R;

//https://github.com/gjiazhe/PanoramaImageView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5041
public class PanoramaImageViewActivity extends BaseFontActivity {
    private LPanoramaImageView LPanoramaImageView;
    private GyroscopeObserver gyroscopeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gyroscopeObserver = new GyroscopeObserver();
        // Set the maximum radian the device should rotate to show image's bounds.
        // It should be set between 0 and π/2.
        // The default value is π/9.
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 9);
        LPanoramaImageView = (LPanoramaImageView) findViewById(R.id.panorama_image_view);

        //panoramaImageView.setEnablePanoramaMode(true);
        //panoramaImageView.setEnableScrollbar(true);
        //panoramaImageView.setInvertScrollDirection(false);

        // Set GyroscopeObserver for PanoramaImageView.
        LPanoramaImageView.setGyroscopeObserver(gyroscopeObserver);

        LPanoramaImageView.setOnPanoramaScrollListener(new LPanoramaImageView.OnPanoramaScrollListener() {
            @Override
            public void onScrolled(LPanoramaImageView view, float offsetProgress) {
                LLog.INSTANCE.d(getTAG(), "onScrolled offsetProgress " + offsetProgress);
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
