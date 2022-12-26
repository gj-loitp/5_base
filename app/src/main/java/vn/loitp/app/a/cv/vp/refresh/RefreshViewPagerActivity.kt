package vn.loitp.app.a.cv.vp.refresh

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_view_pager_refresh.*
import vn.loitp.R

@LogTag("RefreshViewPagerActivity")
@IsFullScreen(false)
class RefreshViewPagerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_refresh
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
            this.tvTitle?.text = RefreshViewPagerActivity::class.java.simpleName
        }
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
