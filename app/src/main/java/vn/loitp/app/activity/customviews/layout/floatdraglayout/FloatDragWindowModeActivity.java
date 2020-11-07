package vn.loitp.app.activity.customviews.layout.floatdraglayout;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.annotation.IsFullScreen;
import com.annotation.LogTag;
import com.core.base.BaseActivity;
import com.views.layout.floatdraglayout.DisplayUtil;
import com.views.layout.floatdraglayout.FloatDragLayout;

import vn.loitp.app.R;

@LogTag("FloatDragWindowModeActivity")
@IsFullScreen(false)
public class FloatDragWindowModeActivity extends BaseActivity {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_splash_v3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout containerWindows = findViewById(R.id.flWindows);
        FloatDragLayout floatDragLayout = new FloatDragLayout(this);
        floatDragLayout.setBackgroundResource(R.mipmap.ic_launcher);
        int size = DisplayUtil.dp2px(this, 45);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
        floatDragLayout.setLayoutParams(layoutParams);

        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        containerWindows.addView(floatDragLayout, layoutParams);

        floatDragLayout.setOnClickListener(v ->
                showShortInformation("Click on the hover and drag buttons", true)
        );
    }

}
