package vn.loitp.app.activity.customviews.imageview.roundedImageView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_rounded_image_view.*
import vn.loitp.app.R

@LogTag("RoundedImageViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class RoundedImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rounded_image_view
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
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/rishabh876/RoundedImageView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = RoundedImageViewActivity::class.java.simpleName
        }
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_1,
            imageView = ivCircle,
        )
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_1,
            imageView = iv,
        )
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_1,
            imageView = iv1,
        )
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_1,
            imageView = iv2,
        )
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_1,
            imageView = iv4,
        )
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_1,
            imageView = iv5,
        )
    }
}
