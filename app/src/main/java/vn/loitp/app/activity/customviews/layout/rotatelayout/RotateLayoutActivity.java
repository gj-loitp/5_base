package vn.loitp.app.activity.customviews.layout.rotatelayout;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LDeviceUtil;
import com.views.layout.rotatelayout.LRotateLayout;

import loitp.basemaster.R;

public class RotateLayoutActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LRotateLayout LRotateLayout = findViewById(R.id.rotate_layout);

        findViewById(R.id.bt).setOnClickListener(v -> {
            final int angle = LDeviceUtil.Companion.getRandomNumber(360);
            LRotateLayout.setAngle(angle);
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
        return R.layout.activity_rotate_layout;
    }
}
