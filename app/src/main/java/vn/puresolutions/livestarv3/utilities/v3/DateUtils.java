package vn.puresolutions.livestarv3.utilities.v3;

import android.content.Context;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.puresolutions.livestar.R;

/**
 * @author Khanh Le
 * @version 1.0.0
 * @since 6/4/2015
 */
public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();

    public static String convertFormatDate(String strDate, String fromFormat, String toFormat) {
        Date date = stringToDate(strDate, fromFormat);
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);
            //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            DateFormat dateFormat = simpleDateFormat;
            return dateFormat.format(date);
        }
        return null;
    }

    public static Date stringToDate(String text, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return dateFormat.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToString(Date date, Context context) {
        return dateToString(date, context.getString(R.string.date_format));
    }

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public static String formatDatePicker(int year, int month, int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getTime(int hr, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hr);
        cal.set(Calendar.MINUTE, min);
        Format formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(cal.getTime());
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMM_HHmm");
        return df.format(c.getTime());
    }

    public static List<String> genListDayOfMonth(int month) {
        List<String> stringList = new ArrayList<>();
        if (month > 0 && month < 13) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, 0);
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            SimpleDateFormat df = new SimpleDateFormat("EEE*dd/MM");
            for (int i = 0; i < maxDay; i++) {
                cal.set(Calendar.DAY_OF_MONTH, i + 1);
                LLog.d(TAG, df.format(cal.getTime()));
                stringList.add(df.format(cal.getTime()).replace("*", "\n"));
            }
        }
        return stringList;
    }
}
