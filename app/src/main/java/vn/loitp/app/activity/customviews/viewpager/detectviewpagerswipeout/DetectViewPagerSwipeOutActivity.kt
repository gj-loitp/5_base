package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_view_pager_detect_swipe_out.*
import vn.loitp.app.R
import java.util.*

@LogTag("DetectViewPagerSwipeOutActivity")
@IsFullScreen(false)
class DetectViewPagerSwipeOutActivity : BaseFontActivity() {
    private val vpPhotoList: MutableList<VPPhoto> = ArrayList()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_detect_swipe_out
    }

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
        LUIUtil.setPullLikeIOSHorizontal(
            viewPager = viewPager,
            onUpOrLeft = { offset ->
                logD("onUpOrLeft $offset")
                showShortInformation("Detect Left")
            },
            onUpOrLeftRefresh = { offset ->
                logD("onUpOrLeftRefresh $offset")
            },
            onDownOrRight = { offset ->
                logD("onDownOrRight $offset")
                showShortInformation("Detect Right")
            },
            onDownOrRightRefresh = { offset ->
                logD("onDownOrRightRefresh $offset")
            }
        )
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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
