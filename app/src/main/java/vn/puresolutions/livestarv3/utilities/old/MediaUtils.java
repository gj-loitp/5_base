package vn.puresolutions.livestarv3.utilities.old;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class MediaUtils {
    public static void playSound(Context context, String filename) {
        try {
            MediaPlayer m = new MediaPlayer();
            AssetFileDescriptor descriptor = context.getAssets().openFd(filename);
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(1f, 1f);
            m.setLooping(false);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playVipComingSound(Context context) {
        playSound(context, "sounds/vip_vao_phong.mp3");
    }

    public static void playLevelUpSound(Context context) {
        playSound(context, "sounds/level_up.mp3");
    }
}
