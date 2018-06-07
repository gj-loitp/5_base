package vn.loitp.core.utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import loitp.core.R;

/**
 * Created by LENOVO on 6/7/2018.
 */

public class LNotificationUtil {
    private final static String TAG = LNotificationUtil.class.getSimpleName();

    public static void createNotification(Context context, Class neededClass, String title, String bigTitle, String messageBody, int resLargeIcon, int resSmallIcon) {
        if (context == null) {
            return;
        }
        LLog.d(TAG, ">>>show");
        Intent intent = new Intent(context, neededClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), resLargeIcon);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(resSmallIcon)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                //.setContentIntent(resultIntent)
                .setContentText(messageBody)
                .setContentIntent(resultIntent);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(messageBody);
        bigText.setBigContentTitle(bigTitle);
        bigText.setSummaryText(messageBody);
        mBuilder.setStyle(bigText);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }
}
