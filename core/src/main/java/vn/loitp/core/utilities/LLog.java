package vn.loitp.core.utilities;

import android.util.Log;

import vn.loitp.core.common.Constants;

public class LLog {
    public static void d(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.e(tag, msg);
        }
    }
}