package vn.loitp.app.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;

import loitp.basemaster.BuildConfig;
import loitp.basemaster.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LConnectivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPref;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;

public class SplashActivity extends BaseFontActivity {
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

        TextView tvPolicy = (TextView) findViewById(R.id.tv_policy);
        LUIUtil.setTextShadow(tvPolicy);
        tvPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LSocialUtil.openUrlInBrowser(activity, Constants.URL_POLICY);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isShowDialogCheck) {
            checkPermission();
        }
    }

    private boolean isShowDialogCheck;

    private void checkPermission() {
        isShowDialogCheck = true;
        boolean isCanWriteSystem = LScreenUtil.checkSystemWritePermission(activity);
        if (isCanWriteSystem) {
            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {
                                LLog.d(TAG, "onPermissionsChecked do you work now");
                                boolean isNeedCheckReady = false;
                                if (isNeedCheckReady) {
                                    checkReady();
                                } else {
                                    isCheckReadyDone = true;
                                    goToHome();
                                }
                            } else {
                                LLog.d(TAG, "!areAllPermissionsGranted");
                                showShouldAcceptPermission();
                            }

                            // check for permanent denial of any permission
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                LLog.d(TAG, "onPermissionsChecked permission is denied permenantly, navigate user to app settings");
                                showSettingsDialog();
                            }
                            isShowDialogCheck = true;
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            LLog.d(TAG, "onPermissionRationaleShouldBeShown");
                            token.continuePermissionRequest();
                        }
                    })
                    .onSameThread()
                    .check();
        } else {
            AlertDialog alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to allow modifying system settings", "Okay", "Exit", new LDialogUtil.Callback2() {
                @Override
                public void onClick1() {
                    isShowDialogCheck = false;
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    LActivityUtil.tranIn(activity);
                }

                @Override
                public void onClick2() {
                    onBackPressed();
                }
            });
            alertDialog.setCancelable(false);
        }
    }

    private void goToHome() {
        if (isAnimDone && isCheckReadyDone) {
            Intent intent = new Intent(activity, MenuActivity.class);
            startActivity(intent);
            LActivityUtil.tranIn(activity);
            LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                @Override
                public void doAfter(int mls) {
                    finish();
                }
            });
        }
    }

    private void showDialogNotReady() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String title = "";
                if (LConnectivityUtil.isConnected(activity)) {
                    title = "This app is not available now";
                } else {
                    title = getString(R.string.check_ur_connection);
                }
                AlertDialog alertDial = LDialogUtil.showDialog1(activity, "Warning", title, "Ok", new LDialogUtil.Callback1() {
                    @Override
                    public void onClick1() {
                        onBackPressed();
                    }
                });
                alertDial.setCancelable(false);
            }
        });
    }

    private void checkReady() {
        if (LPref.getCheckAppReady(activity)) {
            isCheckReadyDone = true;
            goToHome();
            return;
        }
        LLog.d(TAG, "checkReady");
        final String LINK_GG_DRIVE_CHECK_READY = "https://drive.google.com/uc?export=download&id=1LHnBs4LG1EORS3FtdXpTVwQW2xONvtHo";
        Request request = new Request.Builder().url(LINK_GG_DRIVE_CHECK_READY).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LLog.d(TAG, "onFailure " + e.toString());
                showDialogNotReady();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //LLog.d(TAG, "onResponse isSuccessful " + response.toString());
                    int versionServer = Integer.parseInt(response.body().string());
                    LLog.d(TAG, "onResponse " + versionServer);
                    if (versionServer == 1) {
                        isCheckReadyDone = true;
                        LPref.setCheckAppReady(activity, true);
                        goToHome();
                    } else {
                        showDialogNotReady();
                    }
                } else {
                    LLog.d(TAG, "onResponse !isSuccessful: " + response.toString());
                    showDialogNotReady();
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
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_splash;
    }

    private void showSettingsDialog() {
        AlertDialog alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                isShowDialogCheck = false;
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }

            @Override
            public void onClick2() {
                onBackPressed();
            }
        });
        alertDialog.setCancelable(false);
    }

    private void showShouldAcceptPermission() {
        AlertDialog alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                checkPermission();
            }

            @Override
            public void onClick2() {
                onBackPressed();
            }
        });
        alertDialog.setCancelable(false);
    }
}
