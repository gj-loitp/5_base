package com.views.calendar.cosmocalendar.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.views.calendar.cosmocalendar.adapter.MonthAdapter;
import com.views.calendar.cosmocalendar.adapter.viewholder.DayHolder;
import com.views.calendar.cosmocalendar.model.Day;
import com.views.calendar.cosmocalendar.selection.BaseSelectionManager;
import com.views.calendar.cosmocalendar.selection.MultipleSelectionManager;
import com.views.calendar.cosmocalendar.view.CalendarView;

//TODO convert kotlin
public class DayDelegate extends BaseDelegate {

    private MonthAdapter monthAdapter;

    public DayDelegate(CalendarView calendarView, MonthAdapter monthAdapter) {
        this.calendarView = calendarView;
        this.monthAdapter = monthAdapter;
    }

    public DayHolder onCreateDayHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cosmo_calendar_day, parent, false);
        return new DayHolder(view, calendarView);
    }

    public void onBindDayHolder(final RecyclerView.Adapter daysAdapter, final Day day,
                                final DayHolder holder, final int position) {
        final BaseSelectionManager selectionManager = monthAdapter.getSelectionManager();
        holder.bind(day, selectionManager);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!day.isDisabled()) {
                    selectionManager.toggleDay(day);
                    if (selectionManager instanceof MultipleSelectionManager) {
                        daysAdapter.notifyItemChanged(position);
                    } else {
                        monthAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
