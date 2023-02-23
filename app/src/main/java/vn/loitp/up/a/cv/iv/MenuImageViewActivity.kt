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
import vn.loitp.up.a.cv.iv.reflection.ReflectionActivity
import vn.loitp.up.a.cv.iv.roundedIv.RoundedIvActivity
import vn.loitp.up.a.cv.iv.scrollParallax.ScrollParallaxIvActivity
import vn.loitp.up.a.cv.iv.shapeableIv.ShapeableIvActivity
import vn.loitp.up.a.cv.iv.stfaiconIv.ListActivity
import vn.loitp.databinding.AIvMenuBinding
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
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btCirleImageView -> launchActivity(CircleIvActivityFont::class.java)
            binding.btStretchyImageView -> launchActivity(StrectchyIvActivity::class.java)
            binding.btTouchImageView -> launchActivity(TouchIvActivity::class.java)
            binding.btZoomImageView -> launchActivity(ZoomIvActivity::class.java)
            binding.btFidgetSpinner -> launchActivity(FidgetSpinnerIvActivityFont::class.java)
            binding.btContinuousScrollableImageView -> launchActivity(
                ContinuousScrollableImageViewActivityFont::class.java
            )
            binding.btScrollParallaxImageView -> launchActivity(ScrollParallaxIvActivity::class.java)
            binding.btPanoramaImageView -> launchActivity(PanoramaIvActivityFont::class.java)
            binding.btBigImageView -> launchActivity(BigIvActivityFont::class.java)
            binding.btBigImageViewWithScrollView -> launchActivity(BigIvWithSvActivityFont::class.java)
            binding.btTouchImageViewWithViewPager -> launchActivity(PinchToZoomViewPagerActivityFont::class.java)
            binding.btKenburnView -> launchActivity(KenburnViewActivityFont::class.java)
            binding.btComicView -> launchActivity(ComicViewActivityFont::class.java)
            binding.btStfalconImageViewer -> launchActivity(ListActivity::class.java)
            binding.btReflection -> launchActivity(ReflectionActivity::class.java)
            binding.btRoundedImageView -> launchActivity(RoundedIvActivity::class.java)
            binding.btPreviewImageCollection -> launchActivity(PreviewImageCollectionActivityFont::class.java)
            binding.btShapeableImageViewActivity -> launchActivity(ShapeableIvActivity::class.java)
            binding.btCoil -> launchActivity(CoilActivityFont::class.java)
        }
    }
}
