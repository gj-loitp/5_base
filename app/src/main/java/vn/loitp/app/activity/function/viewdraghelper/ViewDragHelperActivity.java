package vn.loitp.app.activity.function.viewdraghelper;

import android.os.Bundle;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;

public class ViewDragHelperActivity extends BaseFontActivity {

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
