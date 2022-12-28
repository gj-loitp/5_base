package com.loitp.func.wallpo;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Loitp on 28,December,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public class Wallpo {
    public static WallpaperManager setMainScreenWallpaper(Context context, ImageView imageView, String message) {
        Bitmap bitmap;

        WallpaperManager manager = WallpaperManager.getInstance(context);
        try {
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
            } else {

                manager.setBitmap(bitmap);
            }

            if (message.isEmpty()) {
                message = "Wallpaper set successfully";
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static WallpaperManager setLockScreenWallpaper(Activity activity, ImageView imageView, String message) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            WallpaperManager manager = WallpaperManager.getInstance(activity);

            try {
                manager.setBitmap(((BitmapDrawable) imageView.getDrawable()).getBitmap(), null, true, WallpaperManager.FLAG_LOCK);

                if (message.isEmpty()) {
                    message = "Lock Screen Wallpaper set successfully";
                }

                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();


            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(activity, "Not supported with the version", Toast.LENGTH_SHORT).show();
        }
        return null;

    }
}
