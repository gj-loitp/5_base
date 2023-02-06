package vn.loitp.up.a.cv.vp.detectSwipeOut2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FONT_PATH
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.changeTabsFont
import com.loitp.core.ext.setPullLikeIOSHorizontal
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.vp.swipeOut.LSwipeOutViewPager
import vn.loitp.R
import vn.loitp.a.cv.vp.auto.FrmIv.Companion.newInstance
import vn.loitp.databinding.AVpSwipeOut2Binding

@LogTag("ViewPagerSwipeOut2Activity")
@IsFullScreen(false)
class ViewPagerSwipeOut2Activity : BaseActivityFont() {
    private lateinit var binding: AVpSwipeOut2Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVpSwipeOut2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ViewPagerSwipeOut2Activity::class.java.simpleName
        }
        binding.vp.adapter = SamplePagerAdapter(supportFragmentManager)
        binding.vp.setOnSwipeOutListener(object : LSwipeOutViewPager.OnSwipeOutListener {
            override fun onSwipeOutAtStart() {
                showShortInformation("onSwipeOutAtStart")
            }

            override fun onSwipeOutAtEnd() {
                showShortInformation("onSwipeOutAtEnd")
            }
        })
        binding.vp.setPullLikeIOSHorizontal()
        binding.tabLayout.setupWithViewPager(binding.vp)
        binding.tabLayout.changeTabsFont(FONT_PATH)
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
