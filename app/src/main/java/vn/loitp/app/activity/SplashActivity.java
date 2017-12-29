package vn.loitp.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import loitp.basemaster.BuildConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.loitp.core.base.BaseActivity;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;

public class SplashActivity extends BaseActivity {
    private boolean isAnimDone = false;
    private boolean isCheckReadyDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LUIUtil.setDelay(2500, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                isAnimDone = true;
                goToHome();
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("Version " + BuildConfig.VERSION_NAME);

        //TODO
        boolean isNeedCheckReady = true;
        if (isNeedCheckReady) {
            checkReady();
        } else {
            isCheckReadyDone = true;
            goToHome();
        }
    }

    private void goToHome() {
        if (isAnimDone && isCheckReadyDone) {
            Intent intent = new Intent(activity, MenuActivity.class);
            startActivity(intent);
            LUIUtil.transActivityFadeIn(activity);
            finish();
        }
    }

    private void checkReady() {
        final String LINK_GG_DRIVE_CHECK_READY = "https://drive.google.com/uc?export=download&id=0B0-bfr9v36LUTk9XdXZzbDc1aG8";
        Request request = new Request.Builder().url(LINK_GG_DRIVE_CHECK_READY).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LLog.d(TAG, "onFailure " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //LLog.d(TAG, "onResponse isSuccessful " + response.toString());
                    int versionServer = Integer.parseInt(response.body().string());
                    LLog.d(TAG, "onResponse " + versionServer);
                } else {
                    LLog.d(TAG, "onResponse !isSuccessful: " + response.toString());
                }
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
        return R.layout.activity_splash;
    }
}
