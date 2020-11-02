package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup.OnCheckedStateChangeListener
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import kotlinx.android.synthetic.main.activity_switch_tbg_custom_button.*
import vn.loitp.app.R

@LogTag("TBGCustomButtonActivity")
@IsFullScreen(false)
class TBGCustomButtonActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_tbg_custom_button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        groupSingleRadioButton.setOnCheckedChangeListener(SingleSelectListener())
        groupMultiCustomCompoundButton.setOnCheckedChangeListener(MultiSelectListener())
        groupMultiCustomToggleButton.setOnCheckedChangeListener(MultiSelectListener())
        groupSingleCustomCompoundToggleButton.setOnCheckedChangeListener(SingleSelectListener())
    }

    private inner class SingleSelectListener : SingleSelectToggleGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: SingleSelectToggleGroup, checkedId: Int) {
            logD("onCheckedChanged")
        }
    }

    private inner class MultiSelectListener : OnCheckedStateChangeListener {
        override fun onCheckedStateChanged(group: MultiSelectToggleGroup, checkedId: Int, isChecked: Boolean) {
            logD("onCheckedStateChanged(): $checkedId, isChecked = $isChecked")
        }
    }
}
