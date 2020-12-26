package com.views.calendar.cosmocalendar.utils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//21.12.2020 try to convert kotlin but failed
@Retention(RetentionPolicy.SOURCE)
@IntDef({SelectionType.SINGLE, SelectionType.MULTIPLE, SelectionType.RANGE, SelectionType.NONE})
public @interface SelectionType {
    int SINGLE = 0;
    int MULTIPLE = 1;
    int RANGE = 2;
    int NONE = 3;
}
