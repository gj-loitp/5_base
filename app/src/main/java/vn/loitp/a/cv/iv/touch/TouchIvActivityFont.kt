package vn.loitp.a.cv.iv.touch

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_iv_touch.*
import vn.loitp.R
import vn.loitp.up.common.Constants

@LogTag("TouchImageViewActivity")
@IsFullScreen(false)
class TouchIvActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_touch
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
            this.tvTitle?.text = TouchIvActivityFont::class.java.simpleName
        }
        // note when use with glide, must have placeholder
        lTouchImageView.loadGlide(
            any = Constants.URL_IMG,
            resPlaceHolder = R.color.colorPrimary
        )
    }
}
