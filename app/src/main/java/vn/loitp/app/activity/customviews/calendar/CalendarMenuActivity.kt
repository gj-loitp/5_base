package vn.loitp.app.activity.customviews.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_calendar_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.calendar.cosmocalendar.calendarview.CosmoCalendarActivity

@LogTag("CalendarMenuActivity")
@IsFullScreen(false)
class CalendarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_calendar_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = CalendarMenuActivity::class.java.simpleName
        }
        btCalendar.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btCalendar -> intent = Intent(this, CosmoCalendarActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
