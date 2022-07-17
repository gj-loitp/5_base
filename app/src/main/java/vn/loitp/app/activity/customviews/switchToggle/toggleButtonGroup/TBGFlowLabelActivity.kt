package vn.loitp.app.activity.customviews.switchToggle.toggleButtonGroup

import android.graphics.Color
import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.nex3z.togglebuttongroup.button.LabelToggle
import kotlinx.android.synthetic.main.activity_switch_tbg_flow_label.*
import vn.loitp.app.R

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
