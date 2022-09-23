package vn.loitp.app.activity.customviews.imageview.circleImageView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_circle_image_view.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LogTag("CircleImageViewActivity")
@IsFullScreen(false)
class CircleImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_circle_image_view
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
            this.tvTitle?.text = CircleImageViewActivity::class.java.simpleName
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
