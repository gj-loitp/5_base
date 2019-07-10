package com.core.loitp.gallery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.loitp.gallery.album.GalleryCoreAlbumActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LImageUtil;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.restapi.restclient.RestClient;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.utils.util.AppUtils;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;

public class GalleryCoreSplashActivity extends BaseFontActivity {
    private int bkgRootView;
    private AdView adView;
    private String admobBannerUnitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExit = false;
        setTransparentStatusNavigationBar();
        RestClient.init(getString(R.string.flickr_URL));
        admobBannerUnitId = getIntent().getStringExtra(Constants.getAD_UNIT_ID_BANNER());
        LLog.d(TAG, "admobBannerUnitId " + admobBannerUnitId);
        final LinearLayout lnAdview = findViewById(R.id.ln_adview);
        if (admobBannerUnitId == null || admobBannerUnitId.isEmpty()) {
            lnAdview.setVisibility(View.GONE);
        } else {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(admobBannerUnitId);
            LUIUtil.createAdBanner(adView);
            lnAdview.addView(adView);
            int navigationHeight = DisplayUtil.getNavigationBarHeight(activity);
            LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4);
        }
        final ImageView ivBkg = findViewById(R.id.iv_bkg);
        String urlCoverSplashScreen = getIntent().getStringExtra(Constants.INSTANCE.getBKG_SPLASH_SCREEN());
        if (urlCoverSplashScreen == null || urlCoverSplashScreen.isEmpty()) {
            urlCoverSplashScreen = Constants.INSTANCE.getURL_IMG_2();
        }
        LImageUtil.load(activity, urlCoverSplashScreen, ivBkg);
        bkgRootView = getIntent().getIntExtra(Constants.getBKG_ROOT_VIEW(), Constants.getNOT_FOUND());
        LLog.d(TAG, "bkgRootView " + bkgRootView);
        if (bkgRootView == Constants.getNOT_FOUND()) {
            getRootView().setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        } else {
            getRootView().setBackgroundResource(bkgRootView);
        }

        final TextView tvCopyright = findViewById(R.id.tv_copyright);
        LUIUtil.setTextShadow(tvCopyright);

        final TextView tvName = findViewById(R.id.tv_name);
        tvName.setText(AppUtils.getAppName());
        LUIUtil.setTextShadow(tvName);
    }

    private void goToHome() {
        final ArrayList<String> removeAlbumList = getIntent().getStringArrayListExtra(Constants.getKEY_REMOVE_ALBUM_FLICKR_LIST());
        LUIUtil.setDelay(3000, mls -> {
            final Intent intent = new Intent(activity, GalleryCoreAlbumActivity.class);
            intent.putExtra(Constants.getAD_UNIT_ID_BANNER(), admobBannerUnitId);
            intent.putExtra(Constants.getBKG_ROOT_VIEW(), bkgRootView);
            intent.putStringArrayListExtra(Constants.getKEY_REMOVE_ALBUM_FLICKR_LIST(), removeAlbumList == null ? new ArrayList<String>() : removeAlbumList);
            startActivity(intent);
            LActivityUtil.tranIn(activity);
            finish();
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
        return R.layout.activity_gallery_core_splash;
    }

    @Override
    protected void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
        if (!isShowDialogCheck) {
            checkPermission();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private boolean isShowDialogCheck;

    private void checkPermission() {
        isShowDialogCheck = true;
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
                            goToHome();
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
    }

    private void showShouldAcceptPermission() {
        final AlertDialog alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", new LDialogUtil.Callback2() {
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

    private void showSettingsDialog() {
        final AlertDialog alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", new LDialogUtil.Callback2() {
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
}
