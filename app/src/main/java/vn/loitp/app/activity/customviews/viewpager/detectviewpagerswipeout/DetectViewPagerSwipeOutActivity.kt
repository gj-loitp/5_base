package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_view_pager_detect_swipe_out.*
import vn.loitp.app.R
import java.util.*

class DetectViewPagerSwipeOutActivity : BaseFontActivity() {
    private val vpPhotoList: MutableList<VPPhoto> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val max = 3
        for (i in 0 until max) {
            val vpPhoto = VPPhoto()
            vpPhoto.color = LStoreUtil.randomColor
            vpPhoto.string = "Page " + i + "/" + (max - 1)
            vpPhotoList.add(vpPhoto)
        }
        LUIUtil.setPullLikeIOSHorizontal(viewPager, object : LUIUtil.Callback {
            override fun onUpOrLeft(offset: Float) {
                logD("onUpOrLeft $offset")
                showShort("Detect Left")
            }

            override fun onUpOrLeftRefresh(offset: Float) {
                logD("onUpOrLeftRefresh $offset")
            }

            override fun onDownOrRight(offset: Float) {
                logD("onDownOrRight $offset")
                showShort("Detect Right")
            }

            override fun onDownOrRightRefresh(offset: Float) {
                logD("onDownOrRightRefresh $offset")
            }
        })
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_detect_swipe_out
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
