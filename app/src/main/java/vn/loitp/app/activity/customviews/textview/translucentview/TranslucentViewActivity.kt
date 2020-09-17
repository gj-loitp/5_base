package vn.loitp.app.activity.customviews.textview.translucentview

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_text_view_translucent)
class TranslucentViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
