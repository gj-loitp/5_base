package vn.puresolutions.livestarv3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.categoryget.CategoryGet;
import vn.puresolutions.livestar.corev3.api.model.v3.getposter.GetPoster;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

public class TestAPIActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRegister();
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testGetPoster();
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testFollowIdol();
            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testCategoryGet();
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
        return R.layout.activity_test_api;
    }

    private void testRegister() {
        LSService service = RestClient.createService(LSService.class);

        String phone = "12345678910";
        String password = "password";
        String name = "name";

        subscribe(service.register(phone, password, name), new ApiSubscriber<Void>() {
            @Override
            public void onSuccess(Void result) {
                LLog.d(TAG, "onSuccess ");
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LLog.d(TAG, "onFail " + e.toString());
            }

            @Override
            public void onFinally(boolean success) {
                LLog.d(TAG, "onFinally success " + success);
            }
        });
    }

    private void testGetPoster() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getPoster(3), new ApiSubscriber<GetPoster[]>() {
            @Override
            public void onSuccess(GetPoster[] result) {
                LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LLog.d(TAG, "onFail " + e.toString());
            }

            @Override
            public void onFinally(boolean success) {
                LLog.d(TAG, "onFinally success " + success);
            }
        });
    }

    private void testFollowIdol() {
        LSService service = RestClient.createService(LSService.class);

        subscribe(service.followIdol("123456"), new ApiSubscriber<Object>() {
            @Override
            public void onSuccess(Object result) {
                LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LLog.d(TAG, "onFail " + e.toString());
            }

            @Override
            public void onFinally(boolean success) {
                LLog.d(TAG, "onFinally success " + success);
            }
        });
    }
    private void testCategoryGet() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.categoryGet(), new ApiSubscriber<CategoryGet[]>() {
            @Override
            public void onSuccess(CategoryGet[] result) {
                LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LLog.d(TAG, "onFail " + e.toString());
            }

            @Override
            public void onFinally(boolean success) {
                LLog.d(TAG, "onFinally success " + success);
            }
        });
    }
}
