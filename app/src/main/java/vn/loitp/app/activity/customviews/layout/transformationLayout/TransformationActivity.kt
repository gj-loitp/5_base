package vn.loitp.app.activity.customviews.layout.transformationLayout

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.skydoves.transformationlayout.onTransformationStartContainer
import kotlinx.android.synthetic.main.activity_transformation_main.*
import vn.loitp.app.R

// https://github.com/skydoves/TransformationLayout
@LogTag("MainActivity")
@IsFullScreen(false)
class TransformationActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_transformation_main
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

        //TODO setOnNavigationItemSelectedListener
        mainBottomNavigation.setOnNavigationItemSelectedListener {
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
