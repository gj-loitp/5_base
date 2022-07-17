package com.loitpcore.views.calendar.cosmoCalendar.settings.selection

import com.loitpcore.views.calendar.cosmoCalendar.utils.SelectionType

class SelectionModel : SelectionInterface {
    // Selecton type SINGLE, MULTIPLE, RANGE, NONE
    @get:SelectionType
    @SelectionType
    override var selectionType = 0
}
