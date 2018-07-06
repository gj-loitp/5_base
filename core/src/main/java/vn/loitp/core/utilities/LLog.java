package vn.loitp.core.utilities;

import android.util.Log;

import vn.loitp.core.common.Constants;
import vn.loitp.views.ldebugview.ComunicateDebug;
import vn.loitp.views.ldebugview.LDebug;

public class LLog {
    public static void d(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.d(tag, msg);
            LDebug.log(tag + " / " + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.e(tag, msg);
            LDebug.log(ComunicateDebug.MsgFromActivity.TYPE_E, tag + " / " + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.e(tag, msg);
            LDebug.log(ComunicateDebug.MsgFromActivity.TYPE_I, tag + " / " + msg);
        }
    }

    /*public static void d(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.d(tag, msg);
            LDebug.log("Sample d: " + System.currentTimeMillis());
        }
    }

    public static void e(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.e(tag, msg);
            LDebug.log(ComunicateDebug.MsgFromActivity.TYPE_E, tag + " / " + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (Constants.IS_DEBUG) {
            Log.e(tag, msg);
            LDebug.log(ComunicateDebug.MsgFromActivity.TYPE_I, tag + " / " + msg);
        }
    }*/
}