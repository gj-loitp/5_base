package vn.loitp.app.activity.function.notification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.function.notification.Notti;
import vn.loitp.function.notification.NottiFactory;
import vn.loitp.function.notification.actions.ContentAction;
import vn.loitp.function.notification.actions.NotificationAction;
import vn.loitp.function.notification.config.LightSettings;
import vn.loitp.function.notification.config.NottiConf;
import vn.loitp.function.notification.config.VibrationSettings;

public class MenuNotificationActivity extends BaseFontActivity implements View.OnClickListener {
    private Notti notti;

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
        }
    }
}
