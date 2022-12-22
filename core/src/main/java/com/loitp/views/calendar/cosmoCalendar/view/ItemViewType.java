package com.loitp.views.calendar.cosmoCalendar.view;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//20.12.2020 try to convert kotlin but failed
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        ItemViewType.MONTH,
        ItemViewType.MONTH_DAY,
        ItemViewType.OTHER_MONTH_DAY,
        ItemViewType.DAY_OF_WEEK
})
public @interface ItemViewType {
    int MONTH = 0;
    int MONTH_DAY = 1;
    int OTHER_MONTH_DAY = 2;
    int DAY_OF_WEEK = 3;
}
