package vn.loitp.views.textview.selectabletextview;

/**
 * Created by www.muathu@gmail.com on 12/25/2017.
 */

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by jd on 2017. 8. 13..
 */

public class CommonUtil {

    public static int dpTpPx(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

}