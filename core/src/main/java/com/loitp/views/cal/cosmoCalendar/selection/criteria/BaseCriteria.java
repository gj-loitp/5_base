package com.loitp.views.cal.cosmoCalendar.selection.criteria;

import com.loitp.views.cal.cosmoCalendar.model.Day;

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
public abstract class BaseCriteria {

    protected Set<Integer> weekendDays;
    protected Set<Integer> disabledDays;
    public abstract boolean isCriteriaPassed(Day day);

    @Suppress(names = "unused")
    public void setWeekendDays(Set<Integer> weekendDays) {
        this.weekendDays = weekendDays;
    }

    @Suppress(names = "unused")
    public void setDisabledDays(Set<Integer> disabledDays) {
        this.disabledDays = disabledDays;
    }
}
