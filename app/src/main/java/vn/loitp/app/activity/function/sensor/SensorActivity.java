package vn.loitp.app.activity.function.sensor;

import android.content.Context;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;

public class SensorActivity extends BaseFontActivity {
    private OrientationListener orientationListener;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(TAG, "onCreate");
        orientationListener = new OrientationListener(this);
        ll = (LinearLayout) findViewById(R.id.ll);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_sensor;
    }

    @Override
    public void onStart() {
        orientationListener.enable();
        super.onStart();
    }

    @Override
    public void onStop() {
        orientationListener.disable();
        super.onStop();
    }

    private class OrientationListener extends OrientationEventListener {
        final int ROTATION_O = 1;
        final int ROTATION_90 = 2;
        final int ROTATION_180 = 3;
        final int ROTATION_270 = 4;

        private int rotation = 0;

        public OrientationListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if ((orientation < 35 || orientation > 325) && rotation != ROTATION_O) { // PORTRAIT
                rotation = ROTATION_O;
                LLog.d(TAG, "ROTATION_O");
                ll.setRotation(0);
            } else if (orientation > 145 && orientation < 215 && rotation != ROTATION_180) { // REVERSE PORTRAIT
                rotation = ROTATION_180;
                LLog.d(TAG, "ROTATION_180");
                ll.setRotation(180);
            } else if (orientation > 55 && orientation < 125 && rotation != ROTATION_270) { // REVERSE LANDSCAPE
                rotation = ROTATION_270;
                LLog.d(TAG, "ROTATION_270");
                ll.setRotation(270);
            } else if (orientation > 235 && orientation < 305 && rotation != ROTATION_90) { //LANDSCAPE
                rotation = ROTATION_90;
                LLog.d(TAG, "ROTATION_90");
                ll.setRotation(90);
            }
        }
    }
}
