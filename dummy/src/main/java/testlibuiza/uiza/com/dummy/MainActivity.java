package testlibuiza.uiza.com.dummy;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import testlibuiza.uiza.com.dummy.app.APIServices;
import testlibuiza.uiza.com.dummy.app.LSApplication;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.restapi.uiza.UizaService;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.LToast;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAPI();
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_main;
    }

    private void testAPI() {
        RestClient.init("https://api.stackexchange.com");
        //RestClient.init("https://api.stackexchange.com", "token");
        APIServices service = RestClient.createService(APIServices.class);
        subscribe(service.test(), new ApiSubscriber<Object>() {
            @Override
            public void onSuccess(Object result) {
                LLog.d(TAG, "testAPI onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                LToast.show(activity, LSApplication.getInstance().getGson().toJson(result));
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }
}
