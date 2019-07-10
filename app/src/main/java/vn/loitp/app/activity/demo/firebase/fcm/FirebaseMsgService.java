package vn.loitp.app.activity.demo.firebase.fcm;

/**
 * Created by LENOVO on 6/7/2018.
 */

import android.content.Intent;
import android.graphics.Color;

import com.core.common.Constants;
import com.core.utilities.LLog;
import com.function.notification.Notti;
import com.function.notification.NottiFactory;
import com.function.notification.actions.ContentAction;
import com.function.notification.config.LightSettings;
import com.function.notification.config.NottiConf;
import com.function.notification.config.VibrationSettings;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import loitp.basemaster.R;
import vn.loitp.app.activity.SplashActivity;
import vn.loitp.utils.util.AppUtils;

public class FirebaseMsgService extends FirebaseMessagingService {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            if (remoteMessage == null || remoteMessage.getNotification() == null) {
                return;
            }
            LLog.INSTANCE.d(TAG, "From: " + remoteMessage.getFrom());
            LLog.INSTANCE.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

            //THIS CODE BELOWS SEND A NOTIFICATION
            if (Constants.INSTANCE.getIS_DEBUG()) {
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
            }
        } catch (Exception e) {
            LLog.INSTANCE.e(TAG, "onMessageReceived Exception " + e.toString());
        }
    }

}
