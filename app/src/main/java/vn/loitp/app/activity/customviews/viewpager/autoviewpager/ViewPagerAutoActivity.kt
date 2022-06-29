package vn.loitp.app.activity.customviews.viewpager.autoviewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_view_pager_auto.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.FrmIv.Companion.newInstance

@LogTag("ViewPagerAutoActivity")
@IsFullScreen(false)
class ViewPagerAutoActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_auto
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager.adapter = SamplePagerAdapter(supportFragmentManager)
        // viewPager.setIndeterminate(true)
        viewPager.setAutoScrollEnabled(true)
        tabLayout.setupWithViewPager(viewPager)
        LUIUtil.changeTabsFont(tabLayout, Constants.FONT_PATH)
    }

    private inner class SamplePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return newInstance()
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence {
            return "Page Title $position"
        }
    }
}
