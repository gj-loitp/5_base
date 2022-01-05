package vn.loitp.app.activity.customviews.layout.scrollview2d

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_layout_scrollview_2d.*
import vn.loitp.app.R

@LogTag("ScrollView2DActivity")
@IsFullScreen(false)
class ScrollView2DActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_scrollview_2d
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        twoDScrollView.setScrollChangeListener { _, x, y, oldx, oldy ->
            logD("setScrollChangeListener $x - $y - $oldx - $oldy")
        }
        LUIUtil.setDelay(
            mls = 2000,
            runnable = Runnable {
                twoDScrollView.smoothScrollTo(300, 300)
            }
        )
    }
}
