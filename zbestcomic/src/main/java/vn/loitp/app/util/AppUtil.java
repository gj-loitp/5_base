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
}
