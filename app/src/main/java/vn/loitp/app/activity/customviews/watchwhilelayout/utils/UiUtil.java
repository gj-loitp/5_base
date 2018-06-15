package vn.loitp.app.activity.customviews.watchwhilelayout.utils;

import android.content.res.Resources;

import loitp.basemaster.R;

/**
 * Created by thangn on 2/27/17.
 */
public class UiUtil {
    public static int getGridColumnCount(Resources res) {
        return Math.min(getGridColumnContentWidth(res) / res.getDimensionPixelSize(R.dimen.column_min_size), 5);
    }

    private static int getGridColumnContentWidth(Resources res) {
        return res.getDisplayMetrics().widthPixels - 2 * res.getDimensionPixelSize(R.dimen.card_spacing);
    }
}
