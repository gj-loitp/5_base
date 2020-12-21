package com.views.calendar.cosmocalendar.selection;

import androidx.annotation.NonNull;

import com.views.calendar.cosmocalendar.model.Day;

//TODO convert kotlin
public class NoneSelectionManager extends BaseSelectionManager {

    @Override
    public void toggleDay(@NonNull Day day) {

    }

    @Override
    public boolean isDaySelected(@NonNull Day day) {
        return false;
    }

    @Override
    public void clearSelections() {

    }
}
