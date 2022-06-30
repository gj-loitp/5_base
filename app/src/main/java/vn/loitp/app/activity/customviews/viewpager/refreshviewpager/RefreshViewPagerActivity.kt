package vn.loitp.app.activity.customviews.viewpager.refreshviewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_viewpager_refresh.*
import vn.loitp.app.R

@LogTag("RefreshViewPagerActivity")
@IsFullScreen(false)
class RefreshViewPagerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_viewpager_refresh
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        vp.adapter = SamplePagerAdapter(supportFragmentManager)
        LUIUtil.setPullLikeIOSHorizontal(vp)
        tabLayout.setupWithViewPager(vp)
        LUIUtil.changeTabsFont(tabLayout, Constants.FONT_PATH)
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
