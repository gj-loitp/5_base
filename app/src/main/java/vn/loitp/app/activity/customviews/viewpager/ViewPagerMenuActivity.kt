package vn.loitp.app.activity.customviews.viewpager

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import loitp.basemaster.R
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
        findViewById<View>(R.id.bt_auto_viewpager).setOnClickListener(this)
        findViewById<View>(R.id.bt_parallax_viewpager).setOnClickListener(this)
        findViewById<View>(R.id.bt_detect_viewpager_swipe_out).setOnClickListener(this)
        findViewById<View>(R.id.bt_view_pager_tablayout).setOnClickListener(this)
        findViewById<View>(R.id.bt_detect_viewpager_swipe_out_2).setOnClickListener(this)
        findViewById<View>(R.id.bt_lockable_viewpager).setOnClickListener(this)
        findViewById<View>(R.id.bt_refresh_viewpager).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_view_pager
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_auto_viewpager -> intent = Intent(activity, AutoViewPagerActivity::class.java)
            R.id.bt_parallax_viewpager -> intent = Intent(activity, ParallaxViewPagerActivity::class.java)
            R.id.bt_detect_viewpager_swipe_out -> intent = Intent(activity, DetectViewPagerSwipeOutActivity::class.java)
            R.id.bt_view_pager_tablayout -> intent = Intent(activity, ViewPagerWithTabLayoutActivity::class.java)
            R.id.bt_detect_viewpager_swipe_out_2 -> intent = Intent(activity, ViewPagerSwipeOut2Activity::class.java)
            R.id.bt_lockable_viewpager -> intent = Intent(activity, LockableViewPagerActivity::class.java)
            R.id.bt_refresh_viewpager -> intent = Intent(activity, RefreshViewPagerActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
