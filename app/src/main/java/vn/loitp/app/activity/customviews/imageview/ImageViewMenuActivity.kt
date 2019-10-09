package vn.loitp.app.activity.customviews.imageview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_imageview.*

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewActivity
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewWithScrollViewActivity
import vn.loitp.app.activity.customviews.imageview.blurimageview.BlurImageViewActivity
import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity
import vn.loitp.app.activity.customviews.imageview.circularroundrectimageview.CircularRoundRectImageViewActivity
import vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview.ContinuousScrollableImageViewActivity
import vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview.FidgetSpinnerImageViewActivity
import vn.loitp.app.activity.customviews.imageview.panoramaimageview.PanoramaImageViewActivity
import vn.loitp.app.activity.customviews.imageview.pinchtozoom.PinchToZoomActivity
import vn.loitp.app.activity.customviews.imageview.scrollparallaximageview.ScrollParallaxImageViewActivity
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity
import vn.loitp.app.activity.customviews.imageview.zoomimageview.ZoomImageViewActivity

class ImageViewMenuActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_blur_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_cirle_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_stretchy_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_touch_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_zoom_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_fidgetspinner).setOnClickListener(this)
        findViewById<View>(R.id.bt_cirlularroundrect_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_continuous_scrollable_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_scroll_parallax_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_panorama_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_big_imageview).setOnClickListener(this)
        findViewById<View>(R.id.bt_big_imageview_with_scroll_view).setOnClickListener(this)
        btPinchToZoom.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_imageview
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_blur_imageview -> intent = Intent(activity, BlurImageViewActivity::class.java)
            R.id.bt_cirle_imageview -> intent = Intent(activity, CircleImageViewActivity::class.java)
            R.id.bt_stretchy_imageview -> intent = Intent(activity, StrectchyImageViewActivity::class.java)
            R.id.bt_touch_imageview -> intent = Intent(activity, TouchImageViewActivity::class.java)
            R.id.bt_zoom_imageview -> intent = Intent(activity, ZoomImageViewActivity::class.java)
            R.id.bt_fidgetspinner -> intent = Intent(activity, FidgetSpinnerImageViewActivity::class.java)
            R.id.bt_cirlularroundrect_imageview -> intent = Intent(activity, CircularRoundRectImageViewActivity::class.java)
            R.id.bt_continuous_scrollable_imageview -> intent = Intent(activity, ContinuousScrollableImageViewActivity::class.java)
            R.id.bt_scroll_parallax_imageview -> intent = Intent(activity, ScrollParallaxImageViewActivity::class.java)
            R.id.bt_panorama_imageview -> intent = Intent(activity, PanoramaImageViewActivity::class.java)
            R.id.bt_big_imageview -> intent = Intent(activity, BigImageViewActivity::class.java)
            R.id.bt_big_imageview_with_scroll_view -> intent = Intent(activity, BigImageViewWithScrollViewActivity::class.java)
            R.id.btPinchToZoom -> intent = Intent(activity, PinchToZoomActivity::class.java)
        }
        intent?.let { i ->
            startActivity(i)
            LActivityUtil.tranIn(activity)
        }
    }
}
