package com.loitp.views.cal.cosmoCalendar.settings.appearance

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface AppearanceInterface {
    var calendarBackgroundColor: Int
    var monthTextColor: Int
    var otherDayTextColor: Int
    var dayTextColor: Int
    var weekendDayTextColor: Int
    var weekDayTitleTextColor: Int
    var selectedDayTextColor: Int
    var selectedDayBackgroundColor: Int
    var selectedDayBackgroundStartColor: Int
    var selectedDayBackgroundEndColor: Int
    var currentDayTextColor: Int
    var currentDayIconRes: Int
    var currentDaySelectedIconRes: Int
    var calendarOrientation: Int
    var connectedDayIconRes: Int
    var connectedDaySelectedIconRes: Int
    var connectedDayIconPosition: Int
    var disabledDayTextColor: Int
    var selectionBarMonthTextColor: Int
    var previousMonthIconRes: Int
    var nextMonthIconRes: Int
    var isShowDaysOfWeek: Boolean
    var isShowDaysOfWeekTitle: Boolean
    var isShowFlBottomSelectionBar: Boolean
}
