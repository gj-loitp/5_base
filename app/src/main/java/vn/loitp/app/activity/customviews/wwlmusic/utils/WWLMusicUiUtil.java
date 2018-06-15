package vn.loitp.app.activity.customviews.wwlmusic.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import loitp.basemaster.R;

/**
 * Created by thangn on 2/27/17.
 */
public class WWLMusicUiUtil {
    public static int getGridColumnCount(Resources res) {
        return Math.min(getGridColumnContentWidth(res) / res.getDimensionPixelSize(R.dimen.column_min_size), 5);
    }

    private static int getGridColumnContentWidth(Resources res) {
        return res.getDisplayMetrics().widthPixels - 2 * res.getDimensionPixelSize(R.dimen.card_spacing);
    }

    public static void hideSystemUI(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!ViewConfiguration.get(activity).hasPermanentMenuKey()) {
            int newUiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            if (Build.VERSION.SDK_INT >= 19) {
                newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }
    }

    public static void showSystemUI(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!ViewConfiguration.get(activity).hasPermanentMenuKey()) {
            int newUiOptions = View.VISIBLE;
            activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }
    }

    public static void updateStatusBarAlpha(Activity activity, float alpha) {
        if (Build.VERSION.SDK_INT >= 21) {
            int color = activity.getResources().getColor(R.color.colorPrimaryDark);
            int color2 = Color.BLACK;
            int color3 = WWLMusicViewHelper.evaluateColorAlpha(Math.max(0.0f, Math.min(1.0f, alpha)), color, color2);
            activity.getWindow().setStatusBarColor(color3);
        }
    }
}
