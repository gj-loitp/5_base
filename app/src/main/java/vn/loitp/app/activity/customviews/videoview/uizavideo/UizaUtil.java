package vn.loitp.app.activity.customviews.videoview.uizavideo;

import android.content.Context;
import android.widget.ImageButton;

import vn.loitp.core.utilities.LScreenUtil;

/**
 * Created by LENOVO on 4/11/2018.
 */

public class UizaUtil {
    public static void setUIFullScreenIcon(Context context, ImageButton imageButton) {
        if (LScreenUtil.isFullScreen(context)) {
            imageButton.setImageResource(loitp.core.R.drawable.ic_fullscreen_exit_black_48dp);
        } else {
            imageButton.setImageResource(loitp.core.R.drawable.ic_fullscreen_black_48dp);
        }
    }
}
