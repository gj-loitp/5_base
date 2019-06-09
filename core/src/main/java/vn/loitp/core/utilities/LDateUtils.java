package vn.loitp.core.utilities;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import vn.loitp.core.common.Constants;

/**
 * @author Khanh Le
 * @version 1.0.0
 * @since 6/4/2015
 */
public class LDateUtils {
    private static final String TAG = LDateUtils.class.getSimpleName();

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

    /*public static String dateToString(Date date, Context context) {
        return dateToString(date, context.getString(R.string.date_format));
    }*/

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

    public static String getCurrentYearMonth() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(c.getTime());
    }

    public static String getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM");
        return df.format(c.getTime());
    }

    public static String getDate(String dateString) {
        return getDate(dateString, "dd/MM/yyyy hh:mm aa");
    }

    public static String getDateWithoutTime(String dateString) {
        return getDate(dateString, "dd/MM/yyyy");
    }

    public static String getDate(String dateString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        try {
            value = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.ENGLISH);
        dateFormatter.setTimeZone(TimeZone.getDefault());
        return dateFormatter.format(value);
    }

    //date ex: 14-09-2017
    public static long convertDateToTimestamp(String d) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = (Date) formatter.parse(d);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            LLog.INSTANCE.d(TAG, "convertDateToTimestamp ParseException " + e.toString());
            return Constants.INSTANCE.getNOT_FOUND();
        }
    }

    public static Date zeroTime(final Date date) {
        return setTime(date, 0, 0, 0, 0);
    }

    public static Date setTime(final Date date, final int hourOfDay, final int minute, final int second, final int ms) {
        final GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.HOUR_OF_DAY, hourOfDay);
        gc.set(Calendar.MINUTE, minute);
        gc.set(Calendar.SECOND, second);
        gc.set(Calendar.MILLISECOND, ms);
        return gc.getTime();
    }

    private static long convertDateToTimeStamp(String d, int h, int m) {
        //String lstart = dd + "/" + mm + "/" + yyyy + " " + h + ":" + m;
        String lstart = d + " " + h + ":" + m;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        try {
            Date date = format.parse(lstart);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long convertDateToTimeStamp(String datetime) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = dateFormat.parse(datetime);
            long time = date.getTime();
            LLog.INSTANCE.d(TAG, "time:" + time);
            return time;
            //new Timestamp(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDateFromDateTime(String datetime) {
        String[] date = datetime.split(" ");
        return date[0];
    }

    public static Calendar convertStringToCalendar(String yyyymmdd) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = df.parse(yyyymmdd);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static Calendar convertStringDate(String yyyymmdd, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = df.parse(yyyymmdd);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    /*
     * getDateCurrentTimeZone("1524141369", "yyyy-MM-dd HH:mm:ss");
     * -> 2018-04-20 02:36:09
     */
    public static String getDateCurrentTimeZone(long timestamp, String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            //TimeZone tz = TimeZone.getDefault();
            TimeZone tz = TimeZone.getTimeZone("UTC");
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    /*
     * getDateCurrentTimeZone(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
     * -> 2018-04-20 02:36:09
     */
    public static String getDateCurrentTimeZoneMls(long timestampMls, String format) {
        //LLog.d(TAG, "getDateCurrentTimeZoneMls " + timestampMls);
        try {
            Calendar calendar = Calendar.getInstance();
            //TimeZone tz = TimeZone.getDefault();
            TimeZone tz = TimeZone.getTimeZone("UTC");
            calendar.setTimeInMillis(timestampMls);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    public static String convertDate(String dateInMilliseconds, String dateFormat) {
        try {
            long timeStamp = Long.parseLong(dateInMilliseconds);
            SimpleDateFormat objFormatter = new SimpleDateFormat(dateFormat);
            objFormatter.setTimeZone(TimeZone.getDefault());
            Calendar objCalendar = Calendar.getInstance(TimeZone.getDefault());
            objCalendar.setTimeInMillis(timeStamp * 1000);
            String result = objFormatter.format(objCalendar.getTime());
            objCalendar.clear();
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
