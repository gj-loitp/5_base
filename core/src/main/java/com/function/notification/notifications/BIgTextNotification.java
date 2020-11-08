package com.function.notification.notifications;

import androidx.core.app.NotificationCompat;

//TODO convert kotlin
public class BIgTextNotification extends BaseNotification {

    public BIgTextNotification(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public NotificationCompat.Builder setBuilder(NotificationCompat.Builder builder) {
        builder.setContentTitle(title).setContentText(content);
        setBuilderIcon(builder);
        setBigTextStyle(builder);

        return builder;
    }

    public void setBuilderIcon(NotificationCompat.Builder builder) {
        if (icon != null) {
            builder.setSmallIcon(icon);
        }
    }

    public void setBigTextStyle(NotificationCompat.Builder builder) {
        if (bigText != null) {
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));
        }
    }
}
