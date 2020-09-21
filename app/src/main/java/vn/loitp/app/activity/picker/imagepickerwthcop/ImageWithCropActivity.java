package vn.loitp.app.activity.picker.imagepickerwthcop;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LLog;
import com.interfaces.Callback2;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.picker.imagepickerwithcrop.PickerBuilder;
import com.utils.util.AppUtils;
import com.views.LToast;

import java.util.List;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_image_picker_with_crop)
@LogTag("ImageWithCropActivity")
@IsFullScreen(false)
public class ImageWithCropActivity extends BaseFontActivity {
    private ImageView imageView;
    private boolean isShowDialogCheck;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = findViewById(R.id.imageView);
        name = AppUtils.getAppName() + "";
        //check permission
        checkPermission();

        (findViewById(R.id.startGalleryBtn)).setOnClickListener(v -> new PickerBuilder(this, PickerBuilder.SELECT_FROM_GALLERY)
                .setOnImageReceivedListener(imageUri -> {
                    imageView.setImageURI(imageUri);
                    LToast.showShort(this, "Got image - " + imageUri, R.drawable.l_bkg_horizontal);
                })
                .setImageName(name + System.currentTimeMillis())
                .setImageFolderName(name)
                .setCropScreenColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setOnPermissionRefusedListener(() -> {
                })
                .start());

        (findViewById(R.id.startCameraBtn)).setOnClickListener(v -> new PickerBuilder(this, PickerBuilder.SELECT_FROM_CAMERA)
                .setOnImageReceivedListener(imageUri -> {
                    imageView.setImageURI(imageUri);
                    LToast.showShort(this, "Got image - " + imageUri, R.drawable.l_bkg_horizontal);
                })
                .setImageName(name + System.currentTimeMillis())
                .setImageFolderName(name)
                .withTimeStamp(false)
                .setCropScreenColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .start());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isShowDialogCheck) {
            checkPermission();
        }
    }

    private void checkPermission() {
        isShowDialogCheck = true;
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            LLog.d(getLogTag(), "onPermissionsChecked do you work now");
                        } else {
                            LLog.d(getLogTag(), "!areAllPermissionsGranted");
                            showShouldAcceptPermission();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            LLog.d(getLogTag(), "onPermissionsChecked permission is denied permenantly, navigate user to app settings");
                            showSettingsDialog();
                        }
                        isShowDialogCheck = true;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        LLog.d(getLogTag(), "onPermissionRationaleShouldBeShown");
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showShouldAcceptPermission() {
        final AlertDialog alertDialog = LDialogUtil.Companion.showDialog2(this, "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", new Callback2() {
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
        final AlertDialog alertDialog = LDialogUtil.Companion.showDialog2(this, "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", new Callback2() {
            @Override
            public void onClick1() {
                isShowDialogCheck = false;
                final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                final Uri uri = Uri.fromParts("package", getPackageName(), null);
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
