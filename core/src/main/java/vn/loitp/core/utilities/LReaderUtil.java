package vn.loitp.core.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import loitp.core.R;

public class LReaderUtil {
    public static Bitmap decodeBitmapFromByteArray(byte[] coverImage, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(coverImage, 0, coverImage.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(coverImage, 0, coverImage.length, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static int getDefaultCover() {
        return R.drawable.l_book_5;
    }

    /*public static void readEpub(Activity activity, BookInfo bookInfo) {
        if (activity == null || bookInfo == null) {
            return;
        }
        final Intent intent = new Intent(activity, EpubReaderReadActivity.class);
        intent.putExtra(EpubReaderReadActivity.BOOK_INFO, bookInfo);
        intent.putExtra(EpubReaderReadActivity.IS_USE_FONT, true);
        intent.putExtra(EpubReaderReadActivity.IS_WEBVIEW, false);
        startActivity(intent);
    }*/
}
