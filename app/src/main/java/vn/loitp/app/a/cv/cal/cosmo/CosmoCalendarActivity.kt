package vn.loitp.app.a.cv.cal.cosmo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.OrientationHelper
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFontActivity
import com.loitp.views.cal.cosmoCalendar.model.Day
import com.loitp.views.cal.cosmoCalendar.selection.MultipleSelectionManager
import com.loitp.views.cal.cosmoCalendar.selection.OnDaySelectedListener
import com.loitp.views.cal.cosmoCalendar.selection.RangeSelectionManager
import com.loitp.views.cal.cosmoCalendar.selection.criteria.BaseCriteria
import com.loitp.views.cal.cosmoCalendar.selection.criteria.WeekDayCriteria
import com.loitp.views.cal.cosmoCalendar.selection.criteria.month.CurrentMonthCriteria
import com.loitp.views.cal.cosmoCalendar.selection.criteria.month.NextMonthCriteria
import com.loitp.views.cal.cosmoCalendar.selection.criteria.month.PreviousMonthCriteria
import com.loitp.views.cal.cosmoCalendar.utils.SelectionType
import kotlinx.android.synthetic.main.activity_cosmo_calendar.*
import vn.loitp.R
import java.util.*

// https://github.com/ApplikeySolutions/CosmoCalendar
// set color o colors.xml region cosmo calendar
@LogTag("CosmoCalendarActivity")
@IsFullScreen(false)
class CosmoCalendarActivity : BaseFontActivity(), RadioGroup.OnCheckedChangeListener {
    private var threeMonthsCriteriaList =
        ArrayList<BaseCriteria>()
    private var fridayCriteria: WeekDayCriteria? = null

    private var fridayCriteriaEnabled: Boolean = false
    private var threeMonthsCriteriaEnabled: Boolean = false

    private lateinit var menuFridays: MenuItem
    private lateinit var menuThreeMonth: MenuItem

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_cosmo_calendar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        initViews()
        createCriterias()

        // add min date and max date
        /*val c = Calendar.getInstance()
        calendarView.minDate = c

        c.add(Calendar.DATE, 30)
        calendarView.maxDate = c*/

