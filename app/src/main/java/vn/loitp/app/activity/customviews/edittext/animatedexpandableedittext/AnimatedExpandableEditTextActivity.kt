package vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext

import com.core.base.BaseFontActivity
import loitp.basemaster.R

class AnimatedExpandableEditTextActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animated_expandale_editext
    }

}
