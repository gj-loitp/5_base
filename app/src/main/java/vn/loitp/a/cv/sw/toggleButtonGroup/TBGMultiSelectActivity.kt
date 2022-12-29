package vn.loitp.a.cv.sw.toggleButtonGroup

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup
import kotlinx.android.synthetic.main.a_switch_tbg_multi_select.*
import vn.loitp.R

@LogTag("TBGMultiSelectActivity")
@IsFullScreen(false)
class TBGMultiSelectActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_switch_tbg_multi_select
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
            this.tvTitle?.text = TBGMultiSelectActivity::class.java.simpleName
        }
        groupWeekdays.setOnCheckedChangeListener { group: MultiSelectToggleGroup, _: Int, _: Boolean ->
            logD("onCheckedStateChanged(): group.getCheckedIds() = " + group.checkedIds)
        }
    }
}
