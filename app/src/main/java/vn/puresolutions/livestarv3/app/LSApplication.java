package vn.puresolutions.livestarv3.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestarv3.utilities.v3.LPref;

/**
 * Created by Phu Tran on 8/3/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class LSApplication extends MultiDexApplication {
    private static LSApplication instance;
    private Tracker mTracker;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;
        if (gson == null) {
            gson = new Gson();
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proxima-nova-regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        Fresco.initialize(getApplicationContext());
        RestClient.init(getString(R.string.webService_URL), LPref.getToken(this));

        FacebookSdk.sdkInitialize(this);
    }

    public Gson getGson() {
        return gson;
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }

    public static LSApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
