package vn.loitp.up.a.cv.vp.lockable

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
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AVpLockableBinding
import vn.loitp.up.a.cv.vp.auto.FrmIv

@LogTag("LockableViewPagerActivity")
@IsFullScreen(false)
class LockableViewPagerActivity : BaseActivityFont() {
    private lateinit var binding: AVpLockableBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVpLockableBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = LockableViewPagerActivity::class.java.simpleName
        }
        binding.vp.adapter = SamplePagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.vp)
        binding.tabLayout.changeTabsFont(fontName = FONT_PATH)
        binding.btEnable.setSafeOnClickListener {
            binding.vp.swipeLocked = false
        }
        binding.btDisable.setSafeOnClickListener {
            binding.vp.swipeLocked = true
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
