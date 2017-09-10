package vn.puresolutions.livestarv3.utilities.v3;

import android.util.Log;

import vn.puresolutions.livestar.common.Constants;

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