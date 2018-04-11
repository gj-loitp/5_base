package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.data.ActivityData;

/**
 * Created by www.muathu@gmail.com on 1/3/2018.
 */

public class LActivityUtil {

    // This snippet hides the system bars.
    public static void hideSystemUI(View mDecorView) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    public static void showSystemUI(View mDecorView) {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static void tranIn(Context context) {
        int typeActivityTransition = ActivityData.getInstance().getType();
        if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM) {
            LActivityUtil.transActivityNoAniamtion((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT) {
            //do nothing
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT) {
            LActivityUtil.slideLeft((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT) {
            LActivityUtil.slideRight((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN) {
            LActivityUtil.slideDown((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP) {
            LActivityUtil.slideUp((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_FADE) {
            LActivityUtil.fade((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_ZOOM) {
            LActivityUtil.zoom((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL) {
            LActivityUtil.windmill((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL) {
            LActivityUtil.diagonal((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SPIN) {
            LActivityUtil.spin((Activity) context);
        }
    }

    public static void tranOut(Context context) {
        int typeActivityTransition = ActivityData.getInstance().getType();
        if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM) {
            LActivityUtil.transActivityNoAniamtion((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT) {
            //do nothing
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT) {
            LActivityUtil.slideRight((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT) {
            LActivityUtil.slideLeft((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN) {
            LActivityUtil.slideUp((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP) {
            LActivityUtil.slideDown((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_FADE) {
            LActivityUtil.fade((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_ZOOM) {
            LActivityUtil.zoom((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL) {
            LActivityUtil.windmill((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL) {
            LActivityUtil.diagonal((Activity) context);
        } else if (typeActivityTransition == Constants.TYPE_ACTIVITY_TRANSITION_SPIN) {
            LActivityUtil.spin((Activity) context);
        }
    }

    public static void transActivityNoAniamtion(Context context) {
        ((Activity) context).overridePendingTransition(0, 0);
    }

    public static void slideLeft(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
    }

    public static void slideRight(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void slideDown(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_down_enter, R.anim.slide_down_exit);
    }

    public static void slideUp(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_up_enter, R.anim.slide_up_exit);
    }

    public static void zoom(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    public static void fade(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.fade_enter, R.anim.fade_exit);
    }

    public static void windmill(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.windmill_enter, R.anim.windmill_exit);
    }

    public static void spin(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.spin_enter, R.anim.spin_exit);
    }

    public static void diagonal(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.diagonal_right_enter, R.anim.diagonal_right_exit);
    }

    public static void toggleFullScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    public static void toggleScreenOritation(Activity activity) {
        int s = getScreenOrientation(activity);
        if (s == Configuration.ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        } else if (s == Configuration.ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }

    public static void changeScreenPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void changeScreenLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static int getScreenOrientation(Activity activity) {
        return activity.getResources().getConfiguration().orientation;
    }
}
