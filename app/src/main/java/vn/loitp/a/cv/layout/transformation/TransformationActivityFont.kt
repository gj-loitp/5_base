package vn.loitp.a.cv.layout.transformation

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.skydoves.transformationlayout.onTransformationStartContainer
import kotlinx.android.synthetic.main.a_transformation_main.*
import vn.loitp.R

// https://github.com/skydoves/TransformationLayout
@LogTag("TransformationActivity")
@IsFullScreen(false)
class TransformationActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_transformation_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        onTransformationStartContainer()
        super.onCreate(savedInstanceState)

        with(mainViewPager) {
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
                        mainBottomNavigation.menu.getItem(position).isChecked = true
                    }
                }
            )
        }

        mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    mainViewPager.currentItem = 0
                }
                R.id.actionLibray -> {
                    mainViewPager.currentItem = 1
                }
                R.id.actionRadio -> {
                    mainViewPager.currentItem = 2
                }
            }
            true
        }
    }
}
