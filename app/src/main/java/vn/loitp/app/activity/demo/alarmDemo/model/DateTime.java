package vn.loitp.app.activity.demo.alarmDemo.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Keep;

import com.loitpcore.core.base.BaseModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@Keep
public class DateTime extends BaseModel {
    private final Context mContext;
    private String[] mFullDayNames;
    private String[] mShortDayNames;
    private boolean mWeekStartsOnMonday;
    private boolean m24hClock;
    private SimpleDateFormat mTimeFormat;
    private SimpleDateFormat mDateFormat;

    public DateTime(Context context) {
        mContext = context;
        update();
    }

    public void update() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mWeekStartsOnMonday = prefs.getBoolean("week_starts_pref", false);
        m24hClock = prefs.getBoolean("use_24h_pref", false);

        mDateFormat = new SimpleDateFormat("E MMM d, yyyy", Locale.getDefault());

        if (m24hClock)
            mTimeFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        else
            mTimeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

        mFullDayNames = new String[7];
        mShortDayNames = new String[7];

        SimpleDateFormat fullFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat shortFormat = new SimpleDateFormat("E", Locale.getDefault());
        Calendar calendar;

        if (mWeekStartsOnMonday)
            calendar = new GregorianCalendar(2012, Calendar.AUGUST, 6);
        else
            calendar = new GregorianCalendar(2012, Calendar.AUGUST, 5);

        for (int i = 0; i < 7; i++) {
            mFullDayNames[i] = fullFormat.format(calendar.getTime());
            mShortDayNames[i] = shortFormat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    public boolean is24hClock() {
        return m24hClock;
    }

    public String formatTime(Alarm alarm) {
        return mTimeFormat.format(new Date(alarm.getDate()));
    }

    public String formatDate(Alarm alarm) {
        return mDateFormat.format(new Date(alarm.getDate()));
    }

    public String formatDays(Alarm alarm) {
        boolean[] days = getDays(alarm);
        String res = "";

        if (alarm.getDays() == Alarm.NEVER)
            res = "Never";
        else if (alarm.getDays() == Alarm.EVERY_DAY)
            res = "Every day";
        else {
            for (int i = 0; i < 7; i++)
                if (days[i])
                    res += ("".equals(res)) ? mShortDayNames[i] : ", " + mShortDayNames[i];
        }

        return res;
    }

    public String formatDetails(Alarm alarm) {
        String res = "???";

        if (alarm.getOccurence() == Alarm.ONCE)
            res = formatDate(alarm);
        else if (alarm.getOccurence() == Alarm.WEEKLY)
            res = formatDays(alarm);

        res += ", " + formatTime(alarm);

        return res;
    }

    public boolean[] getDays(Alarm alarm) {
        int offs = mWeekStartsOnMonday ? 0 : 1;
        boolean[] rDays = new boolean[7];
        int aDays = alarm.getDays();

        for (int i = 0; i < 7; i++)
            rDays[(i + offs) % 7] = (aDays & (1 << i)) > 0;

        return rDays;
    }

    public void setDays(Alarm alarm, boolean[] days) {
        int offs = mWeekStartsOnMonday ? 0 : 1;
        int sDays = 0;

        for (int i = 0; i < 7; i++)
            sDays |= days[(i + offs) % 7] ? (1 << i) : (0);

        alarm.setDays(sDays);
    }

    public String[] getFullDayNames() {
        return mFullDayNames;
    }

    public String[] getShortDayNames() {
        return mShortDayNames;
    }
}
