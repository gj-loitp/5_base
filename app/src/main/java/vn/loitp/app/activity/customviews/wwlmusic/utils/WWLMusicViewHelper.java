package vn.loitp.app.activity.customviews.wwlmusic.utils;

/**
 * Created by thangn on 2/26/17.
 */
public class WWLMusicViewHelper {
    public static int alpha255(float f) {
        if (f <= 0.0f) {
            return 0;
        }
        if (f >= 1.0f) {
            return 255;
        }
        return (int) (255.0f * f);
    }

    public static int evaluateColorAlpha(float f, int color1, int color2) {
        int c14 = color1 >>> 24;
        int c13 = (color1 >> 16) & 255;
        int c12 = (color1 >> 8) & 255;
        int c11 = color1 & 255;
        int c24 = color2 >>> 24;
        int c23 = (color2 >> 16) & 255;
        int c22 = (color2 >> 8) & 255;
        int c21 = color2 & 255;
        return (c11 + ((int) (((float) (c21 - c11)) * f))) | ((((c14 + ((int) (((float) (c24 - c14)) * f))) << 24) | ((c13 + ((int) (((float) (c23 - c13)) * f))) << 16)) | ((((int) (((float) (c22 - c12)) * f)) + c12) << 8));
    }
}
