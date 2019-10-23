package vn.loitp.app.activity.demo.twoinstanceactivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import loitp.basemaster.R;

public class Activity3 extends BaseFontActivity {
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(getTAG(), "suzuki onCreate");
        findViewById(R.id.bt_go_to_1).setOnClickListener(view -> {
            Intent intent = new Intent(Activity3.this, Activity1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        findViewById(R.id.bt_go_to_2).setOnClickListener(view -> {
            Intent intent = new Intent(Activity3.this, Activity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            askPermission();
        }
        findViewById(R.id.bt_go_to_float).setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                startService(new Intent(Activity3.this, FloatingViewService.class));
                //finish();
            } else if (Settings.canDrawOverlays(Activity3.this)) {
                startService(new Intent(Activity3.this, FloatingViewService.class));
                //finish();
            } else {
                askPermission();
                Toast.makeText(Activity3.this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(getTAG(), "onNewIntent");
        if ((intent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) > 0) {
            mIsRestoredToTop  = true;
        }
    }

    private boolean mIsRestoredToTop;

    @Override
    public void finish() {
        super.finish();
        if (Build.VERSION.SDK_INT >= 19 && !isTaskRoot() && mIsRestoredToTop) {
            // 4.4.2 platform issues for FLAG_ACTIVITY_REORDER_TO_FRONT,
            // reordered activity back press will go to home unexpectly,
            // Workaround: move reordered activity current task to front when it's finished.
            ActivityManager tasksManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            tasksManager.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_NO_USER_ACTION);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(getTAG(), "onDestroy");
        super.onDestroy();
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
        return R.layout.activity_3;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FloatingViewService.MessageEvent event) {
        Log.d(getTAG(), "onMessageEvent");
        Intent intent = new Intent(Activity3.this, Activity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
