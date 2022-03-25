package vn.loitp.app.activity.customviews.edittext.autoresizeedittext

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_admob_banner.*
import vn.loitp.app.R

@LogTag("AutoResizeEditTextActivity")
@IsFullScreen(false)
class AutoResizeEditTextActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_autoresize
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
            this.tvTitle?.text = AutoResizeEditTextActivity::class.java.simpleName
        }
    }
}
