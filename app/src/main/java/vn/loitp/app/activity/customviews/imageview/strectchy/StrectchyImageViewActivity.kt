package vn.loitp.app.activity.customviews.imageview.strectchy

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_strectchy_imageview.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LogTag("StrectchyImageViewActivity")
@IsFullScreen(false)
class StrectchyImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_strectchy_imageview
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = StrectchyImageViewActivity::class.java.simpleName
        }
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_LONG,
            imageView = lStretchyImageView
        )
    }
}
