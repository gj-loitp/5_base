package vn.loitp.app.activity.customviews.calendar.cosmocalendar.calendarview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.OrientationHelper
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.views.calendar.cosmocalendar.selection.MultipleSelectionManager
import com.views.calendar.cosmocalendar.selection.criteria.BaseCriteria
import com.views.calendar.cosmocalendar.selection.criteria.WeekDayCriteria
import com.views.calendar.cosmocalendar.selection.criteria.month.CurrentMonthCriteria
import com.views.calendar.cosmocalendar.selection.criteria.month.NextMonthCriteria
import com.views.calendar.cosmocalendar.selection.criteria.month.PreviousMonthCriteria
import com.views.calendar.cosmocalendar.utils.SelectionType
import kotlinx.android.synthetic.main.activity_calendar_cosmo.*
import vn.loitp.app.R
import vn.loitp.app.app.LApplication
import java.util.*
import kotlin.collections.ArrayList

//https://github.com/ApplikeySolutions/CosmoCalendar
class CosmoCalendarActivity : BaseFontActivity(), RadioGroup.OnCheckedChangeListener {
    private var threeMonthsCriteriaList = ArrayList<BaseCriteria>()
    private var fridayCriteria: WeekDayCriteria? = null

    private var fridayCriteriaEnabled: Boolean = false
    private var threeMonthsCriteriaEnabled: Boolean = false

    private lateinit var menuFridays: MenuItem
    private lateinit var menuThreeMonth: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        initViews()
        createCriterias()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_calendar_cosmo
    }

    private fun initViews() {
        (findViewById<RadioGroup>(R.id.rgShowHideFlBottomSelectionBar)).setOnCheckedChangeListener(this)
        (findViewById<RadioGroup>(R.id.rg_orientation)).setOnCheckedChangeListener(this)
        (findViewById<RadioGroup>(R.id.rg_selection_type)).setOnCheckedChangeListener(this)
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
        menuFridays = menu.findItem(R.id.select_all_fridays)
        menuThreeMonth = menu.findItem(R.id.select_three_months)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.select_all_fridays -> {
                fridayMenuClick()
                return true
            }
            R.id.select_three_months -> {
                threeMonthsMenuClick()
                return true
            }
            R.id.clear_selections -> {
                clearSelectionsMenuClick()
                return true
            }
            R.id.log_selected_days -> {
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
            (calendarView.selectionManager as MultipleSelectionManager).addCriteria(fridayCriteria)
        }
        calendarView.update()
    }

    private fun unselectAllFridays() {
        if (calendarView.selectionManager is MultipleSelectionManager) {
            (calendarView.selectionManager as MultipleSelectionManager).removeCriteria(fridayCriteria)
        }
        calendarView.update()
    }

    private fun selectThreeMonths() {
        if (calendarView.selectionManager is MultipleSelectionManager) {
            (calendarView.selectionManager as MultipleSelectionManager).addCriteriaList(threeMonthsCriteriaList)
        }
        calendarView.update()
    }

    private fun unselectThreeMonths() {
        if (calendarView.selectionManager is MultipleSelectionManager) {
            (calendarView.selectionManager as MultipleSelectionManager).removeCriteriaList(threeMonthsCriteriaList)
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
        showShort("Selected " + calendarView.selectedDays.size)
        LLog.d(TAG, "logSelectedDaysMenuClick " + LApplication.gson.toJson(calendarView.selectedDays))
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
            R.id.rb_horizontal -> calendarView.calendarOrientation = OrientationHelper.HORIZONTAL
            R.id.rb_vertical -> calendarView.calendarOrientation = OrientationHelper.VERTICAL
            R.id.rb_single -> {
                calendarView.selectionType = SelectionType.SINGLE
                menuFridays.isVisible = false
                menuThreeMonth.isVisible = false
            }
            R.id.rb_multiple -> {
                calendarView.selectionType = SelectionType.MULTIPLE
                menuFridays.isVisible = true
                menuThreeMonth.isVisible = true
            }
            R.id.rb_range -> {
                calendarView.selectionType = SelectionType.RANGE
                menuFridays.isVisible = false
                menuThreeMonth.isVisible = false
            }
            R.id.rb_none -> {
                calendarView.selectionType = SelectionType.NONE
                menuFridays.isVisible = false
                menuThreeMonth.isVisible = false
            }
        }
    }
}
