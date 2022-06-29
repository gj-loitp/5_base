package com.loitpcore.views.calendar.cosmocalendar.settings.selection

import com.loitpcore.views.calendar.cosmocalendar.utils.SelectionType

interface SelectionInterface {
    @get:SelectionType
    var selectionType: Int
}
