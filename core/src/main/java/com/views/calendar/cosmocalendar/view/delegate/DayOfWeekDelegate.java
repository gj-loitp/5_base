package com.views.calendar.cosmocalendar.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.R;
import com.views.calendar.cosmocalendar.adapter.viewholder.DayOfWeekHolder;
import com.views.calendar.cosmocalendar.model.Day;
import com.views.calendar.cosmocalendar.view.CalendarView;

//TODO convert kotlin
public class DayOfWeekDelegate extends BaseDelegate {

    public DayOfWeekDelegate(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public DayOfWeekHolder onCreateDayHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cosmo_calendar_day_of_week, parent, false);
        return new DayOfWeekHolder(view, calendarView);
    }

    public void onBindDayHolder(Day day, DayOfWeekHolder holder, int position) {
        holder.bind(day);
    }
}
