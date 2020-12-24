package vn.loitp.app.activity.customviews.indicator.example

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_custom_navigator_example_layout.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.indicator.ext.navigator.ScaleCircleNavigator
import java.util.*

@LogTag("CustomNavigatorExampleActivity")
@IsFullScreen(false)
class CustomNavigatorExampleActivity : BaseFontActivity() {

    companion object {
        private val CHANNELS = arrayOf("CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT")
    }

    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_navigator_example_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
        initMagicIndicator3()
    }

    private fun initMagicIndicator1() {
        val circleNavigator = CircleNavigator(this)
        circleNavigator.circleCount = CHANNELS.size
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener = CircleNavigator.OnCircleClickListener { index: Int ->
            viewPager.currentItem = index
        }
        magicIndicator1.navigator = circleNavigator
        ViewPagerHelper.bind(magicIndicator1, viewPager)
    }

    private fun initMagicIndicator2() {
        val circleNavigator = CircleNavigator(this)
        circleNavigator.isFollowTouch = false
        circleNavigator.circleCount = CHANNELS.size
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener = CircleNavigator.OnCircleClickListener { index: Int ->
            viewPager.currentItem = index
        }
        magicIndicator2.navigator = circleNavigator
        ViewPagerHelper.bind(magicIndicator2, viewPager)
    }

    private fun initMagicIndicator3() {
        val scaleCircleNavigator = ScaleCircleNavigator(this)
        scaleCircleNavigator.setCircleCount(CHANNELS.size)
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY)
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY)
        scaleCircleNavigator.setCircleClickListener(object : ScaleCircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                viewPager.currentItem = index
            }
        })
        magicIndicator3.navigator = scaleCircleNavigator
        ViewPagerHelper.bind(magicIndicator3, viewPager)
    }
}
