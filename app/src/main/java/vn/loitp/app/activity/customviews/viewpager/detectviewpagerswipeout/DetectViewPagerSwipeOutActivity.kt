package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.interfaces.CallbackPull
import kotlinx.android.synthetic.main.activity_view_pager_detect_swipe_out.*
import vn.loitp.app.R
import java.util.*

@LayoutId(R.layout.activity_view_pager_detect_swipe_out)
@LogTag("DetectViewPagerSwipeOutActivity")
@IsFullScreen(false)
class DetectViewPagerSwipeOutActivity : BaseFontActivity() {
    private val vpPhotoList: MutableList<VPPhoto> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val max = 3
        for (i in 0 until max) {
            val vpPhoto = VPPhoto()
            vpPhoto.color = LStoreUtil.randomColor
            vpPhoto.string = "Page " + i + "/" + (max - 1)
            vpPhotoList.add(vpPhoto)
        }
        LUIUtil.setPullLikeIOSHorizontal(viewPager, object : CallbackPull {
            override fun onUpOrLeft(offset: Float) {
                logD("onUpOrLeft $offset")
                showShortInformation("Detect Left")
            }

            override fun onUpOrLeftRefresh(offset: Float) {
                logD("onUpOrLeftRefresh $offset")
            }

            override fun onDownOrRight(offset: Float) {
                logD("onDownOrRight $offset")
                showShortInformation("Detect Right")
            }

            override fun onDownOrRightRefresh(offset: Float) {
                logD("onDownOrRightRefresh $offset")
            }
        })
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    private inner class ViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            val bundle = Bundle()
            bundle.putSerializable("vpphoto", vpPhotoList[position])
            val fragment = FrmPhoto()
            fragment.arguments = bundle
            return fragment
        }

        override fun getCount(): Int {
            return 3
        }
    }
}
