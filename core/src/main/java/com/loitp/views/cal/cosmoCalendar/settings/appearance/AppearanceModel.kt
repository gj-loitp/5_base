package com.loitp.views.cal.cosmoCalendar.settings.appearance

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AppearanceModel : AppearanceInterface {
    // Background color of whole calendar
    override var calendarBackgroundColor = 0
    override var isShowFlBottomSelectionBar = false

    // Text color of month title
    override var monthTextColor = 0

    // Text color of day that month doesn't include
    override var otherDayTextColor = 0

    // Text color of day
    override var dayTextColor = 0

    // Text color of weekend day (ex. Saturday, Sunday)
    override var weekendDayTextColor = 0

    // Text color of week day titles
    override var weekDayTitleTextColor = 0

    // Text color of selected days
    override var selectedDayTextColor = 0

    // Background color of selected days
    override var selectedDayBackgroundColor = 0

    // Background color of START day from selected range
    override var selectedDayBackgroundStartColor = 0

    // Background color of END day from selected range
    override var selectedDayBackgroundEndColor = 0

    // Text color of current day
    override var currentDayTextColor = 0

    // Icon resource of current day
    override var currentDayIconRes = 0

    // Icon resource of current day selected
    override var currentDaySelectedIconRes = 0

    // Icon resource of connected day
    override var connectedDayIconRes = 0

    // Icon resource of connected day selected
    override var connectedDaySelectedIconRes = 0

    // Position of connected day icon (TOP/BOTTOM)
    override var connectedDayIconPosition = 0

    // Text color of disabled day
    override var disabledDayTextColor = 0

    // Text color of month titles in selection bar
    override var selectionBarMonthTextColor = 0

    // Icon resource of previous month navigation button
    override var previousMonthIconRes = 0

    // Icon resource of next month navigation button
    override var nextMonthIconRes = 0

    /**
     * Orientation of calendar
     * possible values:
     * HORIZONTAL - left/right swipable months, navigation buttons
     * VERTICAL - top/bottom swipable months
     */
    override var calendarOrientation = 0

    // Defines if we need to display week day titles for every month
    override var isShowDaysOfWeek = false

    // Defines if we need to display week day title for whole calendar
    override var isShowDaysOfWeekTitle = false
}