        // calendarView.currentDayIconRes = R.drawable.circle
    }

    private fun initViews() {
        rgShowHideFlBottomSelectionBar.setOnCheckedChangeListener(this)
        rgOrientation.setOnCheckedChangeListener(this)
        rgSelectionType.setOnCheckedChangeListener(this)
    }

    private fun createCriterias() {
        fridayCriteria = WeekDayCriteria(Calendar.FRIDAY)

        threeMonthsCriteriaList.add(CurrentMonthCriteria())
        threeMonthsCriteriaList.add(NextMonthCriteria())
        threeMonthsCriteriaList.add(PreviousMonthCriteria())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_calendar_cosmo, menu)
        menuFridays = menu.findItem(R.id.itemSelectAllFridays)
        menuThreeMonth = menu.findItem(R.id.itemSelectThreeMonths)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemSelectAllFridays -> {
                fridayMenuClick()
                return true
            }
            R.id.itemSelectThreeMonths -> {
                threeMonthsMenuClick()
                return true
            }
            R.id.itemClearSelections -> {
                clearSelectionsMenuClick()
                return true
            }
            R.id.itemLogSelectedDays -> {
                logSelectedDaysMenuClick()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun fridayMenuClick() {
        if (fridayCriteriaEnabled) {
            menuFridays.title = getString(R.string.select_all_fridays)
            unselectAllFridays()
        } else {
            menuFridays.title = getString(R.string.unselect_all_fridays)
            selectAllFridays()
        }
        fridayCriteriaEnabled = !fridayCriteriaEnabled
    }

    private fun threeMonthsMenuClick() {
        if (threeMonthsCriteriaEnabled) {
            menuThreeMonth.title = getString(R.string.select_three_months)
            unselectThreeMonths()
        } else {
            menuThreeMonth.title = getString(R.string.unselect_three_months)
            selectThreeMonths()
        }
        threeMonthsCriteriaEnabled = !threeMonthsCriteriaEnabled
    }

    private fun selectAllFridays() {
        if (calendarView.selectionManager is MultipleSelectionManager) {
            fridayCriteria?.let {
                (calendarView.selectionManager as MultipleSelectionManager).addCriteria(it)
            }
        }
        calendarView.update()
    }

    private fun unselectAllFridays() {
        if (calendarView.selectionManager is MultipleSelectionManager) {
            fridayCriteria?.let {
                (calendarView.selectionManager as MultipleSelectionManager).removeCriteria(it)
            }
        }
        calendarView.update()
    }

    private fun selectThreeMonths() {
        if (calendarView.selectionManager is MultipleSelectionManager) {
            (calendarView.selectionManager as MultipleSelectionManager).addCriteriaList(
                threeMonthsCriteriaList
            )
        }
        calendarView.update()
    }

    private fun unselectThreeMonths() {
        if (calendarView.selectionManager is MultipleSelectionManager) {
            (calendarView.selectionManager as MultipleSelectionManager).removeCriteriaList(
                threeMonthsCriteriaList
            )
        }
        calendarView.update()
    }

    private fun clearSelectionsMenuClick() {
        calendarView.clearSelections()

        fridayCriteriaEnabled = false
        threeMonthsCriteriaEnabled = false
        menuFridays.title = getString(R.string.select_all_fridays)
        menuThreeMonth.title = getString(R.string.select_three_months)
    }

    private fun logSelectedDaysMenuClick() {
        showShortInformation("Selected " + calendarView.selectedDays.size)
    }

    override fun onCheckedChanged(group: RadioGroup, @IdRes checkedId: Int) {
        clearSelectionsMenuClick()
        when (checkedId) {
            R.id.rbShowFlBottomSelectionBar -> {
                calendarView.isShowFlBottomSelectionBar = true
            }
            R.id.rbHideFlBottomSelectionBar -> {
                calendarView.isShowFlBottomSelectionBar = false
            }
            R.id.rbHorizontal -> {
                calendarView.calendarOrientation = OrientationHelper.HORIZONTAL
            }
            R.id.rbVertical -> calendarView.calendarOrientation = OrientationHelper.VERTICAL
            R.id.rbSingle -> {
                calendarView.selectionType = SelectionType.SINGLE
                menuFridays.isVisible = false
                menuThreeMonth.isVisible = false
            }
            R.id.rbMultiple -> {
                calendarView.selectionType = SelectionType.MULTIPLE
                menuFridays.isVisible = true
                menuThreeMonth.isVisible = true
            }
            R.id.rbRange -> {
                calendarView.selectionType = SelectionType.RANGE
                menuFridays.isVisible = false
                menuThreeMonth.isVisible = false

                addDefaultRange()
            }
            R.id.rbNone -> {
                calendarView.selectionType = SelectionType.NONE
                menuFridays.isVisible = false
                menuThreeMonth.isVisible = false
            }
        }
    }

    private fun addDefaultRange() {
        calendarView.selectionManager = RangeSelectionManager(
            object : OnDaySelectedListener {
                override fun onDaySelected() {
                    logD("logSelectedDaysMenuClick " + BaseApplication.gson.toJson(calendarView.selectedDays))
                }
            }
        )
        if (calendarView.selectionManager is RangeSelectionManager) {
            calendarView.clearSelections()
            val rangeSelectionManager: RangeSelectionManager =
                calendarView.selectionManager as RangeSelectionManager
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 3)
            rangeSelectionManager.toggleDay(
                Day(Calendar.getInstance())
            )
            rangeSelectionManager.toggleDay(
                Day(calendar)
            )
            calendarView.update()
        }
    }
}
