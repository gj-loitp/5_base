package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.nex3z.togglebuttongroup.button.LabelToggle
import kotlinx.android.synthetic.main.activity_tbg_flow_label.*
import vn.loitp.app.R

class TBGFlowLabelActivity : BaseFontActivity() {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_tbg_flow_label
    }

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
            groupDummy.addView(toggle)
        }
    }
}