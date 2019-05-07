package vn.loitp.function.notification.notifications;

import android.graphics.Bitmap;
import androidx.core.app.NotificationCompat;

import java.util.List;

import vn.loitp.function.notification.actions.ContentAction;
import vn.loitp.function.notification.actions.NotificationAction;
import vn.loitp.function.notification.config.LightSettings;
import vn.loitp.function.notification.config.VibrationSettings;

public interface CustomNotification {

    CustomNotification setTitle(String title);

    CustomNotification setContent(String content);

    CustomNotification setIcon(int icon);

    CustomNotification setActions(List<NotificationAction> actions);

    CustomNotification setId(int ID);

    NotificationCompat.Builder setBuilder(NotificationCompat.Builder builder);

    List<NotificationAction> getActions();

    CustomNotification setBigText(String text);

    CustomNotification setBigPicture(Bitmap picture);

    CustomNotification setLargeIcon(Bitmap image);

    CustomNotification addInboxItem(String item);

    CustomNotification setInboxSummary(String item);

    CustomNotification setInboxItems(List<String> itemList);

    CustomNotification setContentAction(ContentAction contentAction);

    ContentAction getContentAction();

    LightSettings getLightSettings();

    CustomNotification setLightSettings(LightSettings lightSettings);

    CustomNotification setVibrationSettings(VibrationSettings vibrations);

    VibrationSettings getVibrationSettings();
}
