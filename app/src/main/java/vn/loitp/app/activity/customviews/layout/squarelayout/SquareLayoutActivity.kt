package vn.loitp.app.activity.customviews.layout.squarelayout

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("SquareLayoutActivity")
@IsFullScreen(false)
class SquareLayoutActivity : BaseFontActivity(){

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_square
    }
}
