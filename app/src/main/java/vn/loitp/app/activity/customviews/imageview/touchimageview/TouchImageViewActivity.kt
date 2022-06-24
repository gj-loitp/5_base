package vn.loitp.app.activity.customviews.imageview.touchimageview

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_imageview_touch.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LogTag("TouchImageViewActivity")
@IsFullScreen(false)
class TouchImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_touch
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
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
