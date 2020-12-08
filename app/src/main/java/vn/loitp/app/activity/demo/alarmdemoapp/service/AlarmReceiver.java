package vn.loitp.app.activity.demo.alarmdemoapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.loitp.app.activity.demo.alarmdemoapp.activity.AlarmNotification;
import vn.loitp.app.activity.demo.alarmdemoapp.model.Alarm;

//TODO convert kotlin
public class AlarmReceiver extends BroadcastReceiver {
    private final String logTag = "AlarmMeActivity";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, AlarmNotification.class);
        Alarm alarm = new Alarm(context);

        alarm.fromIntent(intent);
        alarm.toIntent(newIntent);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(newIntent);
    }
}
