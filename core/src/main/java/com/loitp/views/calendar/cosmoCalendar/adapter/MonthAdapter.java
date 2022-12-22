package com.loitp.views.calendar.cosmoCalendar.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.loitp.views.calendar.cosmoCalendar.adapter.viewHolder.MonthHolder;
import com.loitp.views.calendar.cosmoCalendar.model.Day;
import com.loitp.views.calendar.cosmoCalendar.model.Month;
import com.loitpcore.views.calendar.cosmoCalendar.selection.BaseSelectionManager;
import com.loitpcore.views.calendar.cosmoCalendar.settings.lists.DisabledDaysCriteria;
import com.loitpcore.views.calendar.cosmoCalendar.utils.CalendarUtils;
import com.loitpcore.views.calendar.cosmoCalendar.utils.DayFlag;
import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView;
import com.loitpcore.views.calendar.cosmoCalendar.view.ItemViewType;
import com.loitpcore.views.calendar.cosmoCalendar.view.delegate.DayDelegate;
import com.loitpcore.views.calendar.cosmoCalendar.view.delegate.DayOfWeekDelegate;
import com.loitpcore.views.calendar.cosmoCalendar.view.delegate.MonthDelegate;
import com.loitpcore.views.calendar.cosmoCalendar.view.delegate.OtherDayDelegate;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//21.12.2020 try to convert kotlin but failed
public class MonthAdapter extends RecyclerView.Adapter<MonthHolder> {

    private final List<Month> months;
    private final MonthDelegate monthDelegate;
    private final CalendarView calendarView;
    private BaseSelectionManager selectionManager;

    private MonthAdapter(
            List<Month> months,
            MonthDelegate monthDelegate,
            CalendarView calendarView,
            BaseSelectionManager selectionManager
    ) {
        setHasStableIds(true);
        this.months = months;
        this.monthDelegate = monthDelegate;
        this.calendarView = calendarView;
        this.selectionManager = selectionManager;
    }

    public void setSelectionManager(BaseSelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    public BaseSelectionManager getSelectionManager() {
        return selectionManager;
    }

    @NotNull
    @Override
    public MonthHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        DaysAdapter daysAdapter = new DaysAdapter.DaysAdapterBuilder()
                .setDayOfWeekDelegate(new DayOfWeekDelegate(calendarView))
                .setOtherDayDelegate(new OtherDayDelegate(calendarView))
                .setDayDelegate(new DayDelegate(calendarView, this))
                .setCalendarView(calendarView)
                .createDaysAdapter();
        return monthDelegate.onCreateMonthHolder(daysAdapter, parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NotNull MonthHolder holder, int position) {
        final Month month = months.get(position);
        monthDelegate.onBindMonthHolder(month, holder, position);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ItemViewType.MONTH;
    }

    @Override
    public long getItemId(int position) {
        return months.get(position).getFirstDay().getCalendar().getTimeInMillis();
    }

    public List<Month> getData() {
        return months;
    }

    public static class MonthAdapterBuilder {

        private List<Month> months;
        private MonthDelegate monthDelegate;
        private CalendarView calendarView;
        private BaseSelectionManager selectionManager;

        public MonthAdapterBuilder setMonths(List<Month> months) {
            this.months = months;
            return this;
        }

        public MonthAdapterBuilder setMonthDelegate(MonthDelegate monthHolderDelegate) {
            this.monthDelegate = monthHolderDelegate;
            return this;
        }

        public MonthAdapterBuilder setCalendarView(CalendarView calendarView) {
            this.calendarView = calendarView;
            return this;
        }

        public MonthAdapterBuilder setSelectionManager(BaseSelectionManager selectionManager) {
            this.selectionManager = selectionManager;
            return this;
        }

        public MonthAdapter createMonthAdapter() {
            return new MonthAdapter(months,
                    monthDelegate,
                    calendarView,
                    selectionManager);
        }
    }

    public void setWeekendDays(Set<Long> weekendDays) {
        setDaysAccordingToSet(weekendDays, DayFlag.WEEKEND);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMinDate(Calendar minDate) {
        for (Month month : months) {
            for (Day day : month.getDays()) {
                if (!day.isDisabled()) {
                    day.setDisabled(CalendarUtils.isDayDisabledByMinDate(day, minDate));
                }
            }
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMaxDate(Calendar maxDate) {
        for (Month month : months) {
            for (Day day : month.getDays()) {
                if (!day.isDisabled()) {
                    day.setDisabled(CalendarUtils.isDayDisabledByMaxDate(day, maxDate));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setDisabledDays(Set<Long> disabledDays) {
        setDaysAccordingToSet(disabledDays, DayFlag.DISABLED);
    }

    @Suppress(names = "unused")
    public void setConnectedCalendarDays(Set<Long> connectedCalendarDays) {
        setDaysAccordingToSet(connectedCalendarDays, DayFlag.FROM_CONNECTED_CALENDAR);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDisabledDaysCriteria(DisabledDaysCriteria criteria) {
        for (Month month : months) {
            for (Day day : month.getDays()) {
                if (!day.isDisabled()) {
                    day.setDisabled(CalendarUtils.isDayDisabledByCriteria(day, criteria));
                }
            }
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setDaysAccordingToSet(Set<Long> days, DayFlag dayFlag) {
        if (days != null && !days.isEmpty()) {
            for (Month month : months) {
                for (Day day : month.getDays()) {
                    switch (dayFlag) {
                        case WEEKEND:
                            day.setWeekend(days.contains(day.getCalendar().get(Calendar.DAY_OF_WEEK)));
                            break;

                        case DISABLED:
                            day.setDisabled(CalendarUtils.isDayInSet(day, days));
                            break;

                        case FROM_CONNECTED_CALENDAR:
                            day.setFromConnectedCalendar(CalendarUtils.isDayInSet(day, days));
                            break;
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
