package vn.loitp.app.activity.customviews.layout.expansionpanel

import android.os.Bundle
import com.core.base.BaseFontActivity
import vn.loitp.app.R

class ExpansionPanelSampleActivityViewGroup : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_expansion_panel_sample_main_viewgroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}