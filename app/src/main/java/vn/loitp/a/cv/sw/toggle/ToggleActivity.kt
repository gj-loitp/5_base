package vn.loitp.a.cv.sw.toggle

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.sw.toggle.LabeledSwitch
import com.loitp.views.sw.toggle.OnToggledListener
import kotlinx.android.synthetic.main.a_sw_toggle.*
import vn.loitp.R

// https://github.com/Angads25/android-toggle?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6778

@LogTag("ToggleActivity")
@IsFullScreen(false)
class ToggleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sw_toggle
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
            this.tvTitle?.text = ToggleActivity::class.java.simpleName
        }
        labeledSwitch.setOnToggledListener(object : OnToggledListener {
            override fun onSwitched(labeledSwitch: LabeledSwitch, isOn: Boolean) {
                showShortInformation("isOn $isOn")
            }
        })

        llc.setOnClickListener {
            ls.performClick()
        }
        ls.setColorBorder(LAppResource.getColor(R.color.deepPink))
        ls.setOnToggledListener(object : OnToggledListener {
            override fun onSwitched(labeledSwitch: LabeledSwitch, isOn: Boolean) {
                showShortInformation("isOn $isOn")
            }
        })
    }
}
