package com.views.calendar.cosmocalendar.settings.selection;

import com.views.calendar.cosmocalendar.utils.SelectionType;

public interface SelectionInterface {

    @SelectionType
    int getSelectionType();

    void setSelectionType(@SelectionType int selectionType);
}
