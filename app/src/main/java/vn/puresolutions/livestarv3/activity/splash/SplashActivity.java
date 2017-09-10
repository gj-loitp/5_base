package vn.puresolutions.livestarv3.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.listgift.ListGift;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.homescreen.HomeMainActivity;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LConnectivityUtil;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

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
        if (LPref.isUserLoggedIn(activity)) {
            LSService service = RestClient.createService(LSService.class);
            subscribe(service.getProfile(), new ApiSubscriber<UserProfile>() {
                @Override
                public void onSuccess(UserProfile userProfile) {
                    if (userProfile != null) {
                        LPref.setUser(activity, userProfile);
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
                    LPref.setListGiftr(activity, listGift);
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
            Intent intent = new Intent(activity, HomeMainActivity.class);
            startActivity(intent);
            LUIUtil.transActivityFadeIn(activity);
            finish();
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
            LDialogUtils.showDlg1Option(activity, R.drawable.delete_button, getString(R.string.alert), getString(R.string.no_connection_msg), getString(R.string.open_connection), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                    startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                    LUIUtil.transActivityFadeIn(activity);
                }
            });
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
