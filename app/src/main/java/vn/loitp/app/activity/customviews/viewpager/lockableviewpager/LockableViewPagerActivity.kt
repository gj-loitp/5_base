package vn.loitp.app.activity.customviews.viewpager.lockableviewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_viewpager_lockable.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.FrmIv

@LogTag("LockableViewPagerActivity")
@IsFullScreen(false)
class LockableViewPagerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_viewpager_lockable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        vp.adapter = SamplePagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(vp)
        LUIUtil.changeTabsFont(tabLayout = tabLayout, fontName = Constants.FONT_PATH)
        btEnable.setSafeOnClickListener {
            vp.swipeLocked = false
        }
        btDisable.setSafeOnClickListener {
            vp.swipeLocked = true
        }
    }

    private inner class SamplePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return FrmIv.newInstance()
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence {
            return "Page Title $position"
        }
    }
}
