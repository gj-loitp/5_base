package vn.loitp.app.activity.function.fullscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.dialog.imersivedialog.ImmersiveDialogFragment;

public class FullScreenActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_toggle_fullscreen).setOnClickListener(this);
        findViewById(R.id.bt_show_dialog).setOnClickListener(this);
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
        return R.layout.activity_fullscreen;
    }

    private boolean isFullScreen;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_toggle_fullscreen:
                isFullScreen = !isFullScreen;
                LScreenUtil.toggleFullscreen(activity, isFullScreen);
                break;
            case R.id.bt_show_dialog:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        new ImmersiveDialogFragment().showImmersive(this);
    }
}
