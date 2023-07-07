package vn.loitp.up.a.cv.sw.toggleButtonGroup

import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.isDarkTheme
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.nex3z.togglebuttongroup.button.LabelToggle
import vn.loitp.R
import vn.loitp.databinding.ASwitchTbgFlowLabelBinding

@LogTag("TBGFlowLabelActivity")
@IsFullScreen(false)
class TBGFlowLabelActivity : BaseActivityFont() {
    private lateinit var binding: ASwitchTbgFlowLabelBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASwitchTbgFlowLabelBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = TBGFlowLabelActivity::class.java.simpleName
        }
        binding.groupWeekdays.setOnCheckedChangeListener { _, checkedId ->
            logD("onCheckedChanged(): checkedId = $checkedId")
        }
        val dummyText = resources.getStringArray(R.array.dummy_text)
        for (text in dummyText) {
            val toggle = LabelToggle(this)
            toggle.text = text
            if (isDarkTheme()) {
                toggle.setTextColor(Color.WHITE)
            } else {
                toggle.setTextColor(Color.BLACK)
            }

            binding.groupDummy.addView(toggle)
        }
    }
}
