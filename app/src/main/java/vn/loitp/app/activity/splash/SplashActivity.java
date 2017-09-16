package vn.loitp.app.activity.splash;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.livestar.R;
import vn.loitp.livestar.corev3.api.model.v3.listgift.ListGift;
import vn.loitp.livestar.corev3.api.model.v3.login.UserProfile;
import vn.loitp.livestar.corev3.api.restclient.RestClient;
import vn.loitp.livestar.corev3.api.service.LSService;
import vn.loitp.app.rxandroid.ApiSubscriber;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.v3.LConnectivityUtil;
import vn.loitp.app.utilities.v3.LLog;
import vn.loitp.app.utilities.v3.LUIUtil;

//TODO  thông báo về Update apps hoặc Rating cho apps.
public class SplashActivity extends BaseActivity {
    private boolean isGotListGiftOK;
    private boolean isUpdateUserProfileOK;
    private boolean isFinisedSplash;
    private final int DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        //LAnimationUtil.playDuration(ivBkg, Techniques.FadeIn, DURATION);
    }

    private void getUser() {
        boolean isLoggin = true;
        if (isLoggin) {
            LSService service = RestClient.createService(LSService.class);
            subscribe(service.getProfile(), new ApiSubscriber<UserProfile>() {
                @Override
                public void onSuccess(UserProfile userProfile) {
                    if (userProfile != null) {
                        isUpdateUserProfileOK = true;
                        goToHomeMain();
                    }
                }

                @Override
                public void onFail(Throwable e) {
                    handleException(e);
                }
            });
        } else {
            isUpdateUserProfileOK = true;
            goToHomeMain();
        }
    }

    private void getListGift() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.listGift(), new ApiSubscriber<ListGift>() {
            @Override
            public void onSuccess(ListGift listGift) {
                LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(listGift));
                if (listGift != null) {
                    isGotListGiftOK = true;
                    goToHomeMain();
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void goToHomeMain() {
        //LLog.d(TAG, "goToHomeMain isGotListGiftOK " + isGotListGiftOK + ", isFinisedSplash " + isFinisedSplash + ", isUpdateUserProfileOK " + isUpdateUserProfileOK);
        if (isGotListGiftOK && isUpdateUserProfileOK && isFinisedSplash) {

        } else {
            LLog.d(TAG, "-> Still wait");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LConnectivityUtil.isConnected(activity)) {
            getListGift();
            getUser();
            LUIUtil.setDelay(DURATION, new LUIUtil.DelayCallback() {
                @Override
                public void doAfter(int mls) {
                    isFinisedSplash = true;
                    goToHomeMain();
                }
            });
        } else {

        }
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
        return R.layout.activity_splash_v3;
    }
}
