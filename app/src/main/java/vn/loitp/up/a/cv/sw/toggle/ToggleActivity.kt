package vn.loitp.up.a.cv.sw.toggle

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.sw.toggle.LabeledSwitch
import com.loitp.views.sw.toggle.OnToggledListener
import vn.loitp.R
import vn.loitp.databinding.ASwToggleBinding

// https://github.com/Angads25/android-toggle?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6778

@LogTag("ToggleActivity")
@IsFullScreen(false)
class ToggleActivity : BaseActivityFont() {
    private lateinit var binding: ASwToggleBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASwToggleBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = ToggleActivity::class.java.simpleName
        }
        binding.labeledSwitch.setOnToggledListener(object : OnToggledListener {
            override fun onSwitched(labeledSwitch: LabeledSwitch, isOn: Boolean) {
                showShortInformation("isOn $isOn")
            }
        })

        binding.llc.setOnClickListener {
            binding.ls.performClick()
        }
        binding.ls.setColorBorder(getColor(R.color.deepPink))
        binding.ls.setOnToggledListener(object : OnToggledListener {
            override fun onSwitched(labeledSwitch: LabeledSwitch, isOn: Boolean) {
                showShortInformation("isOn $isOn")
            }
        })
    }
}
