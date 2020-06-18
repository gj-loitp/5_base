package vn.loitp.app.activity.customviews.viewpager

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_view_pager_menu.*

import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.AutoViewPagerActivity
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.DetectViewPagerSwipeOutActivity
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout2.ViewPagerSwipeOut2Activity
import vn.loitp.app.activity.customviews.viewpager.lockableviewpager.LockableViewPagerActivity
import vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager.ParallaxViewPagerActivity
import vn.loitp.app.activity.customviews.viewpager.refreshviewpager.RefreshViewPagerActivity
import vn.loitp.app.activity.customviews.viewpager.viewpagerwithtablayout.ViewPagerWithTabLayoutActivity

class ViewPagerMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btAutoViewPager.setOnClickListener(this)
        btParallaxViewPager.setOnClickListener(this)
        btDetectViewPagerSwipeOut.setOnClickListener(this)
        btViewPagerTabLayout.setOnClickListener(this)
        btDetectViewPagerSwipeOut2.setOnClickListener(this)
        btLockableViewPager.setOnClickListener(this)
        btRefreshViewPager.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAutoViewPager -> intent = Intent(activity, AutoViewPagerActivity::class.java)
            btParallaxViewPager -> intent = Intent(activity, ParallaxViewPagerActivity::class.java)
            btDetectViewPagerSwipeOut -> intent = Intent(activity, DetectViewPagerSwipeOutActivity::class.java)
            btViewPagerTabLayout -> intent = Intent(activity, ViewPagerWithTabLayoutActivity::class.java)
            btDetectViewPagerSwipeOut2 -> intent = Intent(activity, ViewPagerSwipeOut2Activity::class.java)
            btLockableViewPager -> intent = Intent(activity, LockableViewPagerActivity::class.java)
            btRefreshViewPager -> intent = Intent(activity, RefreshViewPagerActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
