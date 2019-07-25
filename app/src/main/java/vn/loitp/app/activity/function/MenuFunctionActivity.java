package vn.loitp.app.activity.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;
import vn.loitp.app.activity.function.activityandservice.ActivityServiceComunicateActivity;
import vn.loitp.app.activity.function.dragdropsample.DragDropSampleActivity;
import vn.loitp.app.activity.function.fullscreen.FullScreenActivity;
import vn.loitp.app.activity.function.gesto.GestoActivity;
import vn.loitp.app.activity.function.glide.GlideActivity;
import vn.loitp.app.activity.function.hashmap.HashMapActivity;
import vn.loitp.app.activity.function.keyboard.KeyboardActivity;
import vn.loitp.app.activity.function.location.LocationActivity;
import vn.loitp.app.activity.function.notification.MenuNotificationActivity;
import vn.loitp.app.activity.function.recolor.RecolorActivity;
import vn.loitp.app.activity.function.sensor.SensorActivity;
import vn.loitp.app.activity.function.simplefingergestures.SimpleFingerGesturesActivity;
import vn.loitp.app.activity.function.viewdraghelper.ViewDragHelperActivity;
import vn.loitp.app.activity.function.viewdraghelpersimple.ViewDragHelperSimpleActivity;
import vn.loitp.app.activity.function.viewdraghelpersimple.ViewDragHelperSimpleActivity1;

public class MenuFunctionActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowAdWhenExit(false);

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
        findViewById(R.id.bt_glide).setOnClickListener(this);
        findViewById(R.id.bt_keyboard).setOnClickListener(this);
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
                intent = new Intent(getActivity(), GestoActivity.class);
                break;
            case R.id.bt_simple_finger_gesture:
                intent = new Intent(getActivity(), SimpleFingerGesturesActivity.class);
                break;
            case R.id.bt_hashmap:
                intent = new Intent(getActivity(), HashMapActivity.class);
                break;
            case R.id.bt_drag_drop_sample:
                intent = new Intent(getActivity(), DragDropSampleActivity.class);
                break;
            case R.id.bt_toggle_fullscreen:
                intent = new Intent(getActivity(), FullScreenActivity.class);
                break;
            case R.id.bt_view_drag_helper:
                intent = new Intent(getActivity(), ViewDragHelperActivity.class);
                break;
            case R.id.bt_recolor:
                intent = new Intent(getActivity(), RecolorActivity.class);
                break;
            case R.id.bt_activity_service_comunicate:
                intent = new Intent(getActivity(), ActivityServiceComunicateActivity.class);
                break;
            case R.id.bt_location:
                intent = new Intent(getActivity(), LocationActivity.class);
                break;
            case R.id.bt_notification:
                intent = new Intent(getActivity(), MenuNotificationActivity.class);
                break;
            case R.id.bt_view_drag_helper_simple:
                intent = new Intent(getActivity(), ViewDragHelperSimpleActivity.class);
                break;
            case R.id.bt_view_drag_helper_simple_1:
                intent = new Intent(getActivity(), ViewDragHelperSimpleActivity1.class);
                break;
            case R.id.bt_sensor:
                intent = new Intent(getActivity(), SensorActivity.class);
                break;
            case R.id.bt_glide:
                intent = new Intent(getActivity(), GlideActivity.class);
                break;
            case R.id.bt_keyboard:
                intent = new Intent(getActivity(), KeyboardActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(getActivity());
        }
    }
}
