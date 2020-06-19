package vn.loitp.app.activity.customviews.textview.zoomtextview

import android.os.Bundle

import com.core.base.BaseFontActivity

import vn.loitp.app.R

class ZoomTextViewActivity : BaseFontActivity() {
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
        return R.layout.activity_textview_zoom
    }

}
