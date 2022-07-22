package vn.loitp.app.activity.customviews.layout.expansionPanel

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_expansion_panel_sample_main_viewgroup.*
import vn.loitp.app.R

@LogTag("ExpansionPanelSampleActivityViewGroup")
@IsFullScreen(false)
class ExpansionPanelSampleActivityViewGroup : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_expansion_panel_sample_main_viewgroup
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ExpansionPanelSampleActivityViewGroup::class.java.simpleName
        }
    }
}
