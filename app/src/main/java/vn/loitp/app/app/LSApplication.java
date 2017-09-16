package vn.loitp.app.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.google.gson.Gson;

import loitp.utils.util.Utils;
import vn.loitp.livestar.R;
import vn.loitp.livestar.corev3.api.restclient.RestClient;

/**
 * Created by Phu Tran on 8/3/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
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
        Utils.init(this);
        //TODO truyen token
        RestClient.init(getString(R.string.webService_URL), "token");
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
