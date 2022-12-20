package vn.loitp.app.activity.customviews.switchToggle.toggleButtonGroup

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
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
            this.tvTitle?.text = TBGCustomButtonActivity::class.java.simpleName
        }
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
        override fun onCheckedStateChanged(
            group: MultiSelectToggleGroup,
            checkedId: Int,
            isChecked: Boolean
        ) {
            logD("onCheckedStateChanged(): $checkedId, isChecked = $isChecked")
        }
    }
}
