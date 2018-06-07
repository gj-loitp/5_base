package vn.loitp.app.activity.demo.firebase.fcm;

/**
 * Created by LENOVO on 6/7/2018.
 */

import android.content.Intent;
import android.graphics.Color;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import loitp.basemaster.R;
import vn.loitp.app.activity.SplashActivity;
import vn.loitp.app.activity.function.notification.MenuNotificationActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.function.notification.Notti;
import vn.loitp.function.notification.NottiFactory;
import vn.loitp.function.notification.actions.ContentAction;
import vn.loitp.function.notification.config.LightSettings;
import vn.loitp.function.notification.config.NottiConf;
import vn.loitp.function.notification.config.VibrationSettings;
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
            String messageBody = remoteMessage.getNotification().getBody();

            Notti notti = new Notti(this,
                    new NottiConf(R.mipmap.ic_launcher,
                            new VibrationSettings(VibrationSettings.STD_VIBRATION),
                            new LightSettings(Color.RED)));
            notti.show(NottiFactory
                    .get(NottiFactory.TYPE.STANDARD, title, messageBody)
                    .setContentAction(new ContentAction(new Intent(this, SplashActivity.class), this)));
        } catch (Exception e) {
            LLog.e(TAG, "onMessageReceived Exception " + e.toString());
        }
    }

}
