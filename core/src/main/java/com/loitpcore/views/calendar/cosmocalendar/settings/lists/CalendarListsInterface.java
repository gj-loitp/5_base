package com.loitpcore.views.calendar.cosmocalendar.settings.lists;

import com.loitpcore.views.calendar.cosmocalendar.settings.lists.connectedDays.ConnectedDays;
import com.loitpcore.views.calendar.cosmocalendar.settings.lists.connectedDays.ConnectedDaysManager;

import java.util.Calendar;
import java.util.Set;

//21.12.2020 try to convert kotlin but failed
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
