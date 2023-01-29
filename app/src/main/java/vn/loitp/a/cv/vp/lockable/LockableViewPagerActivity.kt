package vn.loitp.a.cv.vp.lockable

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FONT_PATH
import com.loitp.core.ext.changeTabsFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_vp_lockable.*
import vn.loitp.R
import vn.loitp.a.cv.vp.auto.FrmIv

@LogTag("LockableViewPagerActivity")
@IsFullScreen(false)
class LockableViewPagerActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_vp_lockable
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
            this.tvTitle?.text = LockableViewPagerActivity::class.java.simpleName
        }
        vp.adapter = SamplePagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(vp)
        tabLayout.changeTabsFont(fontName = FONT_PATH)
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
