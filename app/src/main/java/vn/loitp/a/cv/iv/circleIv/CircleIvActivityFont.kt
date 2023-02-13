package vn.loitp.a.cv.iv.circleIv

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
import kotlinx.android.synthetic.main.a_iv_circle.*
import vn.loitp.R
import vn.loitp.up.common.Constants

@LogTag("CircleImageViewActivity")
@IsFullScreen(false)
class CircleIvActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_circle
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
            this.tvTitle?.text = CircleIvActivityFont::class.java.simpleName
        }
        val resPlaceHolder = R.color.red
        imageView.loadGlide(
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
            resPlaceHolder = resPlaceHolder,
            transformation = RoundedCornersTransformation(
                /* radius = */ 45,
                /* margin = */ 0,
                /* cornerType = */ RoundedCornersTransformation.CornerType.BOTTOM
            )
        )

        iv1.loadGlide(
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
            resPlaceHolder = resPlaceHolder,
            transformation = CropCircleWithBorderTransformation()
        )

        iv2.loadGlide(
            any = Constants.URL_IMG_LARGE,
            resPlaceHolder = resPlaceHolder,
            transformation = CropCircleTransformation()
        )

        iv.loadGlide(
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
        )
    }
}
