package vn.puresolutions.livestar.helper;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import vn.puresolutions.livestarv3.app.LSApplication;

/**
 * Created by khanh on 5/11/16.
 */
public class TrackingHelper {

    public static void trackScreenName(Class clazz) {
        trackScreenName(clazz.getSimpleName());
    }

    public static void trackScreenName(String screenName) {
        LSApplication application = LSApplication.getInstance();
        Tracker tracker = application.getDefaultTracker();
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
