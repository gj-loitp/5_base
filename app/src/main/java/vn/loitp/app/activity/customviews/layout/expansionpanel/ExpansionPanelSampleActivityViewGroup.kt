package vn.loitp.app.activity.customviews.layout.expansionpanel

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_expansion_panel_sample_main_viewgroup)
@LogTag("ExpansionPanelSampleActivityViewGroup")
class ExpansionPanelSampleActivityViewGroup : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}