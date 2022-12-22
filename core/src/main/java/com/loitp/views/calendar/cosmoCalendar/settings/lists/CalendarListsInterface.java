package com.loitp.views.calendar.cosmoCalendar.settings.lists;

import com.loitp.views.calendar.cosmoCalendar.settings.lists.connectedDays.ConnectedDaysManager;
import com.loitp.views.calendar.cosmoCalendar.settings.lists.connectedDays.ConnectedDays;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
