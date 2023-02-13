package vn.loitp.up.a.picker.time

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker

class RangeTimePickerDialog(
    context: Context,
    callBack: OnTimeSetListener? = null,
    private var mCurrentHour: Int,
    private var mCurrentMinute: Int,
    is24HourView: Boolean = true
) : TimePickerDialog(context, callBack, mCurrentHour, mCurrentMinute, is24HourView) {

    private var mMinHour = -1
    private var mMinMinute = -1
    private var mMaxHour = 100
    private var mMaxMinute = 100

    fun setMin(
        hour: Int,
        minute: Int
    ) {
        mMinHour = hour
        mMinMinute = minute
    }

    fun setMax(
        hour: Int,
        minute: Int
    ) {
        mMaxHour = hour
        mMaxMinute = minute
    }

    override fun onTimeChanged(
        view: TimePicker,
        hourOfDay: Int,
        minute: Int
    ) {
        super.onTimeChanged(view, hourOfDay, minute)

        val validTime = (
                hourOfDay >= mMinHour &&
                        (hourOfDay != mMinHour || minute >= mMinMinute) &&
                        hourOfDay <= mMaxHour &&
                        (hourOfDay != mMaxHour || minute <= mMaxMinute)
                )
        if (validTime) {
            mCurrentHour = hourOfDay
            mCurrentMinute = minute
        } else {
            updateTime(mCurrentHour, mCurrentMinute)
        }
    }

    init {
        // Somehow the onTimeChangedListener is not set by TimePickerDialog
        // in some Android Versions, so, Adding the listener using
        // reflections
        try {
            val superclass: Class<*>? = javaClass.superclass
            if (superclass != null) {
                val mTimePickerField = superclass.getDeclaredField("mTimePicker")
                mTimePickerField.isAccessible = true
                val mTimePicker = mTimePickerField[this] as TimePicker
                mTimePicker.setOnTimeChangedListener(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
