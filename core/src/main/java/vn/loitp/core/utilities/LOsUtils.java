package vn.loitp.core.utilities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;

public class LOsUtils {
    private static boolean sIsAtLeastM;

    static {
        final int v = getApiVersion();
        sIsAtLeastM = v >= 23;//android.os.Build.VERSION_CODES.M
    }

    /**
     * @return The Android API version of the OS that we're currently running on.
     */
    public static int getApiVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * @return True if the version of Android that we're running on is at least M
     * (API level 23).
     */
    public static boolean isAtLeastM() {
        return sIsAtLeastM;
    }

    public static String[] sRequiredLocationPermission = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    public static String[] sRequiredStoragePermission = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static String[] sRequiredCameraPermission = new String[]{
            Manifest.permission.CAMERA,
    };

    /**
     * Check if the app has the specified permission. If it does not, the app needs to use
     * {@link android.app.Activity #requestPermission}. Note that if it
     * returns true, it cannot return false in the same process as the OS kills the process when
     * any permission is revoked.
     *
     * @param permission A permission from {@link Manifest.permission}
     */
    @TargetApi(23)
    public static boolean hasPermission(Context context, final String permission) {

        if (isAtLeastM() && context != null) {
            int permissionCheck = context.checkSelfPermission(permission);
            return permissionCheck == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    /**
     * Does the app have all the specified permissions
     */
    public static boolean hasPermissions(Context context, final String[] permissions) {
        for (final String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }


    public static boolean hasLocationPermission(Context context) {
        return hasPermissions(context, sRequiredLocationPermission);
    }

    public static boolean hasStoragePermission(Context context) {
        return hasPermissions(context, sRequiredStoragePermission);
    }

    public static boolean hasCameraPermission(Context context) {
        return hasPermissions(context, sRequiredCameraPermission);
    }
}
