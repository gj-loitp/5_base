package vn.loitp.app.activity.function.sensor;

import android.content.Context;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;

public class SensorActivity extends BaseFontActivity {
    private OrientationListener orientationListener;
    private RelativeLayout rl;
    private TextView tv;

    private int sizeW;
    private int sizeH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(TAG, "onCreate");
        sizeW = LScreenUtil.getScreenWidth();
        sizeH = LScreenUtil.getScreenHeightIncludeNavigationBar(activity);
        orientationListener = new OrientationListener(this);
        rl = (RelativeLayout) findViewById(R.id.rl);
        tv = (TextView) findViewById(R.id.tv);


        int w = sizeW;
        int h = sizeW * 9 / 16;
        setSizeRelativeLayout(w, h);
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
        public static final int ROTATION_O = 1;
        public static final int ROTATION_90 = 2;
        public static final int ROTATION_180 = 3;
        public static final int ROTATION_270 = 4;

        public int rotation = 0;

        public OrientationListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if ((orientation < 35 || orientation > 325) && rotation != ROTATION_O) { // PORTRAIT
                rotation = ROTATION_O;
                LLog.d(TAG, "ROTATION_O");
                tv.setText("PORTRAIT");

                int w = sizeW;
                int h = sizeW * 9 / 16;
                setSizeRelativeLayout(w, h);

                rl.animate().rotation(0).start();
            } else if (orientation > 145 && orientation < 215 && rotation != ROTATION_180) { // REVERSE PORTRAIT
                rotation = ROTATION_180;
                LLog.d(TAG, "ROTATION_180");
                //do nothing in this case
            } else if (orientation > 55 && orientation < 125 && rotation != ROTATION_270) { // REVERSE LANDSCAPE
                rotation = ROTATION_270;
                LLog.d(TAG, "ROTATION_270");
                tv.setText("REVERSE LANDSCAPE");

                int w = sizeH;
                int h = sizeW;
                setSizeRelativeLayout(w, h);

                rl.animate().rotation(-90).start();
            } else if (orientation > 235 && orientation < 305 && rotation != ROTATION_90) { //LANDSCAPE
                rotation = ROTATION_90;
                LLog.d(TAG, "ROTATION_90");
                tv.setText("LANDSCAPE");

                int w = sizeH;
                int h = sizeW;
                setSizeRelativeLayout(w, h);

                rl.animate().rotation(90).start();
            }
        }
    }

    private void setSizeRelativeLayout(int w, int h) {
        LLog.d(TAG, "setSizeRelativeLayout " + w + "x" + h);
        ViewGroup.LayoutParams params = rl.getLayoutParams();
        params.width = w;
        params.height = h;
        rl.setLayoutParams(params);
    }
}
