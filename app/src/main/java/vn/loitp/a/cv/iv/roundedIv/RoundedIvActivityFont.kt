package vn.loitp.a.cv.iv.roundedIv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.loadGlide
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_iv_rounded.*
import vn.loitp.R

@LogTag("RoundedIvActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class RoundedIvActivityFont : BaseActivityFont() {

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
            this.tvTitle?.text = RoundedIvActivityFont::class.java.simpleName
        }
        ivCircle.loadGlide(
            any = Constants.URL_IMG_1,
        )
        iv.loadGlide(
            any = Constants.URL_IMG_1,
        )
        iv1.loadGlide(
            any = Constants.URL_IMG_1,
        )
        iv2.loadGlide(
            any = Constants.URL_IMG_1,
        )
        iv4.loadGlide(
            any = Constants.URL_IMG_1,
        )
        iv5.loadGlide(
            any = Constants.URL_IMG_1,
        )
    }
}
