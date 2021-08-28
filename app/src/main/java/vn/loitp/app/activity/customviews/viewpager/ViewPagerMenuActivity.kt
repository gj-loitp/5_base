package vn.loitp.app.activity.customviews.viewpager

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_view_pager_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.ViewPagerAutoActivity
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.DetectViewPagerSwipeOutActivity
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout2.ViewPagerSwipeOut2Activity
import vn.loitp.app.activity.customviews.viewpager.lockableviewpager.LockableViewPagerActivity
import vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager.ParallaxViewPagerActivity
import vn.loitp.app.activity.customviews.viewpager.refreshviewpager.RefreshViewPagerActivity
import vn.loitp.app.activity.customviews.viewpager.verticalviewpager.ViewPagerVerticalActivity
import vn.loitp.app.activity.customviews.viewpager.viewpagerwithtablayout.ViewPagerWithTabLayoutActivity

@LogTag("ViewPagerMenuActivity")
@IsFullScreen(false)
class ViewPagerMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btAutoViewPager.setOnClickListener(this)
        btParallaxViewPager.setOnClickListener(this)
        btDetectViewPagerSwipeOut.setOnClickListener(this)
        btViewPagerTabLayout.setOnClickListener(this)
        btDetectViewPagerSwipeOut2.setOnClickListener(this)
        btLockableViewPager.setOnClickListener(this)
        btVerticalViewPager.setOnClickListener(this)
        btRefreshViewPager.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAutoViewPager -> intent = Intent(this, ViewPagerAutoActivity::class.java)
            btParallaxViewPager -> intent = Intent(this, ParallaxViewPagerActivity::class.java)
            btDetectViewPagerSwipeOut -> intent = Intent(this, DetectViewPagerSwipeOutActivity::class.java)
            btViewPagerTabLayout -> intent = Intent(this, ViewPagerWithTabLayoutActivity::class.java)
            btDetectViewPagerSwipeOut2 -> intent = Intent(this, ViewPagerSwipeOut2Activity::class.java)
            btLockableViewPager -> intent = Intent(this, LockableViewPagerActivity::class.java)
            btVerticalViewPager -> intent = Intent(this, ViewPagerVerticalActivity::class.java)
            btRefreshViewPager -> intent = Intent(this, RefreshViewPagerActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
