package vn.loitp.a.cv.simpleRatingBar

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_simple_rating_bar.*
import vn.loitp.R
import vn.loitp.app.EmptyActivity

@LogTag("SimpleRatingBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class SimpleRatingBarActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_simple_rating_bar
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
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/williamyyu/SimpleRatingBar"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
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
