package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.graphics.Color
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.nex3z.togglebuttongroup.button.LabelToggle
import kotlinx.android.synthetic.main.activity_switch_tbg_flow_label.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_switch_tbg_flow_label)
@LogTag("TBGFlowLabelActivity")
@IsFullScreen(false)
class TBGFlowLabelActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
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
