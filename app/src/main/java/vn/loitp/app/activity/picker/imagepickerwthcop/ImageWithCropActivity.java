package vn.loitp.app.activity.picker.imagepickerwthcop;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.core.base.BaseFontActivity;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LLog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.picker.imagepickerwithcrop.PickerBuilder;
import com.utils.util.AppUtils;
import com.views.LToast;

import java.util.List;

import loitp.basemaster.R;

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

        (findViewById(R.id.startGalleryBtn)).setOnClickListener(v -> new PickerBuilder(getActivity(), PickerBuilder.SELECT_FROM_GALLERY)
                .setOnImageReceivedListener(imageUri -> {
                    imageView.setImageURI(imageUri);
                    LToast.showShort(getActivity(), "Got image - " + imageUri, R.drawable.l_bkg_horizontal);
                })
                .setImageName(name + System.currentTimeMillis())
                .setImageFolderName(name)
                .setCropScreenColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                .setOnPermissionRefusedListener(() -> {
                })
                .start());

        (findViewById(R.id.startCameraBtn)).setOnClickListener(v -> new PickerBuilder(getActivity(), PickerBuilder.SELECT_FROM_CAMERA)
                .setOnImageReceivedListener(imageUri -> {
                    imageView.setImageURI(imageUri);
                    LToast.showShort(getActivity(), "Got image - " + imageUri, R.drawable.l_bkg_horizontal);
                })
                .setImageName(name + System.currentTimeMillis())
                .setImageFolderName(name)
                .withTimeStamp(false)
                .setCropScreenColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                .start());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isShowDialogCheck) {
            checkPermission();
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
    protected int setLayoutResourceId() {
        return R.layout.activity_image_picker_with_crop;
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
                            LLog.d(getTAG(), "onPermissionsChecked do you work now");
                        } else {
                            LLog.d(getTAG(), "!areAllPermissionsGranted");
                            showShouldAcceptPermission();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            LLog.d(getTAG(), "onPermissionsChecked permission is denied permenantly, navigate user to app settings");
                            showSettingsDialog();
                        }
                        isShowDialogCheck = true;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        LLog.d(getTAG(), "onPermissionRationaleShouldBeShown");
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showShouldAcceptPermission() {
        final AlertDialog alertDialog = LDialogUtil.INSTANCE.showDialog2(getActivity(), "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", new LDialogUtil.Callback2() {
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
        final AlertDialog alertDialog = LDialogUtil.INSTANCE.showDialog2(getActivity(), "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", new LDialogUtil.Callback2() {
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
