package vn.loitp.app.activity.function.sensor;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;

public class SensorActivity extends BaseFontActivity {
    private RelativeLayout rl;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(TAG, "onCreate");
        rl = (RelativeLayout) findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFullScreen();
            }
        });

        int w = LScreenUtil.getScreenWidth();
        int h = w * 9 / 16;
        setSizeRelativeLayout(w, h);
    }

    private boolean isFullScreen;

    private void toggleFullScreen() {
        if (isFullScreen) {
            //landscape -> portrait
            rl.animate().rotation(0).start();
            int w = LScreenUtil.getScreenWidth();
            int h = w * 9 / 16;
            setSizeRelativeLayout(w, h);
        } else {
            //portrait -> landscape
            rl.animate().rotation(90).start();
            int w = LScreenUtil.getScreenWidth();
            int h = LScreenUtil.getScreenHeightIncludeNavigationBar(activity);
            setSizeRelativeLayout(w, h);
        }
        isFullScreen = !isFullScreen;
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

    private void setSizeRelativeLayout(int w, int h) {
        LLog.d(TAG, "setSizeRelativeLayout " + w + "x" + h);
        ViewGroup.LayoutParams params = rl.getLayoutParams();
        params.width = w;
        params.height = h;
        rl.setLayoutParams(params);
    }
}
