package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.graphics.Matrix
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_coordinator_layout.*
import vn.loitp.app.R
import kotlin.math.roundToInt

@LayoutId(R.layout.activity_coordinator_layout)
@LogTag("CoordinatorLayoutWithImageViewActivity")
@IsFullScreen(false)
class CoordinatorLayoutWithImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appBar.addOnOffsetChangedListener(OnOffsetChangedListener { _: AppBarLayout?, verticalOffset: Int ->
            val matrix = Matrix(imgHero.imageMatrix)

            //get image's width and height
            val dwidth = imgHero.drawable.intrinsicWidth
            val dheight = imgHero.drawable.intrinsicHeight

            //get view's width and height
            val vwidth = imgHero.width - imgHero.paddingLeft - imgHero.paddingRight
            var vheight = imgHero.height - imgHero.paddingTop - imgHero.paddingBottom
            val scale: Float
            var dx = 0f
            val dy: Float
            val parallaxMultiplier = (imgHero.layoutParams as CollapsingToolbarLayout.LayoutParams).parallaxMultiplier

            //maintain the image's aspect ratio depending on offset
            if (dwidth * vheight > vwidth * dheight) {
                vheight += verticalOffset //calculate view height depending on offset
                scale = vheight.toFloat() / dheight.toFloat() //calculate scale
                dx = (vwidth - dwidth * scale) * 0.5f //calculate x value of the center point of scaled drawable
                dy = -verticalOffset * (1 - parallaxMultiplier) //calculate y value by compensating parallaxMultiplier
            } else {
                scale = vwidth.toFloat() / dwidth.toFloat()
                dy = (vheight - dheight * scale) * 0.5f
            }
            val currentWidth = (scale * dwidth).roundToInt() //calculate current intrinsic width of the drawable
            if (vwidth <= currentWidth) { //compare view width and drawable width to decide, should we scale more or not
                matrix.setScale(scale, scale)
                matrix.postTranslate(dx.roundToInt().toFloat(), dy.roundToInt().toFloat())
                imgHero.imageMatrix = matrix
            }
        })
    }
}
