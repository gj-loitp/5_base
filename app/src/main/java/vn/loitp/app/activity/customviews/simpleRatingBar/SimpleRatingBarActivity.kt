package vn.loitp.app.activity.customviews.simpleRatingBar

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_simple_rating_bar.*
import vn.loitp.app.R
import vn.loitp.app.activity.EmptyActivity

@LogTag("SimpleRatingBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class SimpleRatingBarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_simple_rating_bar
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
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/williamyyu/SimpleRatingBar"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }
        viewPager.adapter = SamplePagerAdapter(supportFragmentManager)
        tabLayout.addTab(tabLayout.newTab().setText("Animation Demo"))
        tabLayout.addTab(tabLayout.newTab().setText("RecyclerView Demo"))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
    }

    //TODO convert viewpager 2
    inner class SamplePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {
        private val fragments: MutableList<Fragment>

        init {
            fragments = ArrayList()
            fragments.add(SRBDemoFragment())
            fragments.add(SRBListFragment())
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

    }
}
