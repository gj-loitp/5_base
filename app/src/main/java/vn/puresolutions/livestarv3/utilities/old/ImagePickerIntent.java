package vn.puresolutions.livestarv3.utilities.old;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 8/28/2015
 */
public class ImagePickerIntent {

    public static final int REQUEST_CAMERA = 0x135a;
    public static final int REQUEST_GALLERY = 1357;
    protected static final int IMAGE_CHAT_WIDTH_SIZE = 300;
    protected static final int IMAGE_CHAT_HEIGHT_SIZE = 300;
    public static final String TAG = ImagePickerIntent.class.getSimpleName();

    public static Intent chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    public static Intent takePicture() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }


    public static Uri getPath(Context context, int requestCode,
                              int resultCode, Intent intent) {
        Uri imagePath = null;
        if (intent != null) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    imagePath = intent.getData();
                    break;
                case REQUEST_GALLERY:
                    imagePath = intent.getData();
                    break;
                default:
                    break;
            }
        }
        return imagePath;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
