package vn.loitp.app.activity.customviews.imageview.pinchtozoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_pinch_to_zoom_vp.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants
import java.util.*

class PinchToZoomViewPagerActivity : BaseFontActivity() {
    private val list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list.add(Constants.URL_IMG)
        list.add(Constants.URL_IMG_1)
        list.add(Constants.URL_IMG_2)
        list.add(Constants.URL_IMG_LARGE)
        list.add(Constants.URL_IMG_GIFT)
        list.add(Constants.URL_IMG_LARGE_LAND_M)
        list.add(Constants.URL_IMG_LONG)
        list.add(Constants.URL_IMG_LARGE_PORTRAIT_O)
        vp.adapter = SamplePagerAdapter(supportFragmentManager)
        LUIUtil.setPullLikeIOSHorizontal(vp);
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pinch_to_zoom_vp
    }

    private inner class SamplePagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            val f = FrmIvPinchToZoom()
            val bundle = Bundle()
            bundle.putString(FrmIvPinchToZoom.KEY_URL, list[position])
            f.arguments = bundle
            return f
        }

        override fun getCount(): Int {
            return list.size
        }
    }
}
