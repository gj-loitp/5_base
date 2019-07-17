package vn.loitp.app.activity.picker.crop;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LImageUtil;
import com.core.utilities.LLog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.picker.crop.CropImage;
import com.picker.crop.CropImageView;
import com.picker.crop.LGalleryActivity;
import com.views.LToast;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;

import loitp.basemaster.R;

public class CropActivity extends BaseFontActivity {
    private ImageView iv;
    private final int REQUEST_CODE_GET_FILE = 1;
    private boolean isOvalOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = findViewById(R.id.iv);
        findViewById(R.id.bt_crop_oval).setOnClickListener(view -> {
            isOvalOption = true;
            crop();
        });
        findViewById(R.id.bt_crop_square).setOnClickListener(view -> {
            isOvalOption = false;
            crop();
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
        return R.layout.activity_crop;
    }

    private void crop() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivityForResult(new Intent(getActivity(), LGalleryActivity.class), REQUEST_CODE_GET_FILE);
                        LActivityUtil.tranIn(getActivity());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        LToast.showShort(getActivity(), "Error onPermissionDenied WRITE_EXTERNAL_STORAGE", R.drawable.bkg_horizontal);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        LToast.showShort(getActivity(), "Error onPermissionDenied WRITE_EXTERNAL_STORAGE", R.drawable.bkg_horizontal);
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LLog.d(getTAG(), "onActivityResult requestCode " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            LLog.e(getTAG(), "data == null return");
            return;
        }
        if (requestCode == REQUEST_CODE_GET_FILE) {
            if (data.getExtras() == null) {
                LLog.e(getTAG(), "data.getExtras() == null return");
                return;
            }
            final String filePath = data.getExtras().getString(LGalleryActivity.RETURN_VALUE);
            if (filePath == null) {
                LLog.e(getTAG(), "filePath == null return");
                return;
            }
            LLog.d(getTAG(), "filePath " + filePath);
            final File file = new File(filePath);
            if (!file.exists()) {
                LLog.e(getTAG(), "file is not exists");
                return;
            }
            final Uri imageUri = Uri.fromFile(file);
            if (imageUri == null) {
                LLog.e(getTAG(), "imageUri == null");
                return;
            }
            if (isOvalOption) {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setBorderLineColor(Color.WHITE)
                        .setBorderLineThickness(2)
                        .setCircleSize(30)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setCircleColor(Color.WHITE)
                        .setBackgroundColor(Color.argb(200, 0, 0, 0))
                        .start(getActivity());
            } else {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .setBorderLineColor(Color.RED)
                        .setBorderLineThickness(2)
                        .setCircleSize(15)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCircleColor(Color.RED)
                        .setBackgroundColor(Color.argb(200, 0, 0, 0))
                        .setAspectRatio(1, 1)
                        .setAutoZoomEnabled(true)
                        .setBorderCornerColor(Color.BLUE)
                        .setGuidelinesColor(Color.GREEN)
                        .start(getActivity());
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                final String realPath = FileUtils.getPath(this, result.getUri());
                if (realPath == null) {
                    return;
                }
                final File file = new File(realPath);
                LLog.d(getTAG(), "onActivityResult file " + file.getPath());
                LImageUtil.load(getActivity(), file, iv);
            }
        }
    }
}
