package vn.loitp.app.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import loitp.basemaster.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.loitp.app.activity.home.HomeMenuActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LPref;
import vn.loitp.core.utilities.LUIUtil;

public class SplashActivity extends BaseActivity {
    private boolean isAnimDone = false;
    private boolean isCheckReadyDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LActivityUtil.hideSystemUI(getWindow().getDecorView());

        ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LUIUtil.setImageFromAsset(activity, "bkg.png", ivBkg);

        TextView tvAppName = (TextView) findViewById(R.id.tv_app_name);
        LUIUtil.setTextShadow(tvAppName, Color.WHITE);

        LUIUtil.setDelay(2500, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                isAnimDone = true;
                goToHome();
            }
        });

        checkReady();
    }

    private void goToHome() {
        if (isAnimDone && isCheckReadyDone) {
            Intent intent = new Intent(activity, HomeMenuActivity.class);
            startActivity(intent);
            LActivityUtil.tranIn(activity);
            finish();
        }
    }

    private void showDialogNotReady() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LDialogUtil.showDialog1(activity, getString(R.string.warning), getString(R.string.cannot_connect_to_server), getString(R.string.confirm), new LDialogUtil.Callback1() {
                    @Override
                    public void onClick1() {
                        onBackPressed();
                    }
                });
            }
        });
    }

    private void checkReady() {
        if (LPref.getCheckAppReady(activity)) {
            isCheckReadyDone = true;
            goToHome();
            return;
        }
        //LLog.d(TAG, "checkReady");
        final String LINK_GG_DRIVE_CHECK_READY = "https://drive.google.com/uc?export=download&id=1HXia4WviPcLD3OE7OSl54sYIFoNR18yf";
        Request request = new Request.Builder().url(LINK_GG_DRIVE_CHECK_READY).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //LLog.d(TAG, "onFailure " + e.toString());
                showDialogNotReady();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //LLog.d(TAG, "onResponse isSuccessful " + response.toString());
                    int versionServer = Integer.parseInt(response.body().string());
                    //LLog.d(TAG, "onResponse " + versionServer);
                    if (versionServer == 1) {
                        isCheckReadyDone = true;
                        LPref.setCheckAppReady(activity, true);
                        goToHome();
                    } else {
                        showDialogNotReady();
                    }
                } else {
                    //LLog.d(TAG, "onResponse !isSuccessful: " + response.toString());
                    showDialogNotReady();
                }
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return true;
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
