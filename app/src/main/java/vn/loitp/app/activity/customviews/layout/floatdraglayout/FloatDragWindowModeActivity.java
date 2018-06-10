package vn.loitp.app.activity.customviews.layout.floatdraglayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;
import vn.loitp.views.layout.floatdraglayout.FloatDragLayout;

public class FloatDragWindowModeActivity extends BaseActivity {
    private FrameLayout containerWindows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerWindows = findViewById(R.id.fl_windows);
        FloatDragLayout floatDragLayout = new FloatDragLayout(this);
        floatDragLayout.setBackgroundResource(R.mipmap.ic_launcher);
        int size = DisplayUtil.dp2px(this, 45);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
        floatDragLayout.setLayoutParams(layoutParams);

        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        containerWindows.addView(floatDragLayout, layoutParams);

        floatDragLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LToast.show(activity, "Click on the hover and drag buttons");
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
        return R.layout.activity_splash_v3;
    }
}
