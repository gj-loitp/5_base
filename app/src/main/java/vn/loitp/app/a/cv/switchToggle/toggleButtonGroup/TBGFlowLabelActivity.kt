package vn.loitp.app.a.cv.switchToggle.toggleButtonGroup

import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.nex3z.togglebuttongroup.button.LabelToggle
import kotlinx.android.synthetic.main.activity_switch_tbg_flow_label.*
import vn.loitp.R

@LogTag("TBGFlowLabelActivity")
@IsFullScreen(false)
class TBGFlowLabelActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_tbg_flow_label
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
            this.tvTitle?.text = TBGFlowLabelActivity::class.java.simpleName
        }
        groupWeekdays.setOnCheckedChangeListener { _, checkedId ->
            logD("onCheckedChanged(): checkedId = $checkedId")
        }
        val dummyText = resources.getStringArray(R.array.dummy_text)
        for (text in dummyText) {
            val toggle = LabelToggle(this)
            toggle.text = text
            if (LUIUtil.isDarkTheme()) {
                toggle.setTextColor(Color.WHITE)
            } else {
                toggle.setTextColor(Color.BLACK)
            }

            groupDummy.addView(toggle)
        }
    }
}
