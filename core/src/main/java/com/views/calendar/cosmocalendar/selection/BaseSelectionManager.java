package com.views.calendar.cosmocalendar.selection;

import androidx.annotation.NonNull;

import com.views.calendar.cosmocalendar.model.Day;

//TODO convert kotlin
public abstract class BaseSelectionManager {

    protected OnDaySelectedListener onDaySelectedListener;

    public abstract void toggleDay(@NonNull Day day);

    public abstract boolean isDaySelected(@NonNull Day day);

    public abstract void clearSelections();

    public BaseSelectionManager() {
    }
}
