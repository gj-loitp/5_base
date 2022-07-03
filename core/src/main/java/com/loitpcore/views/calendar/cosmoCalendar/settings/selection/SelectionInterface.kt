package com.loitpcore.views.calendar.cosmoCalendar.settings.selection

import com.loitpcore.views.calendar.cosmoCalendar.utils.SelectionType

interface SelectionInterface {
    @get:SelectionType
    var selectionType: Int
}
