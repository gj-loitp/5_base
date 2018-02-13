package vn.loitp.app.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.util.Random;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.utilities.LImageUtil;

/**
 * Created by www.muathu@gmail.com on 12/30/2017.
 */

public class AppUtil {
    private static int colors[] = {
            R.color.colorPrimary_50,
            R.color.colorPrimary_100,
            R.color.colorPrimary_200,
            R.color.LightBlue,
            R.color.LightCoral,
            R.color.LightCyan,
            R.color.LightGoldenrodYellow,
            R.color.LightGreen,
            R.color.LightGrey,
            R.color.LightPink,
            R.color.LightSalmon,
            R.color.LightSeaGreen,
            R.color.LightSlateGray,
            R.color.LightSteelBlue,
            R.color.LightYellow,
            R.color.LightSkyBlue
    };

    public static int getColor(Context context) {
        Random random = new Random();
        int c = random.nextInt(colors.length);
        return ContextCompat.getColor(context, colors[c]);
    }

    private static String urlImg[] = {
            Constants.URL_IMG_0,
            Constants.URL_IMG_1,
            Constants.URL_IMG_2,
            Constants.URL_IMG_3,
            Constants.URL_IMG_4,
            Constants.URL_IMG_5,
            Constants.URL_IMG_6,
            Constants.URL_IMG_7,
            Constants.URL_IMG_8,
            Constants.URL_IMG_9
    };

    public static String getRandomUrl() {
        Random random = new Random();
        int c = random.nextInt(urlImg.length);
        return urlImg[c];
    }

    public static void loadBackground(Activity activity, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        LImageUtil.load(activity, getRandomUrl(), imageView);
    }
}
