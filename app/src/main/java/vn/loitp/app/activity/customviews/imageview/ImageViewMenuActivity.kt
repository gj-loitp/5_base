package vn.loitp.app.activity.customviews.imageview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_imageview_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewActivity
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewWithScrollViewActivity
import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity
import vn.loitp.app.activity.customviews.imageview.comicview.ComicViewActivity
import vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview.ContinuousScrollableImageViewActivity
import vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview.FidgetSpinnerImageViewActivity
import vn.loitp.app.activity.customviews.imageview.kenburnview.KenburnViewActivity
import vn.loitp.app.activity.customviews.imageview.panoramaimageview.PanoramaImageViewActivity
import vn.loitp.app.activity.customviews.imageview.pinchtozoom.PinchToZoomViewPagerActivity
import vn.loitp.app.activity.customviews.imageview.scrollparallaximageview.ScrollParallaxImageViewActivity
import vn.loitp.app.activity.customviews.imageview.stfaiconimageviewer.ListActivity
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity
import vn.loitp.app.activity.customviews.imageview.zoomimageview.ZoomImageViewActivity

@LogTag("ImageViewMenuActivity")
@IsFullScreen(false)
class ImageViewMenuActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_menu
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ImageViewMenuActivity::class.java.simpleName
        }
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
        btTouchImageViewWithViewPager.setOnClickListener(this)
        btKenburnView.setOnClickListener(this)
        btComicView.setOnClickListener(this)
        btStfalconImageViewer.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btCirleImageView -> intent = Intent(this, CircleImageViewActivity::class.java)
            btStretchyImageView -> intent = Intent(this, StrectchyImageViewActivity::class.java)
            btTouchImageView -> intent = Intent(this, TouchImageViewActivity::class.java)
            btZoomImageView -> intent = Intent(this, ZoomImageViewActivity::class.java)
            btFidgetSpinner -> intent = Intent(this, FidgetSpinnerImageViewActivity::class.java)
            btContinuousScrollableImageView ->
                intent =
                    Intent(this, ContinuousScrollableImageViewActivity::class.java)
            btScrollParallaxImageView ->
                intent =
                    Intent(this, ScrollParallaxImageViewActivity::class.java)
            btPanoramaImageView -> intent = Intent(this, PanoramaImageViewActivity::class.java)
            btBigImageView -> intent = Intent(this, BigImageViewActivity::class.java)
            btBigImageViewWithScrollView ->
                intent =
                    Intent(this, BigImageViewWithScrollViewActivity::class.java)
            btTouchImageViewWithViewPager ->
                intent =
                    Intent(this, PinchToZoomViewPagerActivity::class.java)
            btKenburnView -> intent = Intent(this, KenburnViewActivity::class.java)
            btComicView -> intent = Intent(this, ComicViewActivity::class.java)
            btStfalconImageViewer -> intent = Intent(this, ListActivity::class.java)
        }
        intent?.let { i ->
            startActivity(i)
            LActivityUtil.tranIn(this)
        }
    }
}
