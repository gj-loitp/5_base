package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.viewpager.swipeout.LSwipeOutViewPager
import kotlinx.android.synthetic.main.activity_view_pager_swipe_out_2.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.FrmIv.Companion.newInstance

@LayoutId(R.layout.activity_view_pager_swipe_out_2)
@LogTag("ViewPagerSwipeOut2Activity")
@IsFullScreen(false)
class ViewPagerSwipeOut2Activity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        vp.adapter = SamplePagerAdapter(supportFragmentManager)
        vp.setOnSwipeOutListener(object : LSwipeOutViewPager.OnSwipeOutListener {
            override fun onSwipeOutAtStart() {
                showShort("onSwipeOutAtStart")
            }

            override fun onSwipeOutAtEnd() {
                showShort("onSwipeOutAtEnd")
            }
        })
        LUIUtil.setPullLikeIOSHorizontal(vp)
        tabLayout.setupWithViewPager(vp)
        LUIUtil.changeTabsFont(tabLayout, com.core.common.Constants.FONT_PATH)
    }

    private inner class SamplePagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return newInstance()
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "Page Title $position"
        }
    }
}
