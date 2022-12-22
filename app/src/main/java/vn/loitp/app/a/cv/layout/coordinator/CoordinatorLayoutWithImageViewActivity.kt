package vn.loitp.app.a.cv.layout.coordinator

import android.graphics.Matrix
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_coordinator_layout.*
import vn.loitp.R
import kotlin.math.roundToInt

@LogTag("CoordinatorLayoutWithImageViewActivity")
@IsFullScreen(false)
class CoordinatorLayoutWithImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coordinator_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        appBarLayout.addOnOffsetChangedListener { _: AppBarLayout?, verticalOffset: Int ->
            val matrix = Matrix(imgCover.imageMatrix)

            // get image's width and height
            val dWidth = imgCover.drawable.intrinsicWidth
            val dHeight = imgCover.drawable.intrinsicHeight

            // get view's width and height
            val vWidth = imgCover.width - imgCover.paddingLeft - imgCover.paddingRight
            var vHeight = imgCover.height - imgCover.paddingTop - imgCover.paddingBottom
            val scale: Float
            var dx = 0f
            val dy: Float
            val parallaxMultiplier =
                (imgCover.layoutParams as CollapsingToolbarLayout.LayoutParams).parallaxMultiplier

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
                imgCover.imageMatrix = matrix
            }
        }
    }
}
