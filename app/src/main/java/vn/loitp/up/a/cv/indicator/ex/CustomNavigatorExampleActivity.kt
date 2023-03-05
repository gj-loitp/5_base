package vn.loitp.up.a.cv.indicator.ex

import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator
import vn.loitp.R
import vn.loitp.databinding.ACustomNavigatorExampleLayoutBinding
import vn.loitp.up.a.cv.indicator.ext.navi.ScaleCircleNavigator

@LogTag("CustomNavigatorExampleActivity")
@IsFullScreen(false)
class CustomNavigatorExampleActivity : BaseActivityFont() {

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

    private lateinit var binding: ACustomNavigatorExampleLayoutBinding
    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACustomNavigatorExampleLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = CustomNavigatorExampleActivity::class.java.simpleName
        }
        binding.viewPager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
        initMagicIndicator2()
        initMagicIndicator3()
    }

    private fun initMagicIndicator1() {
        val circleNavigator = CircleNavigator(this)
        circleNavigator.circleCount = CHANNELS.size
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener = CircleNavigator.OnCircleClickListener { index: Int ->
            binding.viewPager.currentItem = index
        }
        binding.magicIndicator1.navigator = circleNavigator
        ViewPagerHelper.bind(binding.magicIndicator1, binding.viewPager)
    }

    private fun initMagicIndicator2() {
        val circleNavigator = CircleNavigator(this)
        circleNavigator.isFollowTouch = false
        circleNavigator.circleCount = CHANNELS.size
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener = CircleNavigator.OnCircleClickListener { index: Int ->
            binding.viewPager.currentItem = index
        }
        binding.magicIndicator2.navigator = circleNavigator
        ViewPagerHelper.bind(binding.magicIndicator2, binding.viewPager)
    }

    private fun initMagicIndicator3() {
        val scaleCircleNavigator = ScaleCircleNavigator(this)
        scaleCircleNavigator.setCircleCount(CHANNELS.size)
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY)
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY)
        scaleCircleNavigator.setCircleClickListener(object :
            ScaleCircleNavigator.OnCircleClickListener {
            override fun onClick(index: Int) {
                binding.viewPager.currentItem = index
            }
        })
        binding.magicIndicator3.navigator = scaleCircleNavigator
        ViewPagerHelper.bind(binding.magicIndicator3, binding.viewPager)
    }
}
