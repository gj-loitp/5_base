package vn.loitp.app.activity.customviews.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_calendar_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.calendar.cosmocalendar.calendarview.CosmoCalendarActivity

@LayoutId( R.layout.activity_calendar_menu)
class CalendarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btCalendar.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btCalendar -> intent = Intent(activity, CosmoCalendarActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
