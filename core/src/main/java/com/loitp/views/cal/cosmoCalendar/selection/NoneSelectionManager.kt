package com.loitp.views.cal.cosmoCalendar.selection

import com.loitp.views.cal.cosmoCalendar.model.Day

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class NoneSelectionManager : BaseSelectionManager() {

    override fun toggleDay(day: Day) {}

    override fun isDaySelected(day: Day): Boolean {
        return false
    }

    override fun clearSelections() {}
}
