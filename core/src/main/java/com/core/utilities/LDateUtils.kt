package com.core.utilities

import com.core.common.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object LDateUtils {
    private val TAG = LDateUtils::class.java.simpleName
    val currentDate: String
        get() {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("ddMM_HHmm")
            return df.format(c.time)
        }

    val currentYearMonth: String
        get() {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM")
            return df.format(c.time)
        }

    val currentMonth: String
        get() {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("MM")
            return df.format(c.time)
        }

    fun convertFormatDate(strDate: String, fromFormat: String, toFormat: String): String? {
        val date = stringToDate(strDate, fromFormat)
        if (date != null) {
            val simpleDateFormat = SimpleDateFormat(toFormat, Locale.ENGLISH)
            //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat.format(date)
        }
        return null
    }

    fun stringToDate(text: String, format: String): Date? {
        val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        return try {
            dateFormat.parse(text)
        } catch (e: ParseException) {
            null
        }

    }

    fun dateToString(date: Date, format: String): String? {
        val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        return try {
            dateFormat.format(date)
        } catch (e: Exception) {
            null
        }

    }

    /*public static String dateToString(Date date, Context context) {
        return dateToString(date, context.getString(R.string.date_format));
    }*/

    fun getDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

    fun formatDatePicker(year: Int, month: Int, day: Int, format: String): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = 0
        cal.set(year, month, day)
        val date = cal.time
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }

    fun getTime(hr: Int, min: Int): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hr)
        cal.set(Calendar.MINUTE, min)
        val formatter = SimpleDateFormat("h:mm a")
        return formatter.format(cal.time)
    }

    fun getDateWithoutTime(dateString: String): String {
        return getDate(dateString, "dd/MM/yyyy")
    }

    @JvmOverloads
    fun getDate(dateString: String, format: String = "dd/MM/yyyy hh:mm aa"): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        var value: Date? = null
        try {
            value = formatter.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val dateFormatter = SimpleDateFormat(format, Locale.ENGLISH)
        dateFormatter.timeZone = TimeZone.getDefault()
        return dateFormatter.format(value)
    }

    //date ex: 14-09-2017
    fun convertDateToTimestamp(d: String): Long {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val date: Date?
        return try {
            date = formatter.parse(d) as Date
            date.time / 1000
        } catch (e: ParseException) {
            LLog.d(TAG, "convertDateToTimestamp ParseException $e")
            Constants.NOT_FOUND.toLong()
        }

    }

    fun zeroTime(date: Date): Date {
        return setTime(date, 0, 0, 0, 0)
    }

    fun setTime(date: Date, hourOfDay: Int, minute: Int, second: Int, ms: Int): Date {
        val gc = GregorianCalendar()
        gc.time = date
        gc.set(Calendar.HOUR_OF_DAY, hourOfDay)
        gc.set(Calendar.MINUTE, minute)
        gc.set(Calendar.SECOND, second)
        gc.set(Calendar.MILLISECOND, ms)
        return gc.time
    }

    private fun convertDateToTimeStamp(d: String, h: Int, m: Int): Long {
        //String lstart = dd + "/" + mm + "/" + yyyy + " " + h + ":" + m;
        val lstart = "$d $h:$m"
        val format = SimpleDateFormat("dd/MM/yyyy hh:mm")
        return try {
            val date = format.parse(lstart)
            date.time / 1000
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }

    }

    fun convertDateToTimeStamp(datetime: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date?
        return try {
            date = dateFormat.parse(datetime)
            val time = date!!.time
            LLog.d(TAG, "time:$time")
            time
            //new Timestamp(time).getTime();
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }

    }

    fun getDateFromDateTime(datetime: String): String {
        val date = datetime.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return date[0]
    }

    fun convertStringToCalendar(yyyymmdd: String): Calendar {
        val df = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        val date: Date?
        try {
            date = df.parse(yyyymmdd)
            cal.time = date
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return cal
    }

    fun convertStringDate(yyyymmdd: String, format: String): Calendar {
        val df = SimpleDateFormat(format)
        val cal = Calendar.getInstance()
        val date: Date?
        try {
            date = df.parse(yyyymmdd)
            cal.time = date
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return cal
    }

    /*
     * getDateCurrentTimeZone("1524141369", "yyyy-MM-dd HH:mm:ss");
     * -> 2018-04-20 02:36:09
     */
    fun getDateCurrentTimeZone(timestamp: Long, format: String): String {
        return try {
            val calendar = Calendar.getInstance()
            //TimeZone tz = TimeZone.getDefault();
            val tz = TimeZone.getTimeZone("UTC")
            calendar.timeInMillis = timestamp * 1000
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
            val sdf = SimpleDateFormat(format)
            val currenTimeZone = calendar.time as Date
            sdf.format(currenTimeZone)
        } catch (e: Exception) {
            ""
        }
    }

    /*
     * getDateCurrentTimeZone(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
     * -> 2018-04-20 02:36:09
     */
    fun getDateCurrentTimeZoneMls(timestampMls: Long, format: String): String {
        //LLog.d(TAG, "getDateCurrentTimeZoneMls " + timestampMls);
        return try {
            val calendar = Calendar.getInstance()
            //TimeZone tz = TimeZone.getDefault();
            val tz = TimeZone.getTimeZone("UTC")
            calendar.timeInMillis = timestampMls
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
            val sdf = SimpleDateFormat(format)
            val currenTimeZone = calendar.time as Date
            sdf.format(currenTimeZone)
        } catch (e: Exception) {
            ""
        }
    }

    fun convertDate(dateInMilliseconds: String, dateFormat: String): String? {
        return try {
            val timeStamp = java.lang.Long.parseLong(dateInMilliseconds)
            val objFormatter = SimpleDateFormat(dateFormat)
            objFormatter.timeZone = TimeZone.getDefault()
            val objCalendar = Calendar.getInstance(TimeZone.getDefault())
            objCalendar.timeInMillis = timeStamp * 1000
            val result = objFormatter.format(objCalendar.time)
            objCalendar.clear()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    fun convertSToFormat(second: Long, format: String): String? {
        val d = Date(second * 1000L)
        val df = SimpleDateFormat(format)
        df.timeZone = TimeZone.getTimeZone("GMT")
        val time = df.format(d)
        return time
    }
}
