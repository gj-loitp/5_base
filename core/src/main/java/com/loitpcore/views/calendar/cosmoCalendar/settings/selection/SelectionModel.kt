package com.loitpcore.views.calendar.cosmoCalendar.settings.selection

import com.loitpcore.views.calendar.cosmoCalendar.utils.SelectionType

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SelectionModel : SelectionInterface {
    // Selection type SINGLE, MULTIPLE, RANGE, NONE
    @get:SelectionType
    @SelectionType
    override var selectionType = 0
}
