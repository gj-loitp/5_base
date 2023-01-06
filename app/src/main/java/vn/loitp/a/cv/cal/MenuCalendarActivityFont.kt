package vn.loitp.a.cv.cal

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_calendar.*
import vn.loitp.R
import vn.loitp.a.cv.cal.cosmo.CosmoCalendarActivityFont

@LogTag("MenuCalendarActivity")
@IsFullScreen(false)
class MenuCalendarActivityFont : BaseActivityFont(), View.OnClickListener {

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
            this.tvTitle?.text = MenuCalendarActivityFont::class.java.simpleName
        }
        btCalendar.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btCalendar -> intent = Intent(this, CosmoCalendarActivityFont::class.java)
        }
        intent?.let {
            startActivity(intent)
            this.tranIn()
        }
    }
}
