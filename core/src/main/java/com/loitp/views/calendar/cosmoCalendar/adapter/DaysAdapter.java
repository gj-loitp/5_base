package com.loitp.views.calendar.cosmoCalendar.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.loitp.views.calendar.cosmoCalendar.adapter.viewHolder.DayOfWeekHolder;
import com.loitp.views.calendar.cosmoCalendar.adapter.viewHolder.OtherDayHolder;
import com.loitp.views.calendar.cosmoCalendar.adapter.viewHolder.DayHolder;
import com.loitp.views.calendar.cosmoCalendar.model.Day;
import com.loitp.views.calendar.cosmoCalendar.model.Month;
import com.loitpcore.views.calendar.cosmoCalendar.utils.Constants;
import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView;
import com.loitpcore.views.calendar.cosmoCalendar.view.ItemViewType;
import com.loitpcore.views.calendar.cosmoCalendar.view.delegate.DayDelegate;
import com.loitpcore.views.calendar.cosmoCalendar.view.delegate.DayOfWeekDelegate;
import com.loitpcore.views.calendar.cosmoCalendar.view.delegate.OtherDayDelegate;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//21.12.2020 try to convert kotlin but failed
public class DaysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Month month;
    private final DayOfWeekDelegate dayOfWeekDelegate;
    private final DayDelegate dayDelegate;
    private final OtherDayDelegate otherDayDelegate;
    private final CalendarView calendarView;

    private DaysAdapter(
            Month month,
            DayOfWeekDelegate dayOfWeekDelegate,
            DayDelegate dayDelegate,
            OtherDayDelegate otherDayDelegate,
            CalendarView calendarView
    ) {
        setHasStableIds(false);
        this.month = month;
        this.dayOfWeekDelegate = dayOfWeekDelegate;
        this.dayDelegate = dayDelegate;
        this.otherDayDelegate = otherDayDelegate;
        this.calendarView = calendarView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < Constants.DAYS_IN_WEEK && calendarView.isShowDaysOfWeek()) {
            return ItemViewType.DAY_OF_WEEK;
        }
        if (month.getDays().get(position).isBelongToMonth()) {
            return ItemViewType.MONTH_DAY;
        } else {
            return ItemViewType.OTHER_MONTH_DAY;
        }
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemViewType.DAY_OF_WEEK:
                return dayOfWeekDelegate.onCreateDayHolder(parent, viewType);
            case ItemViewType.MONTH_DAY:
                return dayDelegate.onCreateDayHolder(parent, viewType);
            case ItemViewType.OTHER_MONTH_DAY:
                return otherDayDelegate.onCreateDayHolder(parent, viewType);
            default:
                throw new IllegalArgumentException("Unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Day day = month.getDays().get(position);
        switch (holder.getItemViewType()) {
            case ItemViewType.DAY_OF_WEEK:
                dayOfWeekDelegate.onBindDayHolder(day, (DayOfWeekHolder) holder, position);
                break;
            case ItemViewType.OTHER_MONTH_DAY:
                otherDayDelegate.onBindDayHolder(day, (OtherDayHolder) holder, position);
                break;
            case ItemViewType.MONTH_DAY:
                dayDelegate.onBindDayHolder(this, day, (DayHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return month == null ? 0 : month.getDays().size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMonth(Month month) {
        this.month = month;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return month.getDays().get(position).getCalendar().getTimeInMillis();
    }

    public static class DaysAdapterBuilder {

        private Month month;
        private DayOfWeekDelegate dayOfWeekDelegate;
        private DayDelegate dayDelegate;
        private OtherDayDelegate anotherDayDelegate;
        private CalendarView calendarView;

        public DaysAdapterBuilder setMonth(Month month) {
            this.month = month;
            return this;
        }

        public DaysAdapterBuilder setDayOfWeekDelegate(DayOfWeekDelegate dayOfWeekDelegate) {
            this.dayOfWeekDelegate = dayOfWeekDelegate;
            return this;
        }

        public DaysAdapterBuilder setDayDelegate(DayDelegate dayDelegate) {
            this.dayDelegate = dayDelegate;
            return this;
        }

        public DaysAdapterBuilder setOtherDayDelegate(OtherDayDelegate anotherDayDelegate) {
            this.anotherDayDelegate = anotherDayDelegate;
            return this;
        }

        public DaysAdapterBuilder setCalendarView(CalendarView calendarView) {
            this.calendarView = calendarView;
            return this;
        }

        public DaysAdapter createDaysAdapter() {
            return new DaysAdapter(month, dayOfWeekDelegate, dayDelegate, anotherDayDelegate, calendarView);
        }
    }
}
