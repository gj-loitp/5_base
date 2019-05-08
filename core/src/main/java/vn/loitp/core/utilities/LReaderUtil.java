package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.function.epub.core.EpubReaderReadActivity;
import vn.loitp.function.epub.model.BookInfo;
import vn.loitp.function.epub.model.BookInfoData;

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
        return R.drawable.df_cover_epub;
    }

    public static void readEpub(Activity activity, BookInfo bookInfo, String admobAdIdBanner) {
        if (activity == null || bookInfo == null) {
            throw new NullPointerException("activity == null || bookInfo == null");
        }
        final Intent intent = new Intent(activity, EpubReaderReadActivity.class);
        BookInfoData.getInstance().setBookInfo(bookInfo);
        intent.putExtra(Constants.INSTANCE.getAD_UNIT_ID_BANNER(), admobAdIdBanner);
        activity.startActivity(intent);
        LActivityUtil.INSTANCE.tranIn(activity);
    }
}
