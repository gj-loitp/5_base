package vn.loitp.app.a.customviews.menu.resideMenu

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_reside_menu_calendar.*
import vn.loitp.R

@LogTag("CalendarFragment")
class CalendarFragment : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_reside_menu_calendar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        activity?.let { a ->
            val arrayAdapter = ArrayAdapter(
                a,
                R.layout.view_item_test_retrofit,
                calendarData
            )
            listView.adapter = arrayAdapter
            listView.onItemClickListener =
                AdapterView.OnItemClickListener { _: AdapterView<*>?, _: View?, _: Int, _: Long ->
                    showShortInformation("Clicked item!")
                }
        }
    }

    private val calendarData: ArrayList<String>
        get() {
            val calendarList = ArrayList<String>()
            calendarList.add("New Year's Day")
            calendarList.add("St. Valentine's Day")
            calendarList.add("Easter Day")
            calendarList.add("April Fool's Day")
            calendarList.add("Mother's Day")
            calendarList.add("Memorial Day")
            calendarList.add("National Flag Day")
            calendarList.add("Father's Day")
            calendarList.add("Independence Day")
            calendarList.add("Labor Day")
            calendarList.add("Columbus Day")
            calendarList.add("Halloween")
            calendarList.add("All Soul's Day")
            calendarList.add("Veterans Day")
            calendarList.add("Thanksgiving Day")
            calendarList.add("Election Day")
            calendarList.add("Forefather's Day")
            calendarList.add("Christmas Day")
            return calendarList
        }
}
