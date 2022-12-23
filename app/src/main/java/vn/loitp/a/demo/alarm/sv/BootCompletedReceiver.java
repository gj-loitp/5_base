package vn.loitp.a.demo.alarm.sv;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.loitp.a.demo.alarm.adt.AlarmListAdapter;

public class BootCompletedReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        // just create AlarmListAdapter and it will load alarms and start them
        new AlarmListAdapter(context);
    }
}
