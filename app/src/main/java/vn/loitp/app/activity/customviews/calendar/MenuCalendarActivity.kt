package vn.loitp.app.activity.customviews.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_calendar.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.calendar.cosmoCalendar.CosmoCalendarActivity

@LogTag("MenuCalendarActivity")
@IsFullScreen(false)
class MenuCalendarActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_calendar
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuCalendarActivity::class.java.simpleName
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
