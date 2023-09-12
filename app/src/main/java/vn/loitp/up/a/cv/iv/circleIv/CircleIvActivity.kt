package vn.loitp.up.a.cv.iv.circleIv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import vn.loitp.databinding.AIvCircleBinding
import vn.loitp.up.common.Constants

@LogTag("CircleImageViewActivity")
@IsFullScreen(false)
class CircleIvActivity : BaseActivityFont() {
    private lateinit var binding: AIvCircleBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvCircleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CircleIvActivity::class.java.simpleName
        }
        val resPlaceHolder = com.loitp.R.color.red
        binding.imageView.loadGlide(
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
            resPlaceHolder = resPlaceHolder,
            transformation = RoundedCornersTransformation(
                /* radius = */ 45,
                /* margin = */ 0,
                /* cornerType = */ RoundedCornersTransformation.CornerType.BOTTOM
            )
        )

        binding.iv1.loadGlide(
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
            resPlaceHolder = resPlaceHolder,
            transformation = CropCircleWithBorderTransformation()
        )

        binding.iv2.loadGlide(
            any = Constants.URL_IMG_LARGE,
            resPlaceHolder = resPlaceHolder,
            transformation = CropCircleTransformation()
        )

        binding.iv.loadGlide(
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
        )
    }
}
