package vn.loitp.a.cv.iv.circleIv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.a_iv_circle.*
import vn.loitp.R
import vn.loitp.common.Constants

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
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CircleIvActivityFont::class.java.simpleName
        }
        val resPlaceHolder = R.color.red
        LImageUtil.load(
            context = this,
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
            imageView = imageView,
            resPlaceHolder = resPlaceHolder,
            transformation = RoundedCornersTransformation(
                45,
                0,
                RoundedCornersTransformation.CornerType.BOTTOM
            )
        )

        LImageUtil.load(
            context = this,
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
            imageView = iv1,
            resPlaceHolder = resPlaceHolder,
            transformation = CropCircleWithBorderTransformation()
        )

        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_LARGE,
            imageView = iv2,
            resPlaceHolder = resPlaceHolder,
            transformation = CropCircleTransformation()
        )

        LImageUtil.load(
            context = this,
            any = "https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg",
            imageView = iv
        )
    }
}
