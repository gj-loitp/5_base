package vn.loitp.app.activity.customviews.imageview.circularroundrectimageview

import android.os.Bundle

import com.core.base.BaseFontActivity

import loitp.basemaster.R

//guide: https://github.com/sparrow007/CircularImageview?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6166
class CircularRoundRectImageViewActivity : BaseFontActivity() {

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
        return R.layout.activity_circular_roundrect_imageview
    }
}
