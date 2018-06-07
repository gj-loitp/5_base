package vn.loitp.app.activity.customviews.compas;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.compass.Compass;
import vn.loitp.views.compass.CompassListener;

//https://github.com/arbelkilani/Compass-View?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6973
public class CompasActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Compass compass = findViewById(R.id.compass_view);
        compass.setListener(new CompassListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                LLog.d(TAG, "onSensorChanged : " + event);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                LLog.d(TAG, "onAccuracyChanged : sensor : " + sensor);
                LLog.d(TAG, "onAccuracyChanged : accuracy : " + accuracy);
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
        return R.layout.activity_compas;
    }
}
