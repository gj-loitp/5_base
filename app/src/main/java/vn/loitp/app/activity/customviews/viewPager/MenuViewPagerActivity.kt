package vn.loitp.app.activity.customviews.viewPager

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_view_pager.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewPager.autoViewPager.ViewPagerAutoActivity
import vn.loitp.app.activity.customviews.viewPager.detectViewPagerSwipeOut.DetectViewPagerSwipeOutActivity
import vn.loitp.app.activity.customviews.viewPager.detectViewPagerSwipeOut2.ViewPagerSwipeOut2Activity
import vn.loitp.app.activity.customviews.viewPager.lockable.LockableViewPagerActivity
import vn.loitp.app.activity.customviews.viewPager.parrallax.ParallaxViewPagerActivity
import vn.loitp.app.activity.customviews.viewPager.refresh.RefreshViewPagerActivity
import vn.loitp.app.activity.customviews.viewPager.vertical.ViewPagerVerticalActivity
import vn.loitp.app.activity.customviews.viewPager.viewPager2.ViewPager2Activity
import vn.loitp.app.activity.customviews.viewPager.viewPagerWithTabLayout.ViewPagerWithTabLayoutActivity

@LogTag("ViewPagerMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuViewPagerActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_view_pager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btAutoViewPager.setOnClickListener(this)
        btParallaxViewPager.setOnClickListener(this)
        btDetectViewPagerSwipeOut.setOnClickListener(this)
        btViewPagerTabLayout.setOnClickListener(this)
        btDetectViewPagerSwipeOut2.setOnClickListener(this)
        btLockableViewPager.setOnClickListener(this)
        btVerticalViewPager.setOnClickListener(this)
        btViewPager2.setOnClickListener(this)
        btRefreshViewPager.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAutoViewPager -> intent = Intent(this, ViewPagerAutoActivity::class.java)
            btParallaxViewPager -> intent = Intent(this, ParallaxViewPagerActivity::class.java)
            btDetectViewPagerSwipeOut ->
                intent =
                    Intent(this, DetectViewPagerSwipeOutActivity::class.java)
            btViewPagerTabLayout ->
                intent =
                    Intent(this, ViewPagerWithTabLayoutActivity::class.java)
            btDetectViewPagerSwipeOut2 ->
                intent =
                    Intent(this, ViewPagerSwipeOut2Activity::class.java)
            btLockableViewPager -> intent = Intent(this, LockableViewPagerActivity::class.java)
            btVerticalViewPager -> intent = Intent(this, ViewPagerVerticalActivity::class.java)
            btViewPager2 -> intent = Intent(this, ViewPager2Activity::class.java)
            btRefreshViewPager -> intent = Intent(this, RefreshViewPagerActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}