package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Vibrator;


/**
 * File created on 11/14/2016.
 *
 * @author loitp
 */
public class LDeviceUtil {
    private static String TAG = LDeviceUtil.class.getSimpleName();

    public static boolean isTablet(Activity activity) {
        return (activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /*
    check device has navigation bar
     */
    /*public boolean isNavigationBarAvailable() {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        //LLog.dialog(TAG, "isNavigationBarAvailable: " + (!(hasBackKey && hasHomeKey)));
        return (!(hasBackKey && hasHomeKey));
    }*/

    /*
      get current android version
      @return int
       */
    public static int getCurrentAndroidVersion(Activity activity) {
        int thisVersion;
        try {
            PackageInfo pi = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            thisVersion = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            thisVersion = 1;
        }
        return thisVersion;
    }

    public static void setClipboard(Context context, String text) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copy", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    public static void vibrate(Context context, int length) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(length);
    }

    public static void vibrate(Context context) {
        vibrate(context, 300);
    }

}
