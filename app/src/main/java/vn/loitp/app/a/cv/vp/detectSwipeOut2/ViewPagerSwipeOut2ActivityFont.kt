package vn.loitp.app.a.cv.vp.detectSwipeOut2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FONT_PATH
import com.loitp.core.ext.changeTabsFont
import com.loitp.core.ext.setPullLikeIOSHorizontal
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.vp.swipeOut.LSwipeOutViewPager
import kotlinx.android.synthetic.main.activity_view_pager_swipe_out_2.*
import vn.loitp.R
import vn.loitp.app.a.cv.vp.auto.FrmIv.Companion.newInstance

@LogTag("ViewPagerSwipeOut2Activity")
@IsFullScreen(false)
class ViewPagerSwipeOut2ActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_swipe_out_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ViewPagerSwipeOut2ActivityFont::class.java.simpleName
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
        vp.setPullLikeIOSHorizontal()
        tabLayout.setupWithViewPager(vp)
        tabLayout.changeTabsFont(FONT_PATH)
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
