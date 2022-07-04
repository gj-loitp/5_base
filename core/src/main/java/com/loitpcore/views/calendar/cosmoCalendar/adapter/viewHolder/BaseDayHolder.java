package com.loitpcore.views.calendar.cosmoCalendar.adapter.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView;

//21.12.2020 try to convert kotlin but failed
public abstract class BaseDayHolder extends RecyclerView.ViewHolder {

    protected TextView tvDay;
    protected CalendarView calendarView;

    public BaseDayHolder(View itemView, CalendarView calendarView) {
        super(itemView);
        this.calendarView = calendarView;
    }
}
