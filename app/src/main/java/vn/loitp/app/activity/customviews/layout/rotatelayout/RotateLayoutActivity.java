package vn.loitp.app.activity.customviews.layout.rotatelayout;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.views.layout.rotatelayout.RotateLayout;

public class RotateLayoutActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RotateLayout rotateLayout = (RotateLayout) findViewById(R.id.rotate_layout);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int angle = LDeviceUtil.getRandomNumber(360);
                rotateLayout.setAngle(angle);
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
        return R.layout.activity_rotate_layout;
    }
}
