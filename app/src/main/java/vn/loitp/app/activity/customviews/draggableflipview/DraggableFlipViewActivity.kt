package vn.loitp.app.activity.customviews.draggableflipview

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/ssk5460/DraggableFlipView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2509

@LayoutId(R.layout.activity_draggable_flipview)
class DraggableFlipViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

}
