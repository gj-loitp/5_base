package com.loitp.views.cal.cosmoCalendar.adapter.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.loitp.views.cal.cosmoCalendar.view.CalendarView;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//21.12.2020 try to convert kotlin but failed
public abstract class BaseDayHolder extends RecyclerView.ViewHolder {

    protected TextView tvDay;
    protected CalendarView calendarView;

    public BaseDayHolder(View itemView, CalendarView calendarView) {
        super(itemView);
        this.calendarView = calendarView;
    }
}
