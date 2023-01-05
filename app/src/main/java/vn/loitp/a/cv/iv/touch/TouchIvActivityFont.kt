package vn.loitp.a.cv.iv.touch

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_iv_touch.*
import vn.loitp.R
import vn.loitp.common.Constants

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
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = TouchIvActivityFont::class.java.simpleName
        }
        // note when use with glide, must have placeholder
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG,
            imageView = lTouchImageView,
            resPlaceHolder = R.color.colorPrimary
        )
    }
}
