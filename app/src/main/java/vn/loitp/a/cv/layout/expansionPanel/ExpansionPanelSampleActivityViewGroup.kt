package vn.loitp.a.cv.layout.expansionPanel

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_expansion_panel_sample_main_view_group.*
import vn.loitp.R

@LogTag("ExpansionPanelSampleActivityViewGroup")
@IsFullScreen(false)
class ExpansionPanelSampleActivityViewGroup : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_expansion_panel_sample_main_view_group
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
            this.tvTitle?.text = ExpansionPanelSampleActivityViewGroup::class.java.simpleName
        }
    }
}
