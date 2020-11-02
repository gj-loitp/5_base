package vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("AnimatedExpandableEditTextActivity")
@IsFullScreen(false)
class AnimatedExpandableEditTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_animated_expandale
    }
}
