package vn.loitp.app.activity.picker.crop;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;

import androidx.annotation.Nullable;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.picker.crop.CropImage;
import vn.loitp.picker.crop.LGalleryActivity;
import vn.loitp.views.LToast;

public class CropActivity extends BaseFontActivity {
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv = findViewById(R.id.iv);
        findViewById(R.id.bt_crop).setOnClickListener(view -> {
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
                        startActivityForResult(new Intent(activity, LGalleryActivity.class), LGalleryActivity.PICK_FROM_ALBUM);
                        LActivityUtil.tranIn(activity);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        LToast.showShort(activity, "Error onPermissionDenied WRITE_EXTERNAL_STORAGE", R.drawable.bkg_horizontal);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        LToast.showShort(activity, "Error onPermissionDenied WRITE_EXTERNAL_STORAGE", R.drawable.bkg_horizontal);
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LLog.d(TAG, "onActivityResult requestCode " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        final CropImage.ActivityResult result = CropImage.getActivityResult(data);
        switch (requestCode) {
            case LGalleryActivity.PICK_FROM_ALBUM:
                if (result != null) {
                    final String realPath = FileUtils.getPath(this, result.getUri());
                    if (realPath == null) {
                        return;
                    }
                    final File file = new File(realPath);
                    LLog.d(TAG, "onActivityResult file " + file.getPath());
                    LImageUtil.load(activity, file, iv);
                }
                break;
        }
    }
}
