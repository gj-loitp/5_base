package com.loitpcore.function.notification.notifications;

import android.graphics.Bitmap;

import androidx.core.app.NotificationCompat;

import com.loitpcore.function.notification.actions.ContentAction;
import com.loitpcore.function.notification.actions.NotificationAction;
import com.loitpcore.function.notification.config.LightSettings;
import com.loitpcore.function.notification.config.VibrationSettings;

import java.util.List;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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

    @Suppress(names = "unused")
    CustomNotification setInboxItems(List<String> itemList);

    CustomNotification setContentAction(ContentAction contentAction);

    ContentAction getContentAction();

    LightSettings getLightSettings();

    @Suppress(names = "unused")
    CustomNotification setLightSettings(LightSettings lightSettings);

    @Suppress(names = "unused")
    CustomNotification setVibrationSettings(VibrationSettings vibrations);

    VibrationSettings getVibrationSettings();
}
