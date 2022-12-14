package vn.loitp.app.activity.customviews.imageview.pinchToZoom

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_pinch_to_zoom_view_pager.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LogTag("PinchToZoomViewPagerActivity")
@IsFullScreen(false)
class PinchToZoomViewPagerActivity : BaseFontActivity() {

    private val list = ArrayList<String>()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pinch_to_zoom_view_pager
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
            this.tvTitle?.text = PinchToZoomViewPagerActivity::class.java.simpleName
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
        LUIUtil.setPullLikeIOSHorizontal(vp)
    }

    //TODO update to vp 2
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
