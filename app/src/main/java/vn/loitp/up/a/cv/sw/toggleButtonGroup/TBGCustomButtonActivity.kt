package vn.loitp.up.a.cv.sw.toggleButtonGroup

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup.OnCheckedStateChangeListener
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import vn.loitp.R
import vn.loitp.databinding.ASwitchTbgCustomButtonBinding

@LogTag("TBGCustomButtonActivity")
@IsFullScreen(false)
class TBGCustomButtonActivity : BaseActivityFont() {
    private lateinit var binding: ASwitchTbgCustomButtonBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASwitchTbgCustomButtonBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = TBGCustomButtonActivity::class.java.simpleName
        }
        binding.groupSingleRadioButton.setOnCheckedChangeListener(SingleSelectListener())
        binding.groupMultiCustomCompoundButton.setOnCheckedChangeListener(MultiSelectListener())
        binding.groupMultiCustomToggleButton.setOnCheckedChangeListener(MultiSelectListener())
        binding.groupSingleCustomCompoundToggleButton.setOnCheckedChangeListener(
            SingleSelectListener()
        )
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
