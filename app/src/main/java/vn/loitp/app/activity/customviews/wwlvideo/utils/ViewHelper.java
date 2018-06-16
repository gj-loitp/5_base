package vn.loitp.app.activity.customviews.wwlvideo.utils;

/**
 * Created by thangn on 2/26/17.
 */
public class ViewHelper {
    public static int alpha255(float f) {
        if (f <= 0.0f) {
            return 0;
        }
        if (f >= 1.0f) {
            return 255;
        }
        return (int) (255.0f * f);
    }
}
