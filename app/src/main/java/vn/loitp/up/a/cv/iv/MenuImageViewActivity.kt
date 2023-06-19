package vn.loitp.up.a.cv.iv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.up.a.cv.iv.bigIv.BigIvActivity
import vn.loitp.up.a.cv.iv.bigIv.BigIvWithSvActivity
import vn.loitp.up.a.cv.iv.circleIv.CircleIvActivity
import vn.loitp.up.a.cv.iv.coil.CoilActivity
import vn.loitp.up.a.cv.iv.comic.ComicViewActivity
import vn.loitp.up.a.cv.iv.continuousScrollable.ContinuousScrollableImageViewActivity
import vn.loitp.up.a.cv.iv.fidgetSpinner.FidgetSpinnerIvActivity
import vn.loitp.up.a.cv.iv.kenburn.KenburnViewActivity
import vn.loitp.up.a.cv.iv.panorama.PanoramaIvActivity
import vn.loitp.up.a.cv.iv.pinchToZoom.PinchToZoomViewPagerActivity
import vn.loitp.up.a.cv.iv.previewImageCollection.PreviewImageCollectionActivity
import vn.loitp.up.a.cv.iv.reflection.ReflectionActivity
import vn.loitp.up.a.cv.iv.roundedIv.RoundedIvActivity
import vn.loitp.up.a.cv.iv.scrollParallax.ScrollParallaxIvActivity
import vn.loitp.up.a.cv.iv.shapeableIv.ShapeableIvActivity
import vn.loitp.up.a.cv.iv.stfaiconIv.ListActivity
import vn.loitp.databinding.AIvMenuBinding
import vn.loitp.up.a.cv.iv.blurry.BlurryActivity
import vn.loitp.up.a.cv.iv.imageSlideshow.ImageSlideshowActivity
import vn.loitp.up.a.cv.iv.strectchy.StrectchyIvActivity
import vn.loitp.up.a.cv.iv.touch.TouchIvActivity
import vn.loitp.up.a.cv.iv.zoom.ZoomIvActivity

@LogTag("ImageViewMenuActivity")
@IsFullScreen(false)
class MenuImageViewActivity : BaseActivityFont(), OnClickListener {
    private lateinit var binding: AIvMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvMenuBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuImageViewActivity::class.java.simpleName
        }
        binding.btCirleImageView.setOnClickListener(this)
        binding.btStretchyImageView.setOnClickListener(this)
        binding.btTouchImageView.setOnClickListener(this)
        binding.btZoomImageView.setOnClickListener(this)
        binding.btFidgetSpinner.setOnClickListener(this)
        binding.btContinuousScrollableImageView.setOnClickListener(this)
        binding.btScrollParallaxImageView.setOnClickListener(this)
        binding.btPanoramaImageView.setOnClickListener(this)
        binding.btBigImageView.setOnClickListener(this)
        binding.btBigImageViewWithScrollView.setOnClickListener(this)
        binding.btTouchImageViewWithViewPager.setOnClickListener(this)
        binding.btKenburnView.setOnClickListener(this)
        binding.btComicView.setOnClickListener(this)
        binding.btStfalconImageViewer.setOnClickListener(this)
        binding.btReflection.setOnClickListener(this)
        binding.btRoundedImageView.setOnClickListener(this)
        binding.btPreviewImageCollection.setOnClickListener(this)
        binding.btShapeableImageViewActivity.setOnClickListener(this)
        binding.btCoil.setOnClickListener(this)
        binding.btImageSlideshow.setOnClickListener(this)
        binding.btBlurry.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btCirleImageView -> launchActivity(CircleIvActivity::class.java)
            binding.btStretchyImageView -> launchActivity(StrectchyIvActivity::class.java)
            binding.btTouchImageView -> launchActivity(TouchIvActivity::class.java)
            binding.btZoomImageView -> launchActivity(ZoomIvActivity::class.java)
            binding.btFidgetSpinner -> launchActivity(FidgetSpinnerIvActivity::class.java)
            binding.btContinuousScrollableImageView -> launchActivity(
                ContinuousScrollableImageViewActivity::class.java
            )
            binding.btScrollParallaxImageView -> launchActivity(ScrollParallaxIvActivity::class.java)
            binding.btPanoramaImageView -> launchActivity(PanoramaIvActivity::class.java)
            binding.btBigImageView -> launchActivity(BigIvActivity::class.java)
            binding.btBigImageViewWithScrollView -> launchActivity(BigIvWithSvActivity::class.java)
            binding.btTouchImageViewWithViewPager -> launchActivity(PinchToZoomViewPagerActivity::class.java)
            binding.btKenburnView -> launchActivity(KenburnViewActivity::class.java)
            binding.btComicView -> launchActivity(ComicViewActivity::class.java)
            binding.btStfalconImageViewer -> launchActivity(ListActivity::class.java)
            binding.btReflection -> launchActivity(ReflectionActivity::class.java)
            binding.btRoundedImageView -> launchActivity(RoundedIvActivity::class.java)
            binding.btPreviewImageCollection -> launchActivity(PreviewImageCollectionActivity::class.java)
            binding.btShapeableImageViewActivity -> launchActivity(ShapeableIvActivity::class.java)
            binding.btCoil -> launchActivity(CoilActivity::class.java)
            binding.btImageSlideshow -> launchActivity(ImageSlideshowActivity::class.java)
            binding.btBlurry -> launchActivity(BlurryActivity::class.java)
        }
    }
}
