package com.loitp.views.cal.cosmoCalendar.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.loitp.views.cal.cosmoCalendar.selection.SelectionState;
import com.loitp.views.cal.cosmoCalendar.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//21.12.2020 try to convert kotlin but failed
@Keep
public class Day {

    private final Calendar calendar;
    private boolean belongToMonth;
    private boolean current;
    private boolean selected;
    private boolean disabled;
    private boolean weekend;

    //Connected days
    private boolean fromConnectedCalendar;
    private int connectedDaysTextColor;
    private int connectedDaysSelectedTextColor;
    private int connectedDaysDisabledTextColor;

    //For animation states
    private SelectionState selectionState;
    private boolean isSelectionCircleDrawed;

    public Day(Date date) {
        this.calendar = DateUtils.INSTANCE.getCalendar(date);
        this.current = DateUtils.isCurrentDate(date);
        this.selected = false;
    }

    public Day(Calendar calendar) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(calendar.getTime());
        this.calendar = tempCalendar;
        this.current = DateUtils.isCurrentDate(calendar.getTime());
        this.selected = false;
    }

    public boolean isBelongToMonth() {
        return belongToMonth;
    }

    public void setBelongToMonth(boolean belongToMonth) {
        this.belongToMonth = belongToMonth;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }

    public boolean isFromConnectedCalendar() {
        return fromConnectedCalendar;
    }

    public void setFromConnectedCalendar(boolean fromConnectedCalendar) {
        this.fromConnectedCalendar = fromConnectedCalendar;
    }

    public boolean isSelectionCircleDrawed() {
        return isSelectionCircleDrawed;
    }

    public void setSelectionCircleDrawed(boolean selectionCircleDrawed) {
        isSelectionCircleDrawed = selectionCircleDrawed;
    }

    public SelectionState getSelectionState() {
        return selectionState;
    }

    public void setSelectionState(SelectionState selectionState) {
        this.selectionState = selectionState;
    }

    public int getConnectedDaysTextColor() {
        return connectedDaysTextColor;
    }

    public void setConnectedDaysTextColor(int connectedDaysTextColor) {
        this.connectedDaysTextColor = connectedDaysTextColor;
    }

    public int getConnectedDaysSelectedTextColor() {
        return connectedDaysSelectedTextColor;
    }

    public void setConnectedDaysSelectedTextColor(int connectedDaysSelectedTextColor) {
        this.connectedDaysSelectedTextColor = connectedDaysSelectedTextColor;
    }

    public int getConnectedDaysDisabledTextColor() {
        return connectedDaysDisabledTextColor;
    }

    public void setConnectedDaysDisabledTextColor(int connectedDaysDisabledTextColor) {
        this.connectedDaysDisabledTextColor = connectedDaysDisabledTextColor;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getDayNumber() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @NonNull
    @Override
    public String toString() {
        return "Day{day=" + calendar.getTime() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Day day = (Day) o;
        Calendar anotherCalendar = day.getCalendar();
        return anotherCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                anotherCalendar.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public int hashCode() {
        return calendar != null ? calendar.hashCode() : 0;
    }
}
