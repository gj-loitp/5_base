package vn.loitp.app.utilities;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by www.muathu@gmail.com on 6/1/2017.
 */

public class LSoundUtil {
    private static final String TAG = LSoundUtil.class.getSimpleName();

    public static void startMusicFromAsset(Context context, String fileName) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd(fileName);
            mediaPlayer.setDataSource(
                    assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength()
            );
            assetFileDescriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    if (mediaPlayer != null) {
                        //LLog.d(TAG, "onCompletion >>> release");
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            });
        } catch (IOException e) {
            //LLog.d(TAG, "startMusicFromAsset: " + e.toString());
        }
    }
}
