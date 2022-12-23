package vn.loitp.app.a.cv.iv.touch

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_touch_image_view.*
import vn.loitp.R
import vn.loitp.common.Constants

@LogTag("TouchImageViewActivity")
@IsFullScreen(false)
class TouchImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_touch_image_view
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
            this.tvTitle?.text = TouchImageViewActivity::class.java.simpleName
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