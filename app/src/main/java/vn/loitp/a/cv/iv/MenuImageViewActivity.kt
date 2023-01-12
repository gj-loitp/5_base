package vn.loitp.a.cv.iv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_iv_menu.*
import vn.loitp.R
import vn.loitp.a.cv.iv.bigIv.BigIvActivityFont
import vn.loitp.a.cv.iv.bigIv.BigIvWithSvActivityFont
import vn.loitp.a.cv.iv.circleIv.CircleIvActivityFont
import vn.loitp.a.cv.iv.coil.CoilActivityFont
import vn.loitp.a.cv.iv.comic.ComicViewActivityFont
import vn.loitp.a.cv.iv.continuousScrollable.ContinuousScrollableImageViewActivityFont
import vn.loitp.a.cv.iv.fidgetSpinner.FidgetSpinnerIvActivityFont
import vn.loitp.a.cv.iv.kenburn.KenburnViewActivityFont
import vn.loitp.a.cv.iv.panorama.PanoramaIvActivityFont
import vn.loitp.a.cv.iv.pinchToZoom.PinchToZoomViewPagerActivityFont
import vn.loitp.a.cv.iv.previewImageCollection.PreviewImageCollectionActivityFont
import vn.loitp.a.cv.iv.reflection.ReflectionActivityFont
import vn.loitp.a.cv.iv.roundedIv.RoundedIvActivityFont
import vn.loitp.a.cv.iv.scrollParallax.ScrollParallaxIvActivityFont
import vn.loitp.a.cv.iv.shapeableIv.ShapeableIvActivityFont
import vn.loitp.a.cv.iv.stfaiconIv.ListActivityFont
import vn.loitp.a.cv.iv.strectchy.StrectchyIvActivityFont
import vn.loitp.a.cv.iv.touch.TouchIvActivityFont
import vn.loitp.a.cv.iv.zoom.ZoomIvActivityFont

@LogTag("ImageViewMenuActivity")
@IsFullScreen(false)
class MenuImageViewActivity : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = vn.loitp.a.cv.iv.MenuImageViewActivity::class.java.simpleName
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
        btReflection.setOnClickListener(this)
        btRoundedImageView.setOnClickListener(this)
        btPreviewImageCollection.setOnClickListener(this)
        btShapeableImageViewActivity.setOnClickListener(this)
        btCoil.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btCirleImageView -> launchActivity(CircleIvActivityFont::class.java)
            btStretchyImageView -> launchActivity(StrectchyIvActivityFont::class.java)
            btTouchImageView -> launchActivity(TouchIvActivityFont::class.java)
            btZoomImageView -> launchActivity(ZoomIvActivityFont::class.java)
            btFidgetSpinner -> launchActivity(FidgetSpinnerIvActivityFont::class.java)
            btContinuousScrollableImageView -> launchActivity(ContinuousScrollableImageViewActivityFont::class.java)
            btScrollParallaxImageView -> launchActivity(ScrollParallaxIvActivityFont::class.java)
            btPanoramaImageView -> launchActivity(PanoramaIvActivityFont::class.java)
            btBigImageView -> launchActivity(BigIvActivityFont::class.java)
            btBigImageViewWithScrollView -> launchActivity(BigIvWithSvActivityFont::class.java)
            btTouchImageViewWithViewPager -> launchActivity(PinchToZoomViewPagerActivityFont::class.java)
            btKenburnView -> launchActivity(KenburnViewActivityFont::class.java)
            btComicView -> launchActivity(ComicViewActivityFont::class.java)
            btStfalconImageViewer -> launchActivity(ListActivityFont::class.java)
            btReflection -> launchActivity(ReflectionActivityFont::class.java)
            btRoundedImageView -> launchActivity(RoundedIvActivityFont::class.java)
            btPreviewImageCollection -> launchActivity(PreviewImageCollectionActivityFont::class.java)
            btShapeableImageViewActivity -> launchActivity(ShapeableIvActivityFont::class.java)
            btCoil -> launchActivity(CoilActivityFont::class.java)
        }
    }
}
