package vn.loitp.app.activity.demo.firebase.fcm;

import android.content.Intent;
import android.graphics.Color;

import com.core.common.Constants;
import com.function.notification.Notti;
import com.function.notification.NottiFactory;
import com.function.notification.actions.ContentAction;
import com.function.notification.config.LightSettings;
import com.function.notification.config.NottiConf;
import com.function.notification.config.VibrationSettings;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.utils.util.AppUtils;

import org.jetbrains.annotations.NotNull;

import vn.loitp.app.R;
import vn.loitp.app.activity.SplashActivity;

public class FirebaseMsgService extends FirebaseMessagingService {
    private final String logTag = getClass().getSimpleName();

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        try {
            if (remoteMessage.getNotification() == null) {
                return;
            }

            //THIS CODE BELOWS SEND A NOTIFICATION
            if (Constants.Companion.getIS_DEBUG()) {
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
            e.printStackTrace();
        }
    }

}
