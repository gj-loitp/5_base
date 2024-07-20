package vn.loitp.up.a.cv.iv.shapeableIv

import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.shape.CornerFamily
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.getDimenValue
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AIvShapeableBinding

@LogTag("ShapeableImageViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ShapeableIvActivity : BaseActivityFont() {
    private lateinit var binding: AIvShapeableBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvShapeableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://viblo.asia/p/hien-thi-image-nhieu-hinh-dang-voi-shapeableimageview-3P0lPGJGZox"
                    )
                })
                isVisible = true
                setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ShapeableIvActivity::class.java.simpleName
        }

        binding.siv.shapeAppearanceModel =
            binding.siv.shapeAppearanceModel.toBuilder().setTopRightCorner(
                    CornerFamily.ROUNDED, getDimenValue(com.loitp.R.dimen.round_largest).toFloat()
                ).setBottomLeftCorner(
                    CornerFamily.ROUNDED, getDimenValue(com.loitp.R.dimen.round_medium).toFloat()
                ).build()
    }
}
