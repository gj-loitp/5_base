package vn.loitp.app.activity.function.sensorvideo;

import android.content.Context;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.layout.rotatelayout.RotateLayout;
import vn.loitp.views.uizavideo.view.frm.FrmUizaIMAVideo;

public class SensorVideoActivity extends BaseFontActivity {
    private RotateLayout rotateLayout;
    private OrientationListener orientationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(TAG, "onCreate");
        rotateLayout = (RotateLayout) findViewById(R.id.rotate_layout);

        /*iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFullScreen();
            }
        });*/

        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        FrmVideo frmVideo = new FrmVideo();
        LScreenUtil.replaceFragment(activity, fl.getId(), frmVideo, false);
        frmVideo.setFragmentCallback(new BaseFragment.FragmentCallback() {
            @Override
            public void onViewCreated() {
                LLog.d(TAG, "onViewCreated");
            }
        });

        int w = LScreenUtil.getScreenWidth();
        int h = w * 9 / 16;
        setSizeRelativeLayout(rotateLayout, w, h);

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
        return R.layout.activity_sensor_video;
    }

    private void setSizeRelativeLayout(View view, int w, int h) {
        LLog.d(TAG, "setSizeRelativeLayout " + w + "x" + h);
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
                LLog.d(TAG, "ROTATION_O");
                rotateLayout.setAngle(0);
                int w = LScreenUtil.getScreenWidth();
                int h = w * 9 / 16;
                setSizeRelativeLayout(rotateLayout, w, h);
                LScreenUtil.toggleFullscreen(activity, false);
            } else if (orientation > 145 && orientation < 215 && rotation != ROTATION_180) { // REVERSE PORTRAIT
                rotation = ROTATION_180;
                LLog.d(TAG, "ROTATION_180");
                //do nothing in this case
            } else if (orientation > 55 && orientation < 125 && rotation != ROTATION_270) { // REVERSE LANDSCAPE
                rotation = ROTATION_270;
                LLog.d(TAG, "ROTATION_270");
                rotateLayout.setAngle(90);
                int w = LScreenUtil.getScreenWidth();
                int h = LScreenUtil.getScreenHeightIncludeNavigationBar(activity);
                setSizeRelativeLayout(rotateLayout, w, h);
                LScreenUtil.toggleFullscreen(activity, true);
            } else if (orientation > 235 && orientation < 305 && rotation != ROTATION_90) { //LANDSCAPE
                rotation = ROTATION_90;
                LLog.d(TAG, "ROTATION_90");
                rotateLayout.setAngle(-90);
                int w = LScreenUtil.getScreenWidth();
                int h = LScreenUtil.getScreenHeightIncludeNavigationBar(activity);
                setSizeRelativeLayout(rotateLayout, w, h);
                LScreenUtil.toggleFullscreen(activity, true);
            }
        }
    }
}
