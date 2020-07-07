package vn.loitp.app.activity.demo.floatingwidget;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.core.base.BaseFontActivity;
import com.core.utilities.LDialogUtil;
import com.interfaces.Callback2;

import vn.loitp.app.R;

public class FloatingWidgetActivity extends BaseFontActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btNotifyMe).setOnClickListener(view -> {
            startService();
        });
    }

    private void startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            LDialogUtil.Companion.showDialog2(activity, "Permission", "Please open overlay permission", "Yes", "No", new Callback2() {
                @Override
                public void onClick1() {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                }

                @Override
                public void onClick2() {
                    //do nothing
                }
            });
        } else {
            startService(new Intent(getActivity(), FloatingViewService.class));
            onBackPressed();
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
        return R.layout.activity_demo_floating_widget;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            startService();
        }
    }
}
