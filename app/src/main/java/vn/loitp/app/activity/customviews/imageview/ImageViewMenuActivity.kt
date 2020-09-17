package vn.loitp.app.activity.customviews.imageview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_imageview_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewActivity
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewWithScrollViewActivity
import vn.loitp.app.activity.customviews.imageview.blurimageview.BlurImageViewActivity
import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity
import vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview.ContinuousScrollableImageViewActivity
import vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview.FidgetSpinnerImageViewActivity
import vn.loitp.app.activity.customviews.imageview.kenburnview.KenburnViewActivity
import vn.loitp.app.activity.customviews.imageview.panoramaimageview.PanoramaImageViewActivity
import vn.loitp.app.activity.customviews.imageview.pinchtozoom.PinchToZoomActivity
import vn.loitp.app.activity.customviews.imageview.pinchtozoom.PinchToZoomViewPagerActivity
import vn.loitp.app.activity.customviews.imageview.scrollparallaximageview.ScrollParallaxImageViewActivity
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity
import vn.loitp.app.activity.customviews.imageview.zoomimageview.ZoomImageViewActivity

@LayoutId(R.layout.activity_imageview_menu)
class ImageViewMenuActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btBlurImageView.setOnClickListener(this)
        btCirleImageView.setOnClickListener(this)
        btStretchyImageView.setOnClickListener(this)
        btTouchImageView.setOnClickListener(this)
        btZoomImageView.setOnClickListener(this)
        btFidgetSpinner.setOnClickListener(this)
        btContinuousScrollableImageView.setOnClickListener(this)
        btScrollParallaxImageView.setOnClickListener(this)
        btPanoramaImageView.setOnClickListener(this)
        btBigImageView.setOnClickListener(this)
        btBigImageViewWithScrollView.setOnClickListener(this)
        btPinchToZoomWithViewPager.setOnClickListener(this)
        btPinchToZoom.setOnClickListener(this)
        btKenburnView.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btBlurImageView -> intent = Intent(activity, BlurImageViewActivity::class.java)
            btCirleImageView -> intent = Intent(activity, CircleImageViewActivity::class.java)
            btStretchyImageView -> intent = Intent(activity, StrectchyImageViewActivity::class.java)
            btTouchImageView -> intent = Intent(activity, TouchImageViewActivity::class.java)
            btZoomImageView -> intent = Intent(activity, ZoomImageViewActivity::class.java)
            btFidgetSpinner -> intent = Intent(activity, FidgetSpinnerImageViewActivity::class.java)
            btContinuousScrollableImageView -> intent = Intent(activity, ContinuousScrollableImageViewActivity::class.java)
            btScrollParallaxImageView -> intent = Intent(activity, ScrollParallaxImageViewActivity::class.java)
            btPanoramaImageView -> intent = Intent(activity, PanoramaImageViewActivity::class.java)
            btBigImageView -> intent = Intent(activity, BigImageViewActivity::class.java)
            btBigImageViewWithScrollView -> intent = Intent(activity, BigImageViewWithScrollViewActivity::class.java)
            btPinchToZoom -> intent = Intent(activity, PinchToZoomActivity::class.java)
            btPinchToZoomWithViewPager -> intent = Intent(activity, PinchToZoomViewPagerActivity::class.java)
            btKenburnView -> intent = Intent(activity, KenburnViewActivity::class.java)
        }
        intent?.let { i ->
            startActivity(i)
            LActivityUtil.tranIn(activity)
        }
    }
}
