package vn.loitp.app.activity.customviews.ldebugview;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.utils.util.ServiceUtils;
import vn.loitp.views.LToast;

import static vn.loitp.views.uizavideo.view.rl.UizaIMAVideo.CODE_DRAW_OVER_OTHER_APP_PERMISSION;

public class LDebugViewActivity extends BaseFontActivity implements OnClickListener {
    private Button btStart;
    private Button btStop;
    private Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btStart = (Button) findViewById(R.id.bt_start);
        btStop = (Button) findViewById(R.id.bt_stop);
        btSend = (Button) findViewById(R.id.bt_send);
        btStart.setOnClickListener(this);
        btStop.setOnClickListener(this);
        btSend.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (resultCode == RESULT_OK) {
                startService(new Intent(activity, LDebugViewService.class));
            } else {
                LToast.show(activity, "Draw over other app permission not available. Closing the application");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
        return R.layout.activity_l_debugview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                } else {
                    ServiceUtils.startService(LDebugViewService.class);
                    btStop.setEnabled(true);
                    btSend.setEnabled(true);
                }
                break;
            case R.id.bt_stop:
                ServiceUtils.stopService(LDebugViewService.class.getName());
                btStop.setEnabled(false);
                btSend.setEnabled(false);
                break;
            case R.id.bt_send:
                break;
        }
    }
}
