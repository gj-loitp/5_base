package vn.loitp.core.loitp.gallery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.album.GalleryCoreAlbumActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.utils.util.AppUtils;

public class GalleryCoreSplashActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        setTransparentStatusNavigationBar();

        RestClient.init(getString(R.string.flickr_URL));

        ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        String urlCoverSplashScreen = getIntent().getStringExtra(Constants.BKG_SPLASH_SCREEN);
        if (urlCoverSplashScreen == null || urlCoverSplashScreen.isEmpty()) {
            urlCoverSplashScreen = Constants.URL_IMG_2;
        }
        LImageUtil.load(activity, urlCoverSplashScreen, ivBkg);

        TextView tvCopyright = (TextView) findViewById(R.id.tv_copyright);
        LUIUtil.setTextShadow(tvCopyright);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        LUIUtil.setColorProgressBar(progressBar, Color.WHITE);

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(AppUtils.getAppName() + "");
        LUIUtil.setTextShadow(tvName);
    }

    private void goToHome() {
        final ArrayList<String> removeAlbumList = getIntent().getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST);
        LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                Intent intent = new Intent(activity, GalleryCoreAlbumActivity.class);
                intent.putStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST, removeAlbumList == null ? new ArrayList<String>() : removeAlbumList);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
                finish();
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
        return R.layout.activity_gallery_core_splash;
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
}
