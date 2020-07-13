package vn.loitp.app.activity.function.sensor;

import android.content.Context;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LImageUtil;
import com.core.utilities.LLog;
import com.core.utilities.LScreenUtil;
import com.views.layout.rotatelayout.LRotateLayout;

import vn.loitp.app.R;
import vn.loitp.app.common.Constants;

public class SensorActivity extends BaseFontActivity {
    private LRotateLayout LRotateLayout;
    private TextView tv;
    private ImageView iv;
    private OrientationListener orientationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(getTAG(), "onCreate");
        LRotateLayout = (LRotateLayout) findViewById(R.id.rotateLayout);
        tv = (TextView) findViewById(R.id.textView);
        iv = (ImageView) findViewById(R.id.imageView);
        LImageUtil.Companion.load(getActivity(), Constants.Companion.getURL_IMG(), iv);

        /*iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFullScreen();
            }
        });*/

        int w = LScreenUtil.Companion.getScreenWidth();
        int h = w * 9 / 16;
        setSizeRelativeLayout(LRotateLayout, w, h);

        orientationListener = new OrientationListener(this);
    }

    /*private boolean isFullScreen;

    private void toggleFullScreen() {
        if (isFullScreen) {
            //landscape -> portrait
            rotateLayout.setAngle(0);
            int w = LScreenUtil.getScreenWidth();
            int h = w * 9 / 16;
            setSizeRelativeLayout(rotateLayout, w, h);
        } else {
            //portrait -> landscape
            rotateLayout.setAngle(-90);
            int w = LScreenUtil.getScreenWidth();
            int h = LScreenUtil.getScreenHeightIncludeNavigationBar(activity);
            setSizeRelativeLayout(rotateLayout, w, h);
        }
        isFullScreen = !isFullScreen;
        LScreenUtil.toggleFullscreen(activity, isFullScreen);
    }*/

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

    private void setSizeRelativeLayout(View view, int w, int h) {
        LLog.d(getTAG(), "setSizeRelativeLayout " + w + "x" + h);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = w;
        params.height = h;
        view.setLayoutParams(params);
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
                LLog.d(getTAG(), "ROTATION_O");
                LRotateLayout.setAngle(0);
                int w = LScreenUtil.Companion.getScreenWidth();
                int h = w * 9 / 16;
                setSizeRelativeLayout(LRotateLayout, w, h);
                LScreenUtil.Companion.toggleFullscreen(getActivity(), false);
            } else if (orientation > 145 && orientation < 215 && rotation != ROTATION_180) { // REVERSE PORTRAIT
                rotation = ROTATION_180;
                LLog.d(getTAG(), "ROTATION_180");
                //do nothing in this case
            } else if (orientation > 55 && orientation < 125 && rotation != ROTATION_270) { // REVERSE LANDSCAPE
                rotation = ROTATION_270;
                LLog.d(getTAG(), "ROTATION_270");
                LRotateLayout.setAngle(90);
                int w = LScreenUtil.Companion.getScreenWidth();
                int h = LScreenUtil.Companion.getScreenHeightIncludeNavigationBar(getActivity());
                setSizeRelativeLayout(LRotateLayout, w, h);
                LScreenUtil.Companion.toggleFullscreen(getActivity(), true);
            } else if (orientation > 235 && orientation < 305 && rotation != ROTATION_90) { //LANDSCAPE
                rotation = ROTATION_90;
                LLog.d(getTAG(), "ROTATION_90");
                LRotateLayout.setAngle(-90);
                int w = LScreenUtil.Companion.getScreenWidth();
                int h = LScreenUtil.Companion.getScreenHeightIncludeNavigationBar(getActivity());
                setSizeRelativeLayout(LRotateLayout, w, h);
                LScreenUtil.Companion.toggleFullscreen(getActivity(), true);
            }
        }
    }
}
