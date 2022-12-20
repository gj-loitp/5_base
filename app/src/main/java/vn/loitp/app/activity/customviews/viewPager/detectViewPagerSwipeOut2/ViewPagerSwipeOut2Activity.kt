package vn.loitp.app.activity.customviews.viewPager.detectViewPagerSwipeOut2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LUIUtil
import com.loitpcore.views.viewPager.swipeOut.LSwipeOutViewPager
import kotlinx.android.synthetic.main.activity_view_pager_swipe_out_2.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewPager.autoViewPager.FrmIv.Companion.newInstance

@LogTag("ViewPagerSwipeOut2Activity")
@IsFullScreen(false)
class ViewPagerSwipeOut2Activity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_swipe_out_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ViewPagerSwipeOut2Activity::class.java.simpleName
        }
        vp.adapter = SamplePagerAdapter(supportFragmentManager)
        vp.setOnSwipeOutListener(object : LSwipeOutViewPager.OnSwipeOutListener {
            override fun onSwipeOutAtStart() {
                showShortInformation("onSwipeOutAtStart")
            }

            override fun onSwipeOutAtEnd() {
                showShortInformation("onSwipeOutAtEnd")
            }
        })
        LUIUtil.setPullLikeIOSHorizontal(vp)
        tabLayout.setupWithViewPager(vp)
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
