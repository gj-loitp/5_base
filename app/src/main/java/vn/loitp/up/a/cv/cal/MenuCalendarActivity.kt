package vn.loitp.up.a.cv.cal

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ActivityMenuCalendarBinding
import vn.loitp.up.a.cv.cal.cosmo.CosmoCalendarActivity

@LogTag("MenuCalendarActivity")
@IsFullScreen(false)
class MenuCalendarActivity : BaseActivityFont() {
    private lateinit var binding: ActivityMenuCalendarBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuCalendarActivity::class.java.simpleName
        }
        binding.btCalendar.setSafeOnClickListener {
            launchActivity(CosmoCalendarActivity::class.java)
        }
    }
}
