package vn.loitp.app.activity.demo.alarmdemoapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.loitp.app.activity.demo.alarmdemoapp.adapter.AlarmListAdapter;

//TODO convert kotlin
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // just create AlarmListAdapter and it will load alarms and start them
        new AlarmListAdapter(context);
    }
}
