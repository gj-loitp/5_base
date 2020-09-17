package vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_editext_animated_expandale)
class AnimatedExpandableEditTextActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }
}
