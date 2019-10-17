package vn.loitp.app.activity.function.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.NotificationCompat;

import com.core.base.BaseFontActivity;
import com.function.notification.Notti;
import com.function.notification.NottiFactory;
import com.function.notification.actions.ContentAction;
import com.function.notification.actions.NotificationAction;
import com.function.notification.config.LightSettings;
import com.function.notification.config.NottiConf;
import com.function.notification.config.VibrationSettings;

import java.util.Arrays;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.SplashActivity;

public class MenuNotificationActivity extends BaseFontActivity implements View.OnClickListener {
    private Notti notti;
    private String channelId = "my_package_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notti = new Notti(this,
                new NottiConf(R.mipmap.ic_launcher,
                        new VibrationSettings(VibrationSettings.STD_VIBRATION),
                        new LightSettings(Color.BLUE)));
        findViewById(R.id.simpleNotification).setOnClickListener(this);
        findViewById(R.id.simpleNotificationActions).setOnClickListener(this);
        findViewById(R.id.bigTextNotification).setOnClickListener(this);
        findViewById(R.id.inboxNotification).setOnClickListener(this);
        findViewById(R.id.bigPictureNotification).setOnClickListener(this);
        findViewById(R.id.btNotificationHeadsup).setOnClickListener(this);

        goToNotificationSettings(activity);
    }

    private void goToNotificationSettings(Context context) {
        String packageName = context.getPackageName();
        try {
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                //intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                //intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
                intent.putExtra("android.provider.extra.CHANNEL_ID", channelId);
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", packageName);
                intent.putExtra("app_uid", context.getApplicationInfo().uid);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + packageName));
            } else {
                return;
            }

            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_notification_menu;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.simpleNotification:
                notti.show(NottiFactory
                        .get(NottiFactory.TYPE.STANDARD, "some text", "some content")
                        .setContentAction(new ContentAction(new Intent(this, MenuNotificationActivity.class), this)));
                break;
            case R.id.simpleNotificationActions:
                intent = new Intent(this, MenuNotificationActivity.class);

                List<NotificationAction> actionsList = Arrays
                        .asList(new NotificationAction("action", intent, this),
                                new NotificationAction("action 2", intent, this));

                notti.show(NottiFactory
                        .get(NottiFactory.TYPE.STANDARD, "some text", "some content")
                        .setActions(actionsList));
                break;
            case R.id.bigTextNotification:
                notti.show(NottiFactory
                        .get(NottiFactory.TYPE.BIG_TEXT, "some text", "some content").setBigText(
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam posuere arcu enim, ut imperdiet sem pellentesque quis. Morbi in tempus lorem. Integer venenatis risus sit amet dolor lobortis, et consequat neque luctus. Etiam ut est nulla. Quisque turpis sapien, aliquet a consequat in, lacinia ut neque. Praesent scelerisque maximus nisi, sed pharetra nulla varius id. Proin at augue purus. Aliquam ut ullamcorper lorem, at vehicula nisl. Pellentesque imperdiet nunc vitae quam consectetur tempus. Nullam vel auctor orci. Ut a turpis ac quam placerat vestibulum. Sed ac hendrerit lorem, non imperdiet neque. Sed nisl urna, eleifend ac sem et, accumsan consectetur felis. Quisque cursus interdum erat, sit amet maximus felis consectetur ac. Aenean luctus, mi nec elementum bibendum, felis felis lacinia justo, vitae lacinia ligula nibh ut nulla. Nunc viverra commodo augue, in cursus nulla."));
                break;
            case R.id.inboxNotification:
                notti.show(NottiFactory
                        .get(NottiFactory.TYPE.INBOX, "some text", "some content")
                        .addInboxItem("some item").addInboxItem("another item")
                        .addInboxItem("and final item").setInboxSummary("random summary"));
                break;
            case R.id.bigPictureNotification:
                Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
                Bitmap iconBig = BitmapFactory.decodeResource(this.getResources(), R.drawable.iv);
                notti.show(NottiFactory
                        .get(NottiFactory.TYPE.BIG_PICTURE, "some text", "some " + "content")
                        .setBigPicture(iconBig).setLargeIcon(icon));
                break;
            case R.id.btNotificationHeadsup:
                createNotification("Loitpppppppppppppppppppppppppppp");
                break;
        }
    }

    private NotificationManager notifManager;

    public void createNotification(String aMessage) {
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = channelId;
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setLightColor(Color.GREEN);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(aMessage)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(this.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(this);
            intent = new Intent(this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(aMessage)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(this.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}
