package vn.loitp.app.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import loitp.basemaster.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.data.ActivityData;
import vn.loitp.data.AdmobData;
import vn.loitp.utils.util.Utils;
//TODO admob id
//TODO remove gradle
//TODO ic_launcher
public class LSApplication extends MultiDexApplication {
    private final String TAG = LSApplication.class.getSimpleName();
    private static LSApplication instance;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (gson == null) {
            gson = new Gson();
        }
        Utils.init(this);
        AdmobData.getInstance().setIdAdmobFull(getString(R.string.str_f));
        Fresco.initialize(getApplicationContext());
        ActivityData.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL);
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
