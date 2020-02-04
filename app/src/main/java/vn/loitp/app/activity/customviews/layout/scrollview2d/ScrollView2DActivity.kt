package vn.loitp.app.activity.customviews.layout.scrollview2d

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.views.scrollview.TwoDScrollView

import loitp.basemaster.R

class ScrollView2DActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val twoDScrollView = findViewById<TwoDScrollView>(R.id.sv)
        twoDScrollView.setScrollChangeListner { _, x, y, oldx, oldy -> LLog.d(TAG, "setScrollChangeListner $x - $y - $oldx - $oldy") }
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

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scrollview_2d
    }
}
