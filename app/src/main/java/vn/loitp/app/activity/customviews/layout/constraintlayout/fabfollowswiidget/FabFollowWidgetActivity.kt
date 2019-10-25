package vn.loitp.app.activity.customviews.layout.constraintlayout.fabfollowswiidget

import android.os.Bundle

import com.core.base.BaseFontActivity

import loitp.basemaster.R

class FabFollowWidgetActivity : BaseFontActivity() {

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
        return R.layout.activity_fab_follow_widget
    }

}
