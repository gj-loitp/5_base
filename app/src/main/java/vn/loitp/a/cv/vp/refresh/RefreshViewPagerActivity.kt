package vn.loitp.a.cv.vp.refresh

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
import kotlinx.android.synthetic.main.a_vp_refresh.*
import vn.loitp.R

@LogTag("RefreshViewPagerActivity")
@IsFullScreen(false)
class RefreshViewPagerActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_vp_refresh
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
            this.tvTitle?.text = RefreshViewPagerActivity::class.java.simpleName
        }
        vp.adapter = SamplePagerAdapter(supportFragmentManager)
        vp.setPullLikeIOSHorizontal()
        tabLayout.setupWithViewPager(vp)
        tabLayout.changeTabsFont(FONT_PATH)
    }

    @Suppress("DEPRECATION")
    private inner class SamplePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_SET_USER_VISIBLE_HINT) {
        override fun getItem(position: Int): Fragment {
            val frmRefresh = FrmRefresh()
            val bundle = Bundle()
            bundle.putInt(FrmRefresh.KEY_POSITION, position)
            frmRefresh.arguments = bundle
            return frmRefresh
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence {
            return "Page Title $position"
        }
    }
}
