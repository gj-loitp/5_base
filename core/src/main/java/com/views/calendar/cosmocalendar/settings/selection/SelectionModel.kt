package com.views.calendar.cosmocalendar.settings.selection

import com.views.calendar.cosmocalendar.utils.SelectionType

class SelectionModel : SelectionInterface {
    // Selecton type SINGLE, MULTIPLE, RANGE, NONE
    @get:SelectionType
    @SelectionType
    override var selectionType = 0
}
