package com.loitpcore.views.calendar.cosmocalendar.settings.appearance

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
