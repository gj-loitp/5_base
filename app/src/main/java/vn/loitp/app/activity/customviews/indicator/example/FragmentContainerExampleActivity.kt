package vn.loitp.app.activity.customviews.indicator.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_fragment_container_example_layout.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import vn.loitp.app.R

@LogTag("FragmentContainerExampleActivity")
@IsFullScreen(false)
class FragmentContainerExampleActivity : BaseFontActivity() {

    companion object {
        private val CHANNELS = arrayOf("KITKAT", "NOUGAT", "DONUT")
    }

    private val mFragments: MutableList<Fragment> = ArrayList()
    private val mFragmentContainerHelper = FragmentContainerHelper()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fragment_container_example_layout
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = FragmentContainerExampleActivity::class.java.simpleName
        }
        initFragments()
        initMagicIndicator1()
        mFragmentContainerHelper.handlePageSelected(1, false)
        switchPages(1)
    }

    private fun switchPages(index: Int) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment: Fragment
        var i = 0
        val j = mFragments.size
        while (i < j) {
            if (i == index) {
                i++
                continue
            }
            fragment = mFragments[i]
            if (fragment.isAdded) {
                fragmentTransaction.hide(fragment)
            }
            i++
        }
        fragment = mFragments[index]
        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            fragmentTransaction.add(R.id.fragmentContainer, fragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun initFragments() {
        for (channel in CHANNELS) {
            val testFragment = TestFragment()
            val bundle = Bundle()
            bundle.putString(TestFragment.EXTRA_TEXT, channel)
            testFragment.arguments = bundle
            mFragments.add(testFragment)
        }
    }

    private fun initMagicIndicator1() {
        magicIndicator1.setBackgroundResource(R.drawable.round_indicator_bg)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return CHANNELS.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = CHANNELS[index]
                clipPagerTitleView.textColor = Color.parseColor("#e94220")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener {
                    mFragmentContainerHelper.handlePageSelected(index)
                    switchPages(index)
                }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight =
                    context.resources.getDimension(R.dimen.common_navigator_height)
                val borderWidth = UIUtil.dip2px(context, 1.0).toFloat()
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.lineHeight = lineHeight
                indicator.roundRadius = lineHeight / 2
                indicator.yOffset = borderWidth
                indicator.setColors(Color.parseColor("#bc2a2a"))
                return indicator
            }
        }
        magicIndicator1.navigator = commonNavigator
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator1)
    }
}
