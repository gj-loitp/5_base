package vn.loitp.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.loitp.app.app.LSApplication;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.rxandroid.ApiSubscriber;
import vn.loitp.app.utilities.LLog;
import vn.loitp.livestar.R;
import vn.loitp.livestar.corev3.api.model.v3.categoryget.CategoryGet;
import vn.loitp.livestar.corev3.api.model.v3.getposter.GetPoster;
import vn.loitp.livestar.corev3.api.restclient.RestClient;
import vn.loitp.livestar.corev3.api.service.LSService;

public class TestAPIActivity extends BaseActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testGetPoster();
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


    private void testGetPoster() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getPoster(3), new ApiSubscriber<GetPoster[]>() {
            @Override
            public void onSuccess(GetPoster[] result) {
                LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                tv.setText(LSApplication.getInstance().getGson().toJson(result));
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
                tv.setText(LSApplication.getInstance().getGson().toJson(result));
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
