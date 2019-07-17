package vn.loitp.app.activity.function.fullscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LScreenUtil;
import com.views.dialog.imersivedialog.ImmersiveDialogFragment;

import loitp.basemaster.R;

public class FullScreenActivity extends BaseFontActivity implements View.OnClickListener {

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
                LScreenUtil.INSTANCE.toggleFullscreen(getActivity(), isFullScreen);
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
