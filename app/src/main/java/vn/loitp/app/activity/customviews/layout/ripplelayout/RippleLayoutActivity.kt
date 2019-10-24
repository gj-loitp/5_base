package vn.loitp.app.activity.customviews.layout.ripplelayout

import android.os.Bundle

import com.core.base.BaseFontActivity

import loitp.basemaster.R

class RippleLayoutActivity : BaseFontActivity() {

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
        return R.layout.activity_ripple_layout
    }
}
