package vn.loitp.app.activity.customviews.textview.translucentview

import android.os.Bundle

import com.core.base.BaseFontActivity

import loitp.basemaster.R

class TranslucentViewActivity : BaseFontActivity() {
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
        return R.layout.activity_translucent_view
    }

}
