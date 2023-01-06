package vn.loitp.a.cv.iv.pinchToZoom

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setPullLikeIOSHorizontal
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_iv_pinch_to_zoom_vp.*
import vn.loitp.R
import vn.loitp.common.Constants

@LogTag("PinchToZoomViewPagerActivity")
@IsFullScreen(false)
class PinchToZoomViewPagerActivityFont : BaseActivityFont() {

    private val list = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_pinch_to_zoom_vp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = PinchToZoomViewPagerActivityFont::class.java.simpleName
        }
        list.add(Constants.URL_IMG)
        list.add(Constants.URL_IMG_1)
        list.add(Constants.URL_IMG_2)
        list.add(Constants.URL_IMG_LARGE)
        list.add(Constants.URL_IMG_GIFT)
        list.add(Constants.URL_IMG_LARGE_LAND_M)
        list.add(Constants.URL_IMG_LONG)
        list.add(Constants.URL_IMG_LARGE_PORTRAIT_O)
        vp.adapter = SamplePagerAdapter(supportFragmentManager)
        vp.setPullLikeIOSHorizontal()
    }

    private inner class SamplePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            val frmIvPinchToZoom = FrmIvPinchToZoom()
            val bundle = Bundle()
            bundle.putString(FrmIvPinchToZoom.KEY_URL, list[position])
            frmIvPinchToZoom.arguments = bundle
            return frmIvPinchToZoom
        }

        override fun getCount(): Int {
            return list.size
        }
    }
}
