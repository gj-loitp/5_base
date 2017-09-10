package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import java.util.Date;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.exception.NoConnectionException;
import vn.puresolutions.livestar.core.api.model.AppUpdate;
import vn.puresolutions.livestar.core.api.model.Token;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.model.param.CheckUpdateParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.AccountService;
import vn.puresolutions.livestar.core.api.service.AppConfigService;
import vn.puresolutions.livestar.custom.dialog.AlertDialog;
import vn.puresolutions.livestar.custom.dialog.ConfirmDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.base.BaseActivity;

import static vn.puresolutions.livestar.notification.handler.NotificationHandler.BUNDLE_KEY_IS_FROM_NOTIFICATION;
import static vn.puresolutions.livestar.notification.handler.NotificationHandler.BUNDLE_KEY_ROOM_ID;

/**
 * Created by khanh on 7/31/16.
 */
public class SplashActivity extends BaseActivity {
    private static final int SPLASH_SCREEN_DELAY = 2000;
    private long startTime;
    private AppUpdate version;
    private boolean isShowUpdateWhenResume;
    private String currentVesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            String url = getIntent().getDataString();
            if (url != null) {
                int roomId = Integer.parseInt(Uri.parse(url).getLastPathSegment());
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(BUNDLE_KEY_IS_FROM_NOTIFICATION, true);
                intent.putExtra(BUNDLE_KEY_ROOM_ID, roomId);
                startActivity(intent);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        startTime = System.currentTimeMillis();
        checkAppUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isShowUpdateWhenResume && version != null) {
            showUpdateDialog();
        }
    }

    private void checkAppUpdate() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            AppConfigService service = RestClient.createService(AppConfigService.class);
            subscribe(service.checkUpdate(new CheckUpdateParam(packageInfo.versionName)), new ApiSubscriber<AppUpdate>() {
                @Override
                public void onSuccess(AppUpdate appUpdate) {
                    /*AppUpdate appUpdate1 = new AppUpdate();
                    appUpdate = new Gson().fromJson("{\"min_version\":\"2.0.3\",\"max_version\":\"2.0.3\",\"force_update\":false}",AppUpdate.class);
                     version = appUpdate;*/
                    version = appUpdate;
                    currentVesion = packageInfo.versionName;
                    showUpdateDialog();
                }

                @Override
                public void onFail(Throwable e) {
                    //handleException(e);
                    startApp();
                }
            });
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void showUpdateDialog() {
        if (!version.getLatestVersion().equals(currentVesion)) {
            // optional
            if (version.isForceUpdate()) {
                // forced
                AlertDialog alertDialog = new AlertDialog(SplashActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.hideCloseButton();
                alertDialog.setMessage(R.string.force_update_message);
                alertDialog.setButton(R.string.ok, dialog -> {
                    openAppStore();
                    isShowUpdateWhenResume = true;
                });
                alertDialog.show();
            } else {
                ConfirmDialog confirmDialog = new ConfirmDialog(SplashActivity.this);
                confirmDialog.setMessage(R.string.update_message);
                confirmDialog.setCancelable(false);
                confirmDialog.setCanceledOnTouchOutside(false);
                confirmDialog.hideCloseButton();
                confirmDialog.setPositiveButton(R.string.ok, dialog -> {
                    openAppStore();
                    isShowUpdateWhenResume = true;
                });
                confirmDialog.setNegativeButton(R.string.cancel, dialog -> startApp());
                confirmDialog.show();
            }
        } else {
            // app version is equal
            startApp();
        }
        /*if (version.isForceUpdate()) {
            // forced
            AlertDialog alertDialog = new AlertDialog(SplashActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.hideCloseButton();
            alertDialog.setMessage(R.string.force_update_message);
            alertDialog.setButton(R.string.ok, dialog -> {
                openAppStore();
                isShowUpdateWhenResume = true;
            });
            alertDialog.show();
        } else {
            if (!version.getLatestVersion().equals(currentVesion)) {
                // optional
                ConfirmDialog confirmDialog = new ConfirmDialog(SplashActivity.this);
                confirmDialog.setMessage(R.string.update_message);
                confirmDialog.setCancelable(false);
                confirmDialog.setCanceledOnTouchOutside(false);
                confirmDialog.hideCloseButton();
                confirmDialog.setPositiveButton(R.string.ok, dialog -> {
                    openAppStore();
                    isShowUpdateWhenResume = true;
                });
                confirmDialog.setNegativeButton(R.string.cancel, dialog -> startApp());
                confirmDialog.show();
            } else {
                // app version is equal
                startApp();
            }
        }*/
    }

    private void startApp() {
        if (!TextUtils.isEmpty(UserInfo.getToken())) {
            loadProfile(this::detectUserMBF);
        } else {
            detectUserMBF();
        }
    }

    private void openAppStore() {
        try {
            // Play Store
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (android.content.ActivityNotFoundException exception) {
            // Web browser
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void loadProfile(Runnable failRunnable) {
        AccountService service = RestClient.createService(AccountService.class);
        subscribe(service.getProfile(), new ApiSubscriber<User>() {
            @Override
            public void onSuccess(User user) {
                UserInfo.setUserLoggedIn(user);
                goToNextScreen(MainActivity.class);
            }

            @Override
            public void onFail(Throwable e) {
                failRunnable.run();
            }
        });
    }

    private void detectUserMBF() {
        AccountService service = RestClient.createService(AccountService.class);
        subscribe(service.loginMBF(), new ApiSubscriber<Token>() {
            @Override
            public void onSuccess(Token token) {
                UserInfo.setPhoneNumber(token.getPhone());
                String apiToken = token.getToken();
                if (!TextUtils.isEmpty(apiToken)) {
                    UserInfo.setToken(apiToken);
                    loadProfile(SplashActivity.this::goToNextScreen);
                } else {
                    goToNextScreen();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (!(e instanceof NoConnectionException)) {
                    UserInfo.logout();
                }
                goToNextScreen();
            }
        });
    }

    private void goToNextScreen() {
        goToNextScreen(OnboardingActivity.class);
    }

    private void goToNextScreen(final Class<? extends Activity> activityClass) {
        long duration = new Date().getTime() - startTime;
        long delayTime = Math.max(1, SPLASH_SCREEN_DELAY - duration);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, activityClass);
            startActivity(intent);
            finish();
        }, delayTime);
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
