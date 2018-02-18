package vn.loitp.app.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import loitp.basemaster.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.data.ActivityData;
import vn.loitp.data.AdmobData;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.utils.util.Utils;

//TODO change url flickr toolbar
//TODO debug false
//TODO ic_launcher
//TODO bkg splash
//TODO check splash screen, url
//TODO remove db asset danhngon
//TODO admob id
//TODO da up len store
//done

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
        //RestClient.init(getString(R.string.flickr_URL));
        //Utils.init(this);
        AdmobData.getInstance().setIdAdmobFull(getString(R.string.str_f));
        //Fresco.initialize(getApplicationContext());
        ActivityData.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT);

        //config realm
        /*RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);*/
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
