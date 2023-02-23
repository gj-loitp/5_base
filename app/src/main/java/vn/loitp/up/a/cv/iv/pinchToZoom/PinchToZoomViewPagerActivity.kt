package vn.loitp.up.a.cv.iv.pinchToZoom

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setPullLikeIOSHorizontal
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AIvPinchToZoomVpBinding
import vn.loitp.up.common.Constants

@LogTag("PinchToZoomViewPagerActivity")
@IsFullScreen(false)
class PinchToZoomViewPagerActivity : BaseActivityFont() {

    private val list = ArrayList<String>()
    private lateinit var binding: AIvPinchToZoomVpBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvPinchToZoomVpBinding.inflate(layoutInflater)
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
        binding.vp.adapter = SamplePagerAdapter(supportFragmentManager)
        binding.vp.setPullLikeIOSHorizontal()
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
