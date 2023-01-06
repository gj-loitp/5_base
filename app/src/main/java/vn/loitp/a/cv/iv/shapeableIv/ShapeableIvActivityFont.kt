package vn.loitp.a.cv.iv.shapeableIv

import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.shape.CornerFamily
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.getDimenValue
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_iv_shapeable.*
import vn.loitp.R

@LogTag("ShapeableImageViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ShapeableIvActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_shapeable
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
                        context.openUrlInBrowser(
                            url = "https://viblo.asia/p/hien-thi-image-nhieu-hinh-dang-voi-shapeableimageview-3P0lPGJGZox"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ShapeableIvActivityFont::class.java.simpleName
        }

        siv.shapeAppearanceModel = siv.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(
                CornerFamily.ROUNDED,
                getDimenValue(R.dimen.round_largest).toFloat()
            )
            .setBottomLeftCorner(
                CornerFamily.ROUNDED,
                getDimenValue(R.dimen.round_medium).toFloat()
            )
            .build()
    }
}
