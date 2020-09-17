package vn.loitp.app.activity.customviews.layout.scrollview2d

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_layout_scrollview_2d.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_layout_scrollview_2d)
class ScrollView2DActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        twoDScrollView.setScrollChangeListner { _, x, y, oldx, oldy ->
            logD("setScrollChangeListner $x - $y - $oldx - $oldy")
        }
        LUIUtil.setDelay(2000, Runnable {
            twoDScrollView.smoothScrollTo(300, 300)
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
