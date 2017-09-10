package vn.puresolutions.livestar.notification.handler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.MainActivity;

/**
 * Created by khanh on 2/20/16.
 */
public class NotificationHandler {
    public static final String BUNDLE_KEY_IS_FROM_NOTIFICATION = "isFromNotification";
    public static final String BUNDLE_KEY_ROOM_ID = "roomId";
    public static final String ON_AIR_NOTIFICATION = "onair";

    private Context context;

    public NotificationHandler(Context context) {
        this.context = context;
    }

    public void pushNotification(Bundle data) {
        Log.i("Notification", data.toString());
        String type  = data.getString("type");
        if (ON_AIR_NOTIFICATION.equals(type)) {
            String title = data.getString("title");
            String message = data.getString("description");
            int roomId = Integer.parseInt(data.getString("room_id"));
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(context, R.color.notification))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentIntent(getContentIntent(roomId));
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(roomId, builder.build());
        }
    }

    protected PendingIntent getContentIntent(int roomId) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra(BUNDLE_KEY_IS_FROM_NOTIFICATION, true);
        notificationIntent.putExtra(BUNDLE_KEY_ROOM_ID, roomId);
        return PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
