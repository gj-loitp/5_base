package vn.loitp.app.activity.function.sensor;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.layout.rotatelayout.RotateLayout;

public class SensorActivity extends BaseFontActivity {
    private RotateLayout rotateLayout;
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(TAG, "onCreate");
        rotateLayout = (RotateLayout) findViewById(R.id.rotate_layout);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        LImageUtil.load(activity, Constants.URL_IMG, iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFullScreen();
            }
        });

        int w = LScreenUtil.getScreenWidth();
        int h = w * 9 / 16;
        setSizeRelativeLayout(rotateLayout, w, h);
    }

    private boolean isFullScreen;

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

    private void setSizeRelativeLayout(View view, int w, int h) {
        LLog.d(TAG, "setSizeRelativeLayout " + w + "x" + h);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = w;
        params.height = h;
        view.setLayoutParams(params);
    }
}
