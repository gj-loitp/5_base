package vn.loitp.app.activity.picker.crop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yalantis.ucrop.util.FileUtils;

import java.io.File;

import androidx.annotation.Nullable;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.picker.crop.CropImage;
import vn.loitp.picker.crop.LGalleryActivity;

public class CropActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //TODO need to check permission storage before
        LLog.d(TAG, "crop");
        startActivityForResult(new Intent(this, LGalleryActivity.class), LGalleryActivity.PICK_FROM_ALBUM);
        LActivityUtil.tranIn(activity);
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
                }
                break;
        }
    }
}
