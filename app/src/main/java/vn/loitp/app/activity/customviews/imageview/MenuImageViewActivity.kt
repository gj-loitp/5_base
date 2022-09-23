package vn.loitp.app.activity.customviews.imageview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_image_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.imageview.bigImageView.BigImageViewActivity
import vn.loitp.app.activity.customviews.imageview.bigImageView.BigImageViewWithScrollViewActivity
import vn.loitp.app.activity.customviews.imageview.circleImageView.CircleImageViewActivity
import vn.loitp.app.activity.customviews.imageview.comicView.ComicViewActivity
import vn.loitp.app.activity.customviews.imageview.continuousScrollableImageView.ContinuousScrollableImageViewActivity
import vn.loitp.app.activity.customviews.imageview.fidgetSpinner.FidgetSpinnerImageViewActivity
import vn.loitp.app.activity.customviews.imageview.kenburnView.KenburnViewActivity
import vn.loitp.app.activity.customviews.imageview.panorama.PanoramaImageViewActivity
import vn.loitp.app.activity.customviews.imageview.pinchToZoom.PinchToZoomViewPagerActivity
import vn.loitp.app.activity.customviews.imageview.scrollParallax.ScrollParallaxImageViewActivity
import vn.loitp.app.activity.customviews.imageview.stfaiconImageViewer.ListActivity
import vn.loitp.app.activity.customviews.imageview.strectchy.StrectchyImageViewActivity
import vn.loitp.app.activity.customviews.imageview.touch.TouchImageViewActivity
import vn.loitp.app.activity.customviews.imageview.zoom.ZoomImageViewActivity

@LogTag("ImageViewMenuActivity")
@IsFullScreen(false)
class MenuImageViewActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_image_view
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuImageViewActivity::class.java.simpleName
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
