package vn.loitp.app.activity.customviews.layout.expansionpanel

import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("ExpansionPanelSampleActivity")
@IsFullScreen(false)
class ExpansionPanelSampleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_expansion_panel_sample_main
    }
}
