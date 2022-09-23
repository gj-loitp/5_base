package vn.loitp.app.activity.customviews.switchToggle.toggleButtonGroup

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup
import kotlinx.android.synthetic.main.activity_switch_tbg_multi_select.*
import vn.loitp.app.R

@LogTag("TBGMultiSelectActivity")
@IsFullScreen(false)
class TBGMultiSelectActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_tbg_multi_select
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = TBGMultiSelectActivity::class.java.simpleName
        }
        groupWeekdays.setOnCheckedChangeListener { group: MultiSelectToggleGroup, _: Int, _: Boolean ->
            logD("onCheckedStateChanged(): group.getCheckedIds() = " + group.checkedIds)
        }
    }
}
