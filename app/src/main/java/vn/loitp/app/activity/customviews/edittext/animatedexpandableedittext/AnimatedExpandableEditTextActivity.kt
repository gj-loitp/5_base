package vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_editext_animated_expandale)
@LogTag("AnimatedExpandableEditTextActivity")
class AnimatedExpandableEditTextActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
