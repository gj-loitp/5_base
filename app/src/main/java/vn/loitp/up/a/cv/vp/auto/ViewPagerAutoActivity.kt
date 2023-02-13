package vn.loitp.up.a.cv.vp.auto

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
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AVpAutoBinding
import vn.loitp.up.a.cv.vp.auto.FrmIv.Companion.newInstance

@LogTag("ViewPagerAutoActivity")
@IsFullScreen(false)
class ViewPagerAutoActivity : BaseActivityFont() {
    private lateinit var binding: AVpAutoBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVpAutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ViewPagerAutoActivity::class.java.simpleName
        }

        binding.viewPager.adapter = SamplePagerAdapter(supportFragmentManager)
        binding.viewPager.setAutoScrollEnabled(true)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
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
