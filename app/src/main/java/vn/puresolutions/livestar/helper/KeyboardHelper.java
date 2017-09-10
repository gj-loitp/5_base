package vn.puresolutions.livestar.helper;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by khanh on 8/21/16.
 */
public class KeyboardHelper {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity (Activity activity) {
        new KeyboardHelper(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;

    private KeyboardHelper(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
                //frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.KeyboardShow, heightDifference);
                Log.i("Keyboard", "keyboard show");
            } else {
                // keyboard probably just became hidden
                //frameLayoutParams.height = usableHeightSansKeyboard;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.KeyboardHide);
                Log.i("Keyboard", "keyboard hide");
            }
            //mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return r.bottom;//(r.bottom - r.top);
    }

}
