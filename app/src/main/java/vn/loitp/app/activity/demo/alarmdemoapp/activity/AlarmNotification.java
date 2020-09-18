package vn.loitp.app.activity.demo.alarmdemoapp.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LLog;

import java.util.Timer;
import java.util.TimerTask;

import vn.loitp.app.R;
import vn.loitp.app.activity.demo.alarmdemoapp.model.Alarm;
import vn.loitp.app.activity.demo.alarmdemoapp.model.DateTime;

@LayoutId(R.layout.activity_alarm_notification)
@LogTag("AlarmNotification")
public class AlarmNotification extends BaseFontActivity {
    private Ringtone mRingtone;
    private Vibrator mVibrator;
    private final long[] mVibratePattern = {0, 500, 500};
    private boolean mVibrate;
    private Uri mAlarmSound;
    private long mPlayTime;
    private Timer mTimer = null;
    private Alarm mAlarm;
    private DateTime mDateTime;
    private TextView mTextView;
    private PlayTimerTask mTimerTask;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mDateTime = new DateTime(this);
        mTextView = findViewById(R.id.alarm_title_text);

        readPreferences();

        mRingtone = RingtoneManager.getRingtone(getApplicationContext(), mAlarmSound);
        if (mVibrate)
            mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        start(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LLog.d(getLogTag(), "AlarmNotification.onDestroy()");

        stop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LLog.d(getLogTag(), "AlarmNotification.onNewIntent()");

        addNotification(mAlarm);

        stop();
        start(intent);
    }

    private void start(Intent intent) {
        mAlarm = new Alarm(this);
        mAlarm.fromIntent(intent);

        LLog.d(getLogTag(), "AlarmNotification.start('" + mAlarm.getTitle() + "')");

        mTextView.setText(mAlarm.getTitle());

        mTimerTask = new PlayTimerTask();
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, mPlayTime);
        mRingtone.play();
        if (mVibrate)
            mVibrator.vibrate(mVibratePattern, 0);
    }

    private void stop() {
        LLog.d(getLogTag(), "AlarmNotification.stop()");

        mTimer.cancel();
        mRingtone.stop();
        if (mVibrate)
            mVibrator.cancel();
    }

    public void onDismissClick(View view) {
        finish();
        LActivityUtil.tranIn(getActivity());
    }

    private void readPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mAlarmSound = Uri.parse(prefs.getString("alarm_sound_pref", "DEFAULT_RINGTONE_URI"));
        mVibrate = prefs.getBoolean("vibrate_pref", true);
        mPlayTime = (long) Integer.parseInt(prefs.getString("alarm_play_time_pref", "30")) * 1000;
    }

    private void addNotification(Alarm alarm) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        PendingIntent activity;
        Intent intent;

        LLog.d(getLogTag(), "AlarmNotification.addNotification(" + alarm.getId() + ", '" + alarm.getTitle() + "', '" + mDateTime.formatDetails(alarm) + "')");

        intent = new Intent(this.getApplicationContext(), AlarmMeActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        activity = PendingIntent.getActivity(this, (int) alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        notification = builder
                .setContentIntent(activity)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle("Missed alarm: " + alarm.getTitle())
                .setContentText(mDateTime.formatDetails(alarm))
                .build();

        notificationManager.notify((int) alarm.getId(), notification);
    }

    @Override
    public void onBackPressed() {
        finish();
        LActivityUtil.tranIn(getActivity());
    }

    private class PlayTimerTask extends TimerTask {
        @Override
        public void run() {
            LLog.d(getLogTag(), "AlarmNotification.PalyTimerTask.run()");
            addNotification(mAlarm);
            finish();
            LActivityUtil.tranIn(getActivity());
        }
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }
}

