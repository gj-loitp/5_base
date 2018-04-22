package vn.loitp.app.activity.function;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.function.dragdropsample.DragDropSampleActivity;
import vn.loitp.app.activity.function.fullscreen.FullScreenActivity;
import vn.loitp.app.activity.function.gesto.GestoActivity;
import vn.loitp.app.activity.function.hashmap.HashMapActivity;
import vn.loitp.app.activity.function.simplefingergestures.SimpleFingerGesturesActivity;
import vn.loitp.app.activity.function.viewdraghelper.ViewDragHelperActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class MenuFunctionActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;

        findViewById(R.id.bt_gesto).setOnClickListener(this);
        findViewById(R.id.bt_simple_finger_gesture).setOnClickListener(this);
        findViewById(R.id.bt_hashmap).setOnClickListener(this);
        findViewById(R.id.bt_drag_drop_sample).setOnClickListener(this);
        findViewById(R.id.bt_toggle_fullscreen).setOnClickListener(this);
        findViewById(R.id.bt_view_drag_helper).setOnClickListener(this);
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
        return R.layout.activity_function_menu;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_gesto:
                intent = new Intent(activity, GestoActivity.class);
                break;
            case R.id.bt_simple_finger_gesture:
                intent = new Intent(activity, SimpleFingerGesturesActivity.class);
                break;
            case R.id.bt_hashmap:
                intent = new Intent(activity, HashMapActivity.class);
                break;
            case R.id.bt_drag_drop_sample:
                intent = new Intent(activity, DragDropSampleActivity.class);
                break;
            case R.id.bt_toggle_fullscreen:
                intent = new Intent(activity, FullScreenActivity.class);
                break;
            case R.id.bt_view_drag_helper:
                intent = new Intent(activity, ViewDragHelperActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
