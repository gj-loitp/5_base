package vn.loitp.app.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.Random;

import loitp.basemaster.R;

/**
 * Created by www.muathu@gmail.com on 12/30/2017.
 */

public class AppUtil {
    private static int colors[] = {
            R.color.colorPrimary,
            R.color.colorPrimary_50,
            R.color.colorPrimary_100,
            R.color.colorPrimary_200,
            R.color.colorPrimary_300,
            R.color.colorPrimary_400,
            R.color.colorPrimary_600,
            R.color.colorPrimary_700,
            R.color.colorPrimary_800,
            R.color.colorPrimaryDark
    };

    public static int getColor(Context context) {
        Random random = new Random();
        int c = random.nextInt(colors.length);
        return ContextCompat.getColor(context, colors[c]);
    }
}
