package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import loitp.core.R;
import vn.loitp.views.LToast;

public class LPickerUtil {
    public static void cropImage(@NonNull final Activity activity, @NonNull final Uri picUri, final int CROP_PIC_REQUEST_CODE,
                                 final int aspectX, final int aspectY, final int outputX, final int outputY) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", aspectX);
            cropIntent.putExtra("aspectY", aspectY);
            cropIntent.putExtra("outputX", outputX);
            cropIntent.putExtra("outputY", outputY);
            cropIntent.putExtra("noFaceDetection", true);
            cropIntent.putExtra("return-data", true);
            activity.startActivityForResult(cropIntent, CROP_PIC_REQUEST_CODE);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            final String errorMessage = "Whoops - your device doesn't support the crop action!";
            LToast.showShort(activity, errorMessage, R.drawable.bkg_horizontal);
        }
    }
}
