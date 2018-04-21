package vn.loitp.app.activity.api.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.livestar.corev3.api.model.v3.categoryget.CategoryGet;
import vn.loitp.restapi.livestar.corev3.api.model.v3.getposter.GetPoster;
import vn.loitp.restapi.livestar.corev3.api.service.LSService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.restapi.uiza.UizaServiceTest;
import vn.loitp.restapi.wtt.WTTService;
import vn.loitp.rxandroid.ApiSubscriber;

public class TestAPIActivity extends BaseActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient.init(getString(R.string.webService_URL), "");//TODO truyen token
                testGetPoster();
            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient.init(getString(R.string.webService_URL), "");
                testCategoryGet();
            }
        });
        findViewById(R.id.bt_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient.init(getString(R.string.wtt_URL), "zHiQCup9CzTr1eP5ZQsbPK5sYNYa8kRL-1517457089350");
                testWTT();
            }
        });
        findViewById(R.id.bt_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient.init(getString(R.string.yup_URL), "");
                testYupProduction();
            }
        });
        findViewById(R.id.bt_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient.init(getString(R.string.uiza_URL), "");
                testUizaV2();
            }
        });
        findViewById(R.id.bt_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient.init(getString(R.string.uiza_stag_URL), "zHiQCup9CzTr1eP5ZQsbPK5sYNYa8kRL-1517457089350");
                testUizaStag();
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
    protected int setLayoutResourceId() {
        return R.layout.activity_test_api;
    }


    private void testGetPoster() {
        LLog.d(TAG, "testGetPoster");
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getPoster(3), new ApiSubscriber<GetPoster[]>() {
            @Override
            public void onSuccess(GetPoster[] result) {
                LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                //tv.setText(LSApplication.getInstance().getGson().toJson(result));
                LUIUtil.printBeautyJson(result, tv);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }

            @Override
            public void onFinally(boolean success) {
                LLog.d(TAG, "onFinally success " + success);
            }
        });
    }

    private void testCategoryGet() {
        LLog.d(TAG, "testCategoryGet");
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.categoryGet(), new ApiSubscriber<CategoryGet[]>() {
            @Override
            public void onSuccess(CategoryGet[] result) {
                LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                LUIUtil.printBeautyJson(result, tv);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }

            @Override
            public void onFinally(boolean success) {
                LLog.d(TAG, "onFinally success " + success);
            }
        });
    }

    private void testWTT() {
        LLog.d(TAG, "testWTT");
        WTTService service = RestClient.createService(WTTService.class);
        subscribe(service.getMetadatList(100), new ApiSubscriber<Object>() {
            @Override
            public void onSuccess(Object result) {
                LLog.d(TAG, "testWTT onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                LUIUtil.printBeautyJson(result, tv);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void testYupProduction() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getPoster(10), new ApiSubscriber<GetPoster[]>() {
            @Override
            public void onSuccess(GetPoster[] getPosters) {
                LLog.d(TAG, "setPoster onSuccess " + LSApplication.getInstance().getGson().toJson(getPosters));
                LUIUtil.printBeautyJson(getPosters, tv);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void testUizaV2() {
        LLog.d(TAG, "testUizaV2");
        UizaServiceTest service = RestClient.createService(UizaServiceTest.class);
        String accessKeyId = "BNEU77HJAPWYVIF1DEU5";
        String secretKeyId = "8yro1j369cCj6VR7cD2kzQbzJ2vDiswt7jxhtGjp";
        subscribe(service.auth(accessKeyId, secretKeyId), new ApiSubscriber<Object>() {
            @Override
            public void onSuccess(Object result) {
                LLog.d(TAG, "testUizaV2 onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                LUIUtil.printBeautyJson(result, tv);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void testUizaStag() {
        LLog.d(TAG, "testUizaStag");
        UizaServiceTest service = RestClient.createService(UizaServiceTest.class);
        subscribe(service.getMetadatList(), new ApiSubscriber<Object>() {
            @Override
            public void onSuccess(Object result) {
                LLog.d(TAG, "testUizaStag onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                LUIUtil.printBeautyJson(result, tv);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }
}
