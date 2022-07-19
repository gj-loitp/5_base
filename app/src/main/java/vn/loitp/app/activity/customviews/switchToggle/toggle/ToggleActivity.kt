package vn.loitp.app.activity.customviews.switchToggle.toggle

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.switchToggle.toggle.LabeledSwitch
import com.loitpcore.views.switchToggle.toggle.OnToggledListener
import kotlinx.android.synthetic.main.activity_switch_toggle.*
import vn.loitp.app.R

// https://github.com/Angads25/android-toggle?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6778

@LogTag("ToggleActivity")
@IsFullScreen(false)
class ToggleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_toggle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        labeledSwitch.setOnToggledListener(object : OnToggledListener {
            override fun onSwitched(labeledSwitch: LabeledSwitch, isOn: Boolean) {
                showShortInformation("isOn $isOn")
            }
        })
    }
}