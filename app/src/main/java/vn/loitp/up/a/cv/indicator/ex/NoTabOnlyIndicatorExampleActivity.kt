package vn.loitp.up.a.cv.indicator.ex

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView
import vn.loitp.R
import vn.loitp.databinding.ANoTabOnlyIndicatorExampleLayoutBinding

@LogTag("NoTabOnlyIndicatorExampleActivity")
@IsFullScreen(false)
class NoTabOnlyIndicatorExampleActivity : BaseActivityFont() {

    companion object {
        private val CHANNELS =
            arrayOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "NOUGAT", "DONUT")
    }

    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)
    private lateinit var binding: ANoTabOnlyIndicatorExampleLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ANoTabOnlyIndicatorExampleLayoutBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = NoTabOnlyIndicatorExampleActivity::class.java.simpleName
        }
        binding.viewPager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
    }

    private fun initMagicIndicator1() {
        binding.magicIndicator1.setBackgroundColor(Color.LTGRAY)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return DummyPagerTitleView(context)
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                val lineHeight = context.resources.getDimension(R.dimen.small_navigator_height)
                linePagerIndicator.lineHeight = lineHeight
                linePagerIndicator.setColors(Color.parseColor("#40c4ff"))
                return linePagerIndicator
            }
        }
        binding.magicIndicator1.navigator = commonNavigator
        ViewPagerHelper.bind(binding.magicIndicator1, binding.viewPager)
    }

    private fun initMagicIndicator2() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return DummyPagerTitleView(context)
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val triangularPagerIndicator = TriangularPagerIndicator(context)
                triangularPagerIndicator.isReverse = true
                val smallNavigatorHeight =
                    context.resources.getDimension(R.dimen.small_navigator_height)
                triangularPagerIndicator.lineHeight = UIUtil.dip2px(context, 2.0)
                triangularPagerIndicator.triangleHeight = smallNavigatorHeight.toInt()
                triangularPagerIndicator.lineColor = Color.parseColor("#e94220")
                return triangularPagerIndicator
            }
        }
        binding.magicIndicator2.navigator = commonNavigator
        ViewPagerHelper.bind(binding.magicIndicator2, binding.viewPager)
    }
}
