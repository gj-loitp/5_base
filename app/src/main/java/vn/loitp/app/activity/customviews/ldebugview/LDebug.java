package vn.loitp.app.activity.customviews.ldebugview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ServiceUtils;
import vn.loitp.views.LToast;

import static vn.loitp.views.uizavideo.view.rl.UizaIMAVideo.CODE_DRAW_OVER_OTHER_APP_PERMISSION;

/**
 * Created by LENOVO on 7/5/2018.
 */

public class LDebug {
    public final static int CODE = 1993;

    public static void init(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, CODE);
        } else {
            ServiceUtils.startService(LDebugViewService.class);
        }
    }

    public static void checkPermission(Activity activity, int requestCode, int resultCode) {
        if (requestCode == CODE) {
            init(activity);
        }
    }

    public static void stop() {
        ServiceUtils.stopService(LDebugViewService.class.getName());
    }

    public static void log(String log) {

    }
}
