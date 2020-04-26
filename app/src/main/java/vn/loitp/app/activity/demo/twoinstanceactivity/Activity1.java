package vn.loitp.app.activity.demo.twoinstanceactivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import vn.loitp.app.R;

public class Activity1 extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LLog.d(getTAG(), "suzuki onCreate");
        findViewById(R.id.btGoTo2).setOnClickListener(view -> {
            Intent intent = new Intent(Activity1.this, Activity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        findViewById(R.id.btGoTo3).setOnClickListener(view -> {
            Intent intent = new Intent(Activity1.this, Activity3.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(getTAG(), "onNewIntent");
        if ((intent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) > 0) {
            mIsRestoredToTop = true;
        }
    }

    private boolean mIsRestoredToTop;

    @Override
    public void finish() {
        super.finish();
        if (android.os.Build.VERSION.SDK_INT >= 19 && !isTaskRoot() && mIsRestoredToTop) {
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
        return R.layout.activity_1;
    }
}
