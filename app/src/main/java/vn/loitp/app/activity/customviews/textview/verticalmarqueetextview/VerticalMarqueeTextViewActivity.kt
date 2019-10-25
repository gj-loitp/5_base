package vn.loitp.app.activity.customviews.textview.verticalmarqueetextview

import android.os.Bundle

import com.core.base.BaseFontActivity

import loitp.basemaster.R

class VerticalMarqueeTextViewActivity : BaseFontActivity() {
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
        return R.layout.activity_vertical_marque_textview
    }

}
