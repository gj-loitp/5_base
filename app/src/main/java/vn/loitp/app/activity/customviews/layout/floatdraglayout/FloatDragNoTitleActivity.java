package vn.loitp.app.activity.customviews.layout.floatdraglayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseActivity;
import com.views.layout.floatdraglayout.DisplayUtil;
import com.views.layout.floatdraglayout.FloatDragLayout;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_splash_v3)
@LogTag("FloatDragNoTitleActivity")
@IsFullScreen(false)
public class FloatDragNoTitleActivity extends BaseActivity {
    private View mDecorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDecorView = getWindow().getDecorView();
        ViewGroup rootView = mDecorView.findViewById(android.R.id.content);

        FloatDragLayout floatDragLayout = new FloatDragLayout(this);
        floatDragLayout.setBackgroundResource(R.drawable.l_heart_icon);
        int size = DisplayUtil.dp2px(this, 100);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
        floatDragLayout.setLayoutParams(layoutParams);

        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        rootView.addView(floatDragLayout, layoutParams);

        floatDragLayout.setOnClickListener(v ->
                showShortInformation("Click on the hover and drag buttons", true)
        );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
