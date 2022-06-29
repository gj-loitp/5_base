package com.loitpcore.views.calendar.cosmocalendar.selection.criteria;

import com.loitpcore.views.calendar.cosmocalendar.model.Day;

import java.util.Set;

//21.12.2020 try to convert kotlin but failed
public abstract class BaseCriteria {

    protected Set<Integer> weekendDays;
    protected Set<Integer> disabledDays;

    public abstract boolean isCriteriaPassed(Day day);

    public void setWeekendDays(Set<Integer> weekendDays) {
        this.weekendDays = weekendDays;
    }

    public void setDisabledDays(Set<Integer> disabledDays) {
        this.disabledDays = disabledDays;
    }
}
