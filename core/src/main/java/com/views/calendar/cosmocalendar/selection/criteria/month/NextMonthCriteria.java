package com.views.calendar.cosmocalendar.selection.criteria.month;

import com.views.calendar.cosmocalendar.utils.DateUtils;

import java.util.Calendar;

public class NextMonthCriteria extends BaseMonthCriteria {

    private long currentTimeInMillis;

    public NextMonthCriteria() {
        currentTimeInMillis = System.currentTimeMillis();
    }

    @Override
    protected int getMonth() {
        Calendar calendar = DateUtils.getCalendar(currentTimeInMillis);
        calendar.add(Calendar.MONTH, 1);
        return calendar.get(Calendar.MONTH);
    }

    @Override
    protected int getYear() {
        Calendar calendar = DateUtils.getCalendar(currentTimeInMillis);
        calendar.add(Calendar.MONTH, 1);
        return calendar.get(Calendar.YEAR);
    }
}
