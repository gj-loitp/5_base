package vn.loitp.app.activity.customviews.edittext.autoResize

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_auto_resize_editext.*
import vn.loitp.app.R

@LogTag("AutoResizeEditTextActivity")
@IsFullScreen(false)
class AutoResizeEditTextActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_auto_resize_editext
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
            this.tvTitle?.text = AutoResizeEditTextActivity::class.java.simpleName
        }
    }
}
