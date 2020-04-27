package vn.loitp.app.activity.customviews.layout.aspectratiolayout

import android.os.Bundle
import com.core.base.BaseFontActivity
import vn.loitp.app.R

class AspectRatioLayoutActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_aspect_ratio
    }
}
