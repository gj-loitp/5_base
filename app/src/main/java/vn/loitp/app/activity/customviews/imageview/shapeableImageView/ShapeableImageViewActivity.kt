package vn.loitp.app.activity.customviews.imageview.shapeableImageView

import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.shape.CornerFamily
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LAppResource
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_shapeable_image_view.*
import vn.loitp.app.R

@LogTag("ShapeableImageViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ShapeableImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shapeable_image_view
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
            this.ivIconRight?.apply {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = this,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://viblo.asia/p/hien-thi-image-nhieu-hinh-dang-voi-shapeableimageview-3P0lPGJGZox"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ShapeableImageViewActivity::class.java.simpleName
        }

        siv.shapeAppearanceModel = siv.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(
                CornerFamily.ROUNDED,
                LAppResource.getDimenValue(R.dimen.round_largest).toFloat()
            )
            .setBottomLeftCorner(
                CornerFamily.ROUNDED,
                LAppResource.getDimenValue(R.dimen.round_medium).toFloat()
            )
            .build()
    }
}
