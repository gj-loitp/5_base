package vn.loitp.app.activity.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.function.activityandservice.ActivityServiceComunicateActivity;
import vn.loitp.app.activity.function.bsimagepicker.BSImagePickerActivity;
import vn.loitp.app.activity.function.dragdropsample.DragDropSampleActivity;
import vn.loitp.app.activity.function.fullscreen.FullScreenActivity;
import vn.loitp.app.activity.function.gesto.GestoActivity;
import vn.loitp.app.activity.function.glide.GlideActivity;
import vn.loitp.app.activity.function.hashmap.HashMapActivity;
import vn.loitp.app.activity.function.location.LocationActivity;
import vn.loitp.app.activity.function.notification.MenuNotificationActivity;
import vn.loitp.app.activity.function.recolor.RecolorActivity;
import vn.loitp.app.activity.function.scrog.ScrogActivity;
import vn.loitp.app.activity.function.sensor.SensorActivity;
import vn.loitp.app.activity.function.simplefingergestures.SimpleFingerGesturesActivity;
import vn.loitp.app.activity.function.viewdraghelper.ViewDragHelperActivity;
import vn.loitp.app.activity.function.viewdraghelpersimple.ViewDragHelperSimpleActivity;
import vn.loitp.app.activity.function.viewdraghelpersimple.ViewDragHelperSimpleActivity1;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class MenuFunctionActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExit = false;

        findViewById(R.id.bt_gesto).setOnClickListener(this);
        findViewById(R.id.bt_simple_finger_gesture).setOnClickListener(this);
        findViewById(R.id.bt_hashmap).setOnClickListener(this);
        findViewById(R.id.bt_drag_drop_sample).setOnClickListener(this);
        findViewById(R.id.bt_toggle_fullscreen).setOnClickListener(this);
        findViewById(R.id.bt_view_drag_helper).setOnClickListener(this);
        findViewById(R.id.bt_recolor).setOnClickListener(this);
        findViewById(R.id.bt_activity_service_comunicate).setOnClickListener(this);
        findViewById(R.id.bt_location).setOnClickListener(this);
        findViewById(R.id.bt_notification).setOnClickListener(this);
        findViewById(R.id.bt_view_drag_helper_simple).setOnClickListener(this);
        findViewById(R.id.bt_view_drag_helper_simple_1).setOnClickListener(this);
        findViewById(R.id.bt_sensor).setOnClickListener(this);
        findViewById(R.id.bt_scrog).setOnClickListener(this);
        findViewById(R.id.bt_glide).setOnClickListener(this);
        findViewById(R.id.bt_bs_image_picker).setOnClickListener(this);
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
            case R.id.bt_recolor:
                intent = new Intent(activity, RecolorActivity.class);
                break;
            case R.id.bt_activity_service_comunicate:
                intent = new Intent(activity, ActivityServiceComunicateActivity.class);
                break;
            case R.id.bt_location:
                intent = new Intent(activity, LocationActivity.class);
                break;
            case R.id.bt_notification:
                intent = new Intent(activity, MenuNotificationActivity.class);
                break;
            case R.id.bt_view_drag_helper_simple:
                intent = new Intent(activity, ViewDragHelperSimpleActivity.class);
                break;
            case R.id.bt_view_drag_helper_simple_1:
                intent = new Intent(activity, ViewDragHelperSimpleActivity1.class);
                break;
            case R.id.bt_sensor:
                intent = new Intent(activity, SensorActivity.class);
                break;
            case R.id.bt_scrog:
                intent = new Intent(activity, ScrogActivity.class);
                break;
            case R.id.bt_glide:
                intent = new Intent(activity, GlideActivity.class);
                break;
            case R.id.bt_bs_image_picker:
                intent = new Intent(activity, BSImagePickerActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
