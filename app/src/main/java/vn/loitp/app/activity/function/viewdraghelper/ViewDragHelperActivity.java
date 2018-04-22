package vn.loitp.app.activity.function.viewdraghelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.function.dragdropsample.DragDropSampleActivity;
import vn.loitp.app.activity.function.fullscreen.FullScreenActivity;
import vn.loitp.app.activity.function.gesto.GestoActivity;
import vn.loitp.app.activity.function.hashmap.HashMapActivity;
import vn.loitp.app.activity.function.simplefingergestures.SimpleFingerGesturesActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class ViewDragHelperActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return R.layout.activity_view_drag_helper;
    }

}
