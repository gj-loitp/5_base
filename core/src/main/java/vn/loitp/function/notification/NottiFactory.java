package vn.loitp.function.notification;

import vn.loitp.function.notification.notifications.BIgTextNotification;
import vn.loitp.function.notification.notifications.BigPictureNotification;
import vn.loitp.function.notification.notifications.CustomNotification;
import vn.loitp.function.notification.notifications.InboxNotification;
import vn.loitp.function.notification.notifications.StandardNotification;

public class NottiFactory {

    public enum TYPE {INBOX, BIG_TEXT, BIG_PICTURE, STANDARD}

    public static CustomNotification get(TYPE type, String title, String content) {
        switch (type) {
            case STANDARD:
                return new StandardNotification(title, content);
            case BIG_TEXT:
                return new BIgTextNotification(title, content);
            case BIG_PICTURE:
                return new BigPictureNotification(title, content);
            case INBOX:
                return new InboxNotification(title, content);
            default:
                return new StandardNotification(title, content);
        }
    }
}
