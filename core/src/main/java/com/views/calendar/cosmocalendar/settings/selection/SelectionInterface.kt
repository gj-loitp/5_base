package com.views.calendar.cosmocalendar.settings.selection

import com.views.calendar.cosmocalendar.utils.SelectionType

interface SelectionInterface {
    @get:SelectionType
    var selectionType: Int
}
