package vn.loitp.app.activity.customviews.videoview.uizavideo;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageButton;

import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;

/**
 * Created by LENOVO on 4/11/2018.
 */

public class UizaUtil {
    private final static String TAG = UizaUtil.class.getSimpleName();

    public static void setUIFullScreenIcon(Context context, ImageButton imageButton) {
        if (LScreenUtil.isFullScreen(context)) {
            imageButton.setImageResource(loitp.core.R.drawable.ic_fullscreen_black_48dp);
        } else {
            imageButton.setImageResource(loitp.core.R.drawable.ic_fullscreen_exit_black_48dp);
        }
    }

    public static void resizeLayout(ViewGroup viewGroup) {
        int widthScreen = 0;
        int heightScreen = 0;
        boolean isFullScreen = LScreenUtil.isFullScreen(viewGroup.getContext());
        if (isFullScreen) {
            widthScreen = LScreenUtil.getScreenHeightIncludeNavigationBar(viewGroup.getContext());
            heightScreen = LScreenUtil.getScreenHeight();

        } else {
            widthScreen = LScreenUtil.getScreenWidth();
            heightScreen = widthScreen * 9 / 16;
        }
        LLog.d(TAG, "resizeLayout isFullScreen " + isFullScreen + " -> " + widthScreen + "x" + heightScreen);
        viewGroup.getLayoutParams().width = widthScreen;
        viewGroup.getLayoutParams().height = heightScreen;
        viewGroup.requestLayout();
    }
}
