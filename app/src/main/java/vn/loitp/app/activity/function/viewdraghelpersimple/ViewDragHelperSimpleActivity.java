package vn.loitp.app.activity.function.viewdraghelpersimple;

import android.os.Bundle;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;

public class ViewDragHelperSimpleActivity extends BaseFontActivity {

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
        return R.layout.activity_view_draghelper_simple;
    }
}
