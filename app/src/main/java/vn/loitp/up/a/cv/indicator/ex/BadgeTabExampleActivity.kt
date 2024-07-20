package vn.loitp.up.a.cv.indicator.ex

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule
import vn.loitp.R
import vn.loitp.databinding.ABadgeTabExampleLayoutBinding
import vn.loitp.up.a.cv.indicator.ext.titles.ScaleTransitionPagerTitleView

@LogTag("BadgeTabExampleActivity")
@IsFullScreen(false)
class BadgeTabExampleActivity : BaseActivityFont() {

    companion object {
        private val CHANNELS = arrayOf("KITKAT", "NOUGAT", "DONUT")
    }

    private lateinit var binding: ABadgeTabExampleLayoutBinding
    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ABadgeTabExampleLayoutBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = BadgeTabExampleActivity::class.java.simpleName
        }
        binding.viewPager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
        initMagicIndicator3()
        initMagicIndicator4()
    }

    private fun initMagicIndicator1() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            @SuppressLint("InflateParams")
            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val simplePagerTitleView: SimplePagerTitleView =
                    ColorTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#88ffffff")
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.setOnClickListener {
                    binding.viewPager.currentItem = index
                    badgePagerTitleView.badgeView = null // cancel badge when click tab
                }
                badgePagerTitleView.innerPagerTitleView = simplePagerTitleView

                // setup badge
                if (index != 2) {
                    val badgeTextView = LayoutInflater.from(context)
                        .inflate(R.layout.simple_count_badge_layout, null) as TextView
                    badgeTextView.text = "${index + 1}"
                    badgePagerTitleView.badgeView = badgeTextView
                } else {
                    val badgeImageView = LayoutInflater.from(context)
                        .inflate(R.layout.simple_red_dot_badge_layout, null) as ImageView
                    badgePagerTitleView.badgeView = badgeImageView
                }

                // set badge position
                when (index) {
                    0 -> {
                        badgePagerTitleView.xBadgeRule =
                            BadgeRule(BadgeAnchor.CONTENT_LEFT, -UIUtil.dip2px(context, 6.0))
                        badgePagerTitleView.yBadgeRule = BadgeRule(BadgeAnchor.CONTENT_TOP, 0)
                    }
                    1 -> {
                        badgePagerTitleView.xBadgeRule =
                            BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 6.0))
                        badgePagerTitleView.yBadgeRule = BadgeRule(BadgeAnchor.CONTENT_TOP, 0)
                    }
                    2 -> {
                        badgePagerTitleView.xBadgeRule =
                            BadgeRule(BadgeAnchor.CENTER_X, -UIUtil.dip2px(context, 3.0))
                        badgePagerTitleView.yBadgeRule =
                            BadgeRule(BadgeAnchor.CONTENT_BOTTOM, UIUtil.dip2px(context, 2.0))
                    }
                }

                // don't cancel badge when tab selected
                badgePagerTitleView.isAutoCancelBadge = false
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.setColors(Color.parseColor("#40c4ff"))
                return linePagerIndicator
            }
        }
        binding.magicIndicator1.navigator = commonNavigator
        val titleContainer = commonNavigator.titleContainer // must after setNavigator
        titleContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerPadding = UIUtil.dip2px(this, 15.0)
        titleContainer.dividerDrawable = getDrawable(com.loitp.R.drawable.simple_splitter)
        ViewPagerHelper.bind(binding.magicIndicator1, binding.viewPager)
    }

    private fun initMagicIndicator2() {
        binding.magicIndicator2.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            @SuppressLint("InflateParams")
            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val simplePagerTitleView: SimplePagerTitleView =
                    ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.normalColor = Color.parseColor("#616161")
                simplePagerTitleView.selectedColor = Color.parseColor("#f57c00")
                simplePagerTitleView.setOnClickListener {
                    binding.viewPager.currentItem = index
                }
                badgePagerTitleView.innerPagerTitleView = simplePagerTitleView

                // setup badge
                if (index == 1) {
                    val badgeImageView = LayoutInflater.from(context)
                        .inflate(R.layout.simple_red_dot_badge_layout, null) as ImageView
                    badgePagerTitleView.badgeView = badgeImageView
                    badgePagerTitleView.xBadgeRule =
                        BadgeRule(BadgeAnchor.CENTER_X, -UIUtil.dip2px(context, 3.0))
                    badgePagerTitleView.yBadgeRule =
                        BadgeRule(BadgeAnchor.CONTENT_BOTTOM, UIUtil.dip2px(context, 2.0))
                }

                // cancel badge when click tab, default true
                badgePagerTitleView.isAutoCancelBadge = true
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(1.6f)
                indicator.yOffset = UIUtil.dip2px(context, 39.0).toFloat()
                indicator.lineHeight = UIUtil.dip2px(context, 1.0).toFloat()
                indicator.setColors(Color.parseColor("#f57c00"))
                return indicator
            }

            override fun getTitleWeight(context: Context, index: Int): Float {
                return when (index) {
                    0 -> {
                        2.0f
                    }
                    1 -> {
                        1.2f
                    }
                    else -> {
                        1.0f
                    }
                }
            }
        }
        binding.magicIndicator2.navigator = commonNavigator
        ViewPagerHelper.bind(binding.magicIndicator2, binding.viewPager)
    }

    private fun initMagicIndicator3() {
        binding.magicIndicator3.setBackgroundResource(com.loitp.R.drawable.round_indicator_bg)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = mDataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#e94220")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener {
                    binding.viewPager.currentItem = index
                }
                badgePagerTitleView.innerPagerTitleView = clipPagerTitleView
                return badgePagerTitleView
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
        binding.magicIndicator3.navigator = commonNavigator
        ViewPagerHelper.bind(binding.magicIndicator3, binding.viewPager)
    }

    private fun initMagicIndicator4() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val simplePagerTitleView: SimplePagerTitleView =
                    ColorTransitionPagerTitleView(context)
                simplePagerTitleView.normalColor = Color.GRAY
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setOnClickListener {
                    binding.viewPager.currentItem = index
                }
                badgePagerTitleView.innerPagerTitleView = simplePagerTitleView
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.setColors(Color.WHITE)
                return linePagerIndicator
            }
        }
        binding.magicIndicator4.navigator = commonNavigator
        val titleContainer = commonNavigator.titleContainer // must after setNavigator
        titleContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerDrawable = object : ColorDrawable() {
            override fun getIntrinsicWidth(): Int {
                return UIUtil.dip2px(this@BadgeTabExampleActivity, 15.0)
            }
        }
        ViewPagerHelper.bind(binding.magicIndicator4, binding.viewPager)
    }
}
