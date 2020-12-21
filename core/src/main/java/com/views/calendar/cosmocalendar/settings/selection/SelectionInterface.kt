package com.views.calendar.cosmocalendar.settings.selection;

import com.views.calendar.cosmocalendar.utils.SelectionType;

//TODO convert kotlin
public interface SelectionInterface {

    @SelectionType
    int getSelectionType();

    void setSelectionType(@SelectionType int selectionType);
}
