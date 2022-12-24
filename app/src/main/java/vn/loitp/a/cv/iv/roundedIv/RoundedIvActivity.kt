package vn.loitp.a.cv.iv.roundedIv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_iv_rounded.*
import vn.loitp.R

@LogTag("RoundedIvActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class RoundedIvActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_rounded
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
            this.tvTitle?.text = RoundedIvActivity::class.java.simpleName
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