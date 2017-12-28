package vn.loitp.views.animation.flyschool;

import android.content.res.Resources;
import android.os.Build;
import android.widget.ImageView;

import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LStoreUtil;

/**
 * Utility class with simple utility functions
 * Created by avin on 09/01/17.
 */

public class Utils {
    /**
     * @param dp : Dimension in dp
     *           Calculates and returns the dimension value in pixels from dp
     * */
    public static int dpToPx(int dp) {
        return (int) ((dp * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * Checks and tell us whether the android phone is on version < LOLLIPOP or not
     * */
    public static boolean isLowerThanLollipop() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    public static void setHeart(ImageView imageView) {
        int size = LDeviceUtil.getRandomNumber(150) + 80;
        imageView.getLayoutParams().height = size;
        imageView.getLayoutParams().width = size;
        imageView.requestLayout();
        int color = LStoreUtil.getRandomColor();
        imageView.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
    }
}
