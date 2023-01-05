package vn.loitp.a.cv.iv.strectchy

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_iv_strectchy.*
import vn.loitp.R
import vn.loitp.common.Constants

@LogTag("StrectchyImageViewActivity")
@IsFullScreen(false)
class StrectchyIvActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_strectchy
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
            this.tvTitle?.text = StrectchyIvActivityFont::class.java.simpleName
        }
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_LONG,
            imageView = lStretchyImageView
        )
    }
}
