package vn.loitp.app.activity.customviews.progressloadingview.avloadingindicatorview

import android.os.Bundle

import com.core.base.BaseFontActivity

import vn.loitp.app.R

class AVLoadingIndicatorActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_avloading_indicator
    }
}
