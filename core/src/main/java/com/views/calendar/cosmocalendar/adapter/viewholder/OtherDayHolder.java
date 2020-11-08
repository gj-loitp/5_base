package com.views.calendar.cosmocalendar.adapter.viewholder;

import android.view.View;

import com.R;
import com.views.calendar.cosmocalendar.model.Day;
import com.views.calendar.cosmocalendar.view.CalendarView;

//TODO convert kotlin
public class OtherDayHolder extends BaseDayHolder {

    public OtherDayHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        tvDay = itemView.findViewById(R.id.tv_day_number);
    }

    public void bind(Day day) {
        tvDay.setText(String.valueOf(day.getDayNumber()));
        tvDay.setTextColor(calendarView.getOtherDayTextColor());
    }
}
