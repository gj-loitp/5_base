package vn.loitp.views.uizavideo.view.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import loitp.core.R;
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

    public static void setUIFullScreenIcon(Context context, ImageView imageView) {
        if (LScreenUtil.isFullScreen(context)) {
            imageView.setImageResource(loitp.core.R.drawable.ic_fullscreen_black_48dp);
        } else {
            imageView.setImageResource(loitp.core.R.drawable.ic_fullscreen_exit_black_48dp);
        }
    }

    public static void resizeLayout(ViewGroup viewGroup, RelativeLayout llMid) {
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

        //edit size of seekbar volume and brightness
        if (llMid != null) {
            if (isFullScreen) {
                llMid.getLayoutParams().height = (int) (heightScreen / 1.75);

            } else {
                llMid.getLayoutParams().height = (int) (heightScreen / 1.95);
            }
            llMid.requestLayout();
        }

        //edit size of imageview thumnail
        FrameLayout flImgThumnailPreviewSeekbar = viewGroup.findViewById(R.id.previewFrameLayout);
        LLog.d(TAG, flImgThumnailPreviewSeekbar == null ? "resizeLayout imgThumnailPreviewSeekbar null" : "resizeLayout imgThumnailPreviewSeekbar !null");
        if (flImgThumnailPreviewSeekbar != null) {
            if (isFullScreen) {
                flImgThumnailPreviewSeekbar.getLayoutParams().width = widthScreen / 4;
                flImgThumnailPreviewSeekbar.getLayoutParams().height = widthScreen / 4 * 9 / 16;
            } else {
                flImgThumnailPreviewSeekbar.getLayoutParams().width = widthScreen / 5;
                flImgThumnailPreviewSeekbar.getLayoutParams().height = widthScreen / 5 * 9 / 16;
            }
            LLog.d(TAG, "resizeLayout: " + flImgThumnailPreviewSeekbar.getWidth() + " x " + flImgThumnailPreviewSeekbar.getHeight());
            flImgThumnailPreviewSeekbar.requestLayout();
        }
    }

    //return button video in debug layout
    public static View getBtVideo(LinearLayout debugRootView) {
        if (debugRootView == null) {
            return null;
        }
        for (int i = 0; i < debugRootView.getChildCount(); i++) {
            View childView = debugRootView.getChildAt(i);
            if (childView instanceof Button) {
                if (((Button) childView).getText().toString().equalsIgnoreCase(debugRootView.getContext().getString(R.string.video))) {
                    return childView;
                }
            }
        }
        return null;
    }

    //return button audio in debug layout
    public static View getBtAudio(LinearLayout debugRootView) {
        if (debugRootView == null) {
            return null;
        }
        for (int i = 0; i < debugRootView.getChildCount(); i++) {
            View childView = debugRootView.getChildAt(i);
            if (childView instanceof Button) {
                if (((Button) childView).getText().toString().equalsIgnoreCase(debugRootView.getContext().getString(R.string.audio))) {
                    return childView;
                }
            }
        }
        return null;
    }

    //return button text in debug layout
    public static View getBtText(LinearLayout debugRootView) {
        if (debugRootView == null) {
            return null;
        }
        for (int i = 0; i < debugRootView.getChildCount(); i++) {
            View childView = debugRootView.getChildAt(i);
            if (childView instanceof Button) {
                if (((Button) childView).getText().toString().equalsIgnoreCase(debugRootView.getContext().getString(R.string.text))) {
                    return childView;
                }
            }
        }
        return null;
    }
}
