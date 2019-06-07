package vn.loitp.core.utilities;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

public final class LDisplayUtils {
    private LDisplayUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static int dip2px(@NonNull final Context context, final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(@NonNull final Context context, final float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(@NonNull final Context context, final float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(@NonNull final Context context, final float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getDialogW(@NonNull final Activity activity) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.widthPixels - 100;
    }

    public static int getScreenW(@NonNull final Activity activity) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenH(@NonNull final Activity activity) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static void toggleKeyboard(@NonNull final Context context) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}