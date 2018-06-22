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

public class SensorActivity extends BaseFontActivity {
    private RelativeLayout rl;
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(TAG, "onCreate");
        rl = (RelativeLayout) findViewById(R.id.rl);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);

        LImageUtil.load(activity, Constants.URL_IMG, iv);

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
            //rl.animate().rotation(0).start();

            // get screen size from DisplayMetrics if you need to rotate before the screen is shown
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            // if you are rotating after the view was shown
            // acquire width and height from the layout's parent's LayoutParams

            // calculate offset to move the view into correct position
            int offset = (width - height) / 2;

            // rotate the layout
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(height, width);
            getRootView().setLayoutParams(lp);
            // 90° clockwise
            getRootView().setRotation(0);
            getRootView().setTranslationX(offset);
            getRootView().setTranslationY(-offset);

        } else {
            //portrait -> landscape
            //rl.animate().rotation(90).start();

            // get screen size from DisplayMetrics if you need to rotate before the screen is shown
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            // if you are rotating after the view was shown
            // acquire width and height from the layout's parent's LayoutParams

            // calculate offset to move the view into correct position
            int offset = (width - height) / 2;

            // rotate the layout
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(height, width);
            getRootView().setLayoutParams(lp);
            // 90° clockwise
            getRootView().setRotation(90.0f);
            getRootView().setTranslationX(offset);
            getRootView().setTranslationY(-offset);
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
