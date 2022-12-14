package vn.loitp.app.activity.customviews.viewPager.lockable

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_view_pager_lockable.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.viewPager.autoViewPager.FrmIv

@LogTag("LockableViewPagerActivity")
@IsFullScreen(false)
class LockableViewPagerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_lockable
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
            this.tvTitle?.text = LockableViewPagerActivity::class.java.simpleName
        }
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
