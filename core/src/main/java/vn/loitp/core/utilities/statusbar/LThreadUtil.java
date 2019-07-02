package vn.loitp.core.utilities.statusbar;

import android.os.Looper;

public class LThreadUtil {
    public static boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
