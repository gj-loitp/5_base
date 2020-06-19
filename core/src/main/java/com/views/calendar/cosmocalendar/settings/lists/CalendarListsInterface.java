package com.views.calendar.cosmocalendar.settings.lists;

import com.views.calendar.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.views.calendar.cosmocalendar.settings.lists.connected_days.ConnectedDaysManager;

import java.util.Calendar;
import java.util.Set;

public interface CalendarListsInterface {

    Calendar getMinDate();

    Calendar getMaxDate();

    Set<Long> getDisabledDays();

    ConnectedDaysManager getConnectedDaysManager();

    Set<Long> getWeekendDays();

    DisabledDaysCriteria getDisabledDaysCriteria();

    void setMinDate(Calendar minDate);

    void setMaxDate(Calendar maxDate);

    void setDisabledDays(Set<Long> disabledDays);

    void setWeekendDays(Set<Long> weekendDays);

    void setDisabledDaysCriteria(DisabledDaysCriteria criteria);

    void addConnectedDays(ConnectedDays connectedDays);
}
