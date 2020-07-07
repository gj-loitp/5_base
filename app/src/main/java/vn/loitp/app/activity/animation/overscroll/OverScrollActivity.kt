package vn.loitp.app.activity.animation.overscroll

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_animation_over_scroll.*
import vn.loitp.app.R

class OverScrollActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LUIUtil.setPullLikeIOSVertical(scrollView = nsv)

        textView.text = LStoreUtil.readTxtFromRawFolder(context = activity, nameOfRawFile = R.raw.overscroll)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_over_scroll
    }
}
