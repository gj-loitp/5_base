package vn.puresolutions.livestarv3.utilities.old;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.puresolutions.livestar.R;

/**
 * @author Khanh Le
 * @version 1.0.0
 * @since 6/4/2015
 */
public class DateUtils {

    public static String convertFormatDate(String strDate, String fromFormat, String toFormat) {
        Date date = stringToDate(strDate, fromFormat);
        if (date != null) {
            DateFormat dateFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);
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
}
