package com.loitpcore.function.pump.download.utils;

import timber.log.Timber;

public class LogUtil {
    private static final String TAG = "Pump";
    public static boolean mEnableLog = true;

    public static void e(String content) {
        if (mEnableLog) {
            Timber.tag(TAG).e("Pump %s", content);
        }
    }

    public static void i(String content) {
        if (mEnableLog) {
            Timber.tag(TAG).i("Pump %s", content);
        }
    }

    public static void d(String content) {
        if (mEnableLog) {
            Timber.tag(TAG).d("Pump %s", content);
        }
    }

    public static void w(String content) {
        if (mEnableLog) {
            Timber.tag(TAG).w("Pump %s", content);
        }
    }
}
