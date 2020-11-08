package vn.loitp.app.activity.customviews.layout.floatdraglayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.annotation.IsFullScreen;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.views.layout.floatdraglayout.DisplayUtil;
import com.views.layout.floatdraglayout.FloatDragLayout;
import com.views.layout.floatdraglayout.FloatDragPopupWindow;

import vn.loitp.app.R;

@LogTag("FloatDragLayoutActivity")
@IsFullScreen(false)
//TODO convert kotlin
public class FloatDragLayoutActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_float_drag_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        rootView = rootView.findViewById(android.R.id.content);

        FloatDragLayout floatDragLayout = new FloatDragLayout(this);
        floatDragLayout.setBackgroundResource(R.mipmap.ic_launcher);
        int size = DisplayUtil.dp2px(this, 45);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
        floatDragLayout.setLayoutParams(layoutParams);

        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        rootView.addView(floatDragLayout, layoutParams);

        floatDragLayout.setOnClickListener(v ->
                showShortInformation("Click on the hover and drag buttons", true)
        );

        findViewById(R.id.bt_change_to_fullscreen).setOnClickListener(this);
        findViewById(R.id.bt_change_to_no_title).setOnClickListener(this);
        findViewById(R.id.bt_change_to_windows).setOnClickListener(this);
        findViewById(R.id.bt_show_floatDragPopupWindow).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_change_to_fullscreen:
                startActivity(new Intent(this, FloatDragFullScreenActivity.class));
                break;
            case R.id.bt_change_to_no_title:
                startActivity(new Intent(this, FloatDragNoTitleActivity.class));
                break;
            case R.id.bt_change_to_windows:
                startActivity(new Intent(this, FloatDragWindowModeActivity.class));
                break;
            case R.id.bt_show_floatDragPopupWindow:
                showFloatDragPopupWindow();
                break;
            default:
                break;
        }
    }

    FloatDragPopupWindow floatDragPopupWindow;

    private void showFloatDragPopupWindow() {
        if (floatDragPopupWindow == null) {
            ImageView contentView = new ImageView(this);
            contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            contentView.setImageResource(R.mipmap.ic_launcher);
            floatDragPopupWindow = new FloatDragPopupWindow.Builder(this)
                    .setContentView(contentView)
                    .setPosition(0, 300)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showShortInformation("Click on FloatDragPopupWindow", true);
                        }
                    })
                    .build();
        }

        floatDragPopupWindow.show();
    }
}
