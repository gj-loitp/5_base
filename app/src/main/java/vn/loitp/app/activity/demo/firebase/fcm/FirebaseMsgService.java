package vn.loitp.app.activity.demo.firebase.fcm;

/**
 * Created by LENOVO on 6/7/2018.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import loitp.basemaster.R;
import vn.loitp.app.activity.SplashActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LNotificationUtil;
import vn.loitp.utils.util.AppUtils;

public class FirebaseMsgService extends FirebaseMessagingService {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            if (remoteMessage == null || remoteMessage.getNotification() == null) {
                return;
            }
            LLog.d(TAG, "From: " + remoteMessage.getFrom());
            LLog.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

            String appName = AppUtils.getAppName();
            String title = appName + " miss you!";
            String bigTitle = appName + " miss you!";
            String messageBody = remoteMessage.getNotification().getBody();
            int resLargeIcon = R.mipmap.ic_launcher;
            int resSmallIcon = R.mipmap.ic_launcher;
            LNotificationUtil.createNotification(this, SplashActivity.class, title, bigTitle, messageBody, resLargeIcon, resSmallIcon);
        } catch (Exception e) {
            LLog.e(TAG, "onMessageReceived Exception " + e.toString());
        }
    }


}
