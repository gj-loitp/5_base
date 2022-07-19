package vn.loitp.app.activity.animation.overScroll

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsShowAdWhenExit
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LStoreUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_animation_over_scroll.*
import vn.loitp.app.R

@LogTag("OverScrollActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(value = true)
class OverScrollActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_over_scroll
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = OverScrollActivity::class.java.simpleName
        }

        LUIUtil.setPullLikeIOSVertical(scrollView = nsv)
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.overscroll)
    }
}
