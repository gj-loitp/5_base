package testlibuiza.uiza.com.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.google.gson.Gson;

import vn.loitp.core.common.Constants;
import vn.loitp.data.ActivityData;
import vn.loitp.utils.util.Utils;

public class LSApplication extends MultiDexApplication {
    private static LSApplication instance;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (gson == null) {
            gson = new Gson();
        }
        Constants.setIsDebug(true);
        Utils.init(this);
        //config activity transition default
        ActivityData.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP);
    }

    public Gson getGson() {
        return gson;
    }

    public static LSApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
