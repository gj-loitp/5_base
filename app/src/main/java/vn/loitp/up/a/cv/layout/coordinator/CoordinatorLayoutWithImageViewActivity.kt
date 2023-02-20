package vn.loitp.up.a.cv.layout.coordinator

import android.graphics.Matrix
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.ACoordinatorLayoutBinding
import kotlin.math.roundToInt

@LogTag("CoordinatorLayoutWithImageViewActivity")
@IsFullScreen(false)
class CoordinatorLayoutWithImageViewActivity : BaseActivityFont() {
    private lateinit var binding: ACoordinatorLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACoordinatorLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.appBarLayout.addOnOffsetChangedListener { _: AppBarLayout?, verticalOffset: Int ->
            val matrix = Matrix(binding.imgCover.imageMatrix)

            // get image's width and height
            val dWidth = binding.imgCover.drawable.intrinsicWidth
            val dHeight = binding.imgCover.drawable.intrinsicHeight

            // get view's width and height
            val vWidth =
                binding.imgCover.width - binding.imgCover.paddingLeft - binding.imgCover.paddingRight
            var vHeight =
                binding.imgCover.height - binding.imgCover.paddingTop - binding.imgCover.paddingBottom
            val scale: Float
            var dx = 0f
            val dy: Float
            val parallaxMultiplier =
                (binding.imgCover.layoutParams as CollapsingToolbarLayout.LayoutParams).parallaxMultiplier

            // maintain the image's aspect ratio depending on offset
            if (dWidth * vHeight > vWidth * dHeight) {
                vHeight += verticalOffset // calculate view height depending on offset
                scale = vHeight.toFloat() / dHeight.toFloat() // calculate scale
                dx =
                    (vWidth - dWidth * scale) * 0.5f // calculate x value of the center point of scaled drawable
                dy =
                    -verticalOffset * (1 - parallaxMultiplier) // calculate y value by compensating parallaxMultiplier
            } else {
                scale = vWidth.toFloat() / dWidth.toFloat()
                dy = (vHeight - dHeight * scale) * 0.5f
            }
            val currentWidth =
                (scale * dWidth).roundToInt() // calculate current intrinsic width of the drawable
            if (vWidth <= currentWidth) { // compare view width and drawable width to decide, should we scale more or not
                matrix.setScale(scale, scale)
                matrix.postTranslate(dx.roundToInt().toFloat(), dy.roundToInt().toFloat())
                binding.imgCover.imageMatrix = matrix
            }
        }
    }
}
