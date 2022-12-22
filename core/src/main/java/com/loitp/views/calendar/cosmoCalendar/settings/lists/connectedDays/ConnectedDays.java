package com.loitp.views.calendar.cosmoCalendar.settings.lists.connectedDays;

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
public class ConnectedDays {

    private Set<Long> days;

    private int textColor;
    private int selectedTextColor;
    private int disabledTextColor;

    public ConnectedDays(Set<Long> days, int textColor, int selectedTextColor, int disabledTextColor) {
        this.days = days;
        this.textColor = textColor;
        this.selectedTextColor = selectedTextColor;
        this.disabledTextColor = disabledTextColor;
    }

    @Suppress(names = "unused")
    public ConnectedDays(Set<Long> days, int textColor, int selectedTextColor) {
        this(days, textColor, selectedTextColor, textColor);
    }

    @Suppress(names = "unused")
    public ConnectedDays(Set<Long> days, int textColor) {
        this(days, textColor, textColor, textColor);
    }

    public Set<Long> getDays() {
        return days;
    }

    public void setDays(Set<Long> days) {
        this.days = days;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    @Suppress(names = "unused")
    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public int getDisabledTextColor() {
        return disabledTextColor;
    }

    @Suppress(names = "unused")
    public void setDisabledTextColor(int disabledTextColor) {
        this.disabledTextColor = disabledTextColor;
    }
}
