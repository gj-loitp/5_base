package vn.loitp.up.a.cv.indicator.ex

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
import vn.loitp.R
import vn.loitp.databinding.ActivityLoadCustomLayoutExampleBinding

@LogTag("LoadCustomLayoutExampleActivity")
@IsFullScreen(false)
class LoadCustomLayoutExampleActivity : BaseActivityFont() {

    companion object {
        private val CHANNELS = arrayOf("NOUGAT", "DONUT", "ECLAIR", "KITKAT")
    }

    private lateinit var binding: ActivityLoadCustomLayoutExampleBinding

    private val mDataList = mutableListOf(*CHANNELS)
    private val mExamplePagerAdapter = ExamplePagerAdapter(mDataList)

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoadCustomLayoutExampleBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = LoadCustomLayoutExampleActivity::class.java.simpleName
        }
        binding.viewPager.adapter = mExamplePagerAdapter
        initMagicIndicator1()
    }

    private fun initMagicIndicator1() {
        binding.magicIndicator1.setBackgroundColor(Color.BLACK)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mDataList.size
            }

            @SuppressLint("InflateParams")
            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val commonPagerTitleView = CommonPagerTitleView(context)

                // load custom layout
                val customLayout =
                    LayoutInflater.from(context).inflate(R.layout.layout_simple_pager_title, null)
                val ivTitle = customLayout.findViewById<ImageView>(R.id.ivTitle)
                val tvTitle = customLayout.findViewById<TextView>(R.id.tvTitle)
                ivTitle.setImageResource(R.drawable.ic_launcher_loitp)
                tvTitle.text = mDataList[index]
                commonPagerTitleView.setContentView(customLayout)
                commonPagerTitleView.onPagerTitleChangeListener =
                    object : OnPagerTitleChangeListener {
                        override fun onSelected(index: Int, totalCount: Int) {
                            tvTitle.setTextColor(Color.WHITE)
                        }

                        override fun onDeselected(index: Int, totalCount: Int) {
                            tvTitle.setTextColor(Color.LTGRAY)
                        }

                        override fun onLeave(
                            index: Int,
                            totalCount: Int,
                            leavePercent: Float,
                            leftToRight: Boolean
                        ) {
                            ivTitle.scaleX = 1.3f + (0.8f - 1.3f) * leavePercent
                            ivTitle.scaleY = 1.3f + (0.8f - 1.3f) * leavePercent
                        }

                        override fun onEnter(
                            index: Int,
                            totalCount: Int,
                            enterPercent: Float,
                            leftToRight: Boolean
                        ) {
                            ivTitle.scaleX = 0.8f + (1.3f - 0.8f) * enterPercent
                            ivTitle.scaleY = 0.8f + (1.3f - 0.8f) * enterPercent
                        }
                    }
                commonPagerTitleView.setOnClickListener {
                    binding.viewPager.currentItem = index
                }
                return commonPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        }
        binding.magicIndicator1.navigator = commonNavigator
        ViewPagerHelper.bind(binding.magicIndicator1, binding.viewPager)
    }
}
