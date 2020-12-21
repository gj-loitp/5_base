package com.views.calendar.cosmocalendar.adapter.viewholder;

import android.view.View;

import com.R;
import com.views.calendar.cosmocalendar.model.Day;
import com.views.calendar.cosmocalendar.utils.Constants;
import com.views.calendar.cosmocalendar.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Locale;

//TODO convert kotlin
public class DayOfWeekHolder extends BaseDayHolder {

    private SimpleDateFormat mDayOfWeekFormatter;

    public DayOfWeekHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        tvDay = itemView.findViewById(R.id.tv_day_name);
        mDayOfWeekFormatter = new SimpleDateFormat(Constants.DAY_NAME_FORMAT, Locale.getDefault());
    }

    public void bind(Day day) {
        tvDay.setText(mDayOfWeekFormatter.format(day.getCalendar().getTime()));
        tvDay.setTextColor(calendarView.getWeekDayTitleTextColor());
    }
}
