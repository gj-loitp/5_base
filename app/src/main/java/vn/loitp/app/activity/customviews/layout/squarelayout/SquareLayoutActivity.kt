package vn.loitp.app.activity.customviews.layout.squarelayout

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_layout_square)
@LogTag("SquareLayoutActivity")
class SquareLayoutActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
