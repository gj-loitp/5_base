package vn.loitp.app.a.customviews.indicator.example

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_scrollable_indicator_example_layout.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import vn.loitp.R
import vn.loitp.app.a.customviews.indicator.ext.titles.ColorFlipPagerTitleView
import vn.loitp.app.a.customviews.indicator.ext.titles.ScaleTransitionPagerTitleView

@LogTag("ScrollableTabExampleActivity")
@IsFullScreen(false)
class ScrollableTabExampleActivity : BaseFontActivity() {

    companion object {
        private val CHANNELS = arrayOf(
            "CUPCAKE",
            "DONUT",
            "ECLAIR",
            "GINGERBREAD",
            "HONEYCOMB",
            "ICE_CREAM_SANDWICH",
            "JELLY_BEAN",
            "KITKAT",
            "LOLLIPOP",
            "M",
            "NOUGAT"
        )
    }

    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scrollable_indicator_example_layout
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
            this.tvTitle?.text = ScrollableTabExampleActivity::class.java.simpleName
        }

        viewPager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
        initMagicIndicator3()
        initMagicIndicator4()
        initMagicIndicator5()
        initMagicIndicator6()
        initMagicIndicator7()
        initMagicIndicator8()
        initMagicIndicator9()
    }

    private fun initMagicIndicator1() {
        magicIndicator1.setBackgroundColor(Color.parseColor("#d43d3d"))
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isSkimOver = true
        val padding = UIUtil.getScreenWidth(this) / 2
        commonNavigator.rightPadding = padding
        commonNavigator.leftPadding = padding

        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = mDataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#f2c4c4")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        }
        magicIndicator1.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator1, viewPager)
    }

    private fun initMagicIndicator2() {
        magicIndicator2.setBackgroundColor(Color.parseColor("#00c853"))
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#c8e6c9")
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.textSize = 12f
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.yOffset = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.setColors(Color.parseColor("#ffffff"))
                return indicator
            }
        }
        magicIndicator2.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator2, viewPager)
    }

    private fun initMagicIndicator3() {
        magicIndicator3.setBackgroundColor(Color.BLACK)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView =
                    ColorTransitionPagerTitleView(context)
                simplePagerTitleView.normalColor = Color.GRAY
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                linePagerIndicator.setColors(Color.WHITE)
                return linePagerIndicator
            }
        }
        magicIndicator3.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator3, viewPager)
    }

    private fun initMagicIndicator4() {
        magicIndicator4.setBackgroundColor(Color.parseColor("#455a64"))
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView =
                    ColorTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#88ffffff")
                simplePagerTitleView.selectedColor = Color.WHITE
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.setColors(Color.parseColor("#40c4ff"))
                return indicator
            }
        }
        magicIndicator4.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator4, viewPager)
    }

    private fun initMagicIndicator5() {
        magicIndicator5.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.8f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView =
                    ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.normalColor = Color.parseColor("#616161")
                simplePagerTitleView.selectedColor = Color.parseColor("#f57c00")
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.startInterpolator = AccelerateInterpolator()
                linePagerIndicator.endInterpolator = DecelerateInterpolator(1.6f)
                linePagerIndicator.yOffset = UIUtil.dip2px(context, 39.0).toFloat()
                linePagerIndicator.lineHeight = UIUtil.dip2px(context, 1.0).toFloat()
                linePagerIndicator.setColors(Color.parseColor("#f57c00"))
                return linePagerIndicator
            }
        }
        magicIndicator5.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator5, viewPager)
    }

    private fun initMagicIndicator6() {
        magicIndicator6.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView =
                    ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.textSize = 18f
                simplePagerTitleView.normalColor = Color.GRAY
                simplePagerTitleView.selectedColor = Color.BLACK
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val bezierPagerIndicator = BezierPagerIndicator(context)
                bezierPagerIndicator.setColors(
                    Color.parseColor("#ff4a42"),
                    Color.parseColor("#fcde64"),
                    Color.parseColor("#73e8f4"),
                    Color.parseColor("#76b0ff"),
                    Color.parseColor("#c683fe")
                )
                return bezierPagerIndicator
            }
        }
        magicIndicator6.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator6, viewPager)
    }

    private fun initMagicIndicator7() {
        magicIndicator7.setBackgroundColor(Color.parseColor("#fafafa"))
        val commonNavigator7 = CommonNavigator(this)
        commonNavigator7.scrollPivotX = 0.65f
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView = ColorFlipPagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#9e9e9e")
                simplePagerTitleView.selectedColor = Color.parseColor("#00c853")
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = UIUtil.dip2px(context, 6.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 10.0).toFloat()
                indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(Color.parseColor("#00c853"))
                return indicator
            }
        }
        magicIndicator7.navigator = commonNavigator7
        ViewPagerHelper.bind(magicIndicator7, viewPager)
    }

    private fun initMagicIndicator8() {
        magicIndicator8.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.35f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#333333")
                simplePagerTitleView.selectedColor = Color.parseColor("#e94220")
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val wrapPagerIndicator = WrapPagerIndicator(context)
                wrapPagerIndicator.fillColor = Color.parseColor("#ebe4e3")
                return wrapPagerIndicator
            }
        }
        magicIndicator8.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator8, viewPager)
    }

    private fun initMagicIndicator9() {
        magicIndicator9.setBackgroundColor(Color.WHITE)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.scrollPivotX = 0.15f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mDataList[index]
                simplePagerTitleView.normalColor = Color.parseColor("#333333")
                simplePagerTitleView.selectedColor = Color.parseColor("#e94220")
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val triangularPagerIndicator = TriangularPagerIndicator(context)
                triangularPagerIndicator.lineColor = Color.parseColor("#e94220")
                return triangularPagerIndicator
            }
        }
        magicIndicator9.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator9, viewPager)
    }
}
