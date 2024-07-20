package vn.loitp.up.a.cv.layout.transformation

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.skydoves.transformationlayout.onTransformationStartContainer
import vn.loitp.R
import vn.loitp.databinding.ATransformationMainBinding

// https://github.com/skydoves/TransformationLayout
@LogTag("TransformationActivity")
@IsFullScreen(false)
class TransformationActivity : BaseActivityFont() {

    private lateinit var binding: ATransformationMainBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {

        onTransformationStartContainer()
        super.onCreate(savedInstanceState)

        binding = ATransformationMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.mainViewPager) {
            adapter = TransformationPagerAdapter(supportFragmentManager)
            offscreenPageLimit = 3
            addOnPageChangeListener(
                object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) = Unit
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) = Unit

                    override fun onPageSelected(position: Int) {
                        binding.mainBottomNavigation.menu.getItem(position).isChecked = true
                    }
                }
            )
        }

        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    binding.mainViewPager.currentItem = 0
                }
                R.id.actionLibray -> {
                    binding.mainViewPager.currentItem = 1
                }
                R.id.actionRadio -> {
                    binding.mainViewPager.currentItem = 2
                }
            }
            true
        }
    }
}
