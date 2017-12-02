package vn.loitp.app.utilities;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by www.muathu@gmail.com on 10/7/2017.
 */

public class LImageUtil {
    public static void load(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
    }

    public static void load(Activity activity, String url, ImageView imageView, int sizeW, int sizeH) {
        Glide.with(activity).load(url).override(sizeW, sizeH).into(imageView);
    }
}
