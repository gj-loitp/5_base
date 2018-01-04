package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Context;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.data.ActivityData;

/**
 * Created by www.muathu@gmail.com on 1/3/2018.
 */

public class LActivityUtil {

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
}
