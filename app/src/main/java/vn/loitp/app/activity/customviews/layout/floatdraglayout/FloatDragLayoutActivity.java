package vn.loitp.app.activity.customviews.layout.floatdraglayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;
import vn.loitp.views.layout.floatdraglayout.FloatDragLayout;
import vn.loitp.views.layout.floatdraglayout.FloatDragPopupWindow;

public class FloatDragLayoutActivity extends BaseFontActivity implements View.OnClickListener {

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

        floatDragLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LToast.show(activity, "Click on the hover and drag buttons");
            }
        });

        findViewById(R.id.bt_change_to_fullscreen).setOnClickListener(this);
        findViewById(R.id.bt_change_to_no_title).setOnClickListener(this);
        findViewById(R.id.bt_change_to_windows).setOnClickListener(this);
        findViewById(R.id.bt_show_floatDragPopupWindow).setOnClickListener(this);
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
        return R.layout.activity_float_drag_layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_change_to_fullscreen:
                startActivity(new Intent(activity, FloatDragFullScreenActivity.class));
                break;
            case R.id.bt_change_to_no_title:
                //startActivity(new Intent(activity, NoTitleActivity.class));
                break;
            case R.id.bt_change_to_windows:
                //startActivity(new Intent(activity, WindowActivity.class));
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
            contentView.setImageResource(R.mipmap.bg_room_blue_middle);
            floatDragPopupWindow = new FloatDragPopupWindow.Builder(this)
                    .setContentView(contentView)
                    .setPosition(0, 300)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LToast.show(activity, "Click on FloatDragPopupWindow");
                        }
                    })
                    .build();
        }

        floatDragPopupWindow.show();
    }
}
