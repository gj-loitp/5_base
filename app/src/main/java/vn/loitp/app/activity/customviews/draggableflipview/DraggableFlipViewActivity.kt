package vn.loitp.app.activity.customviews.draggableflipview

import android.os.Bundle
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/ssk5460/DraggableFlipView?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2509
class DraggableFlipViewActivity : BaseFontActivity() {

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
        return R.layout.activity_draggable_flipview
    }

}
