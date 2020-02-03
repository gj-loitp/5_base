package vn.loitp.app.activity.customviews.layout.expansionpanel

import android.os.Bundle
import com.core.base.BaseFontActivity
import loitp.basemaster.R

class ExpansionPanelSampleActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.expansion_panel_sample_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}