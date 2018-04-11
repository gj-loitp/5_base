package vn.loitp.app.activity.customviews.videoview.uizavideo;

import android.content.Context;
import android.view.ViewGroup;
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

    public static void resizeLayout(ViewGroup viewGroup) {
        if (LScreenUtil.isFullScreen(viewGroup.getContext())) {
            int widthScreen = LScreenUtil.getScreenWidth();
            int heightScreen = LScreenUtil.getScreenHeight();
            viewGroup.getLayoutParams().width = widthScreen;
            viewGroup.getLayoutParams().height = heightScreen;
            viewGroup.requestLayout();
        } else {
            int widthScreen = LScreenUtil.getScreenWidth();
            viewGroup.getLayoutParams().width = widthScreen;
            viewGroup.getLayoutParams().height = widthScreen * 9 / 16;
            viewGroup.requestLayout();
        }
    }
}
