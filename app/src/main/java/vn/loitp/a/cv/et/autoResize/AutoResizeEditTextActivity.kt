package vn.loitp.a.cv.et.autoResize

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_et_auto_resize.*
import vn.loitp.R

@LogTag("AutoResizeEditTextActivity")
@IsFullScreen(false)
class AutoResizeEditTextActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_et_auto_resize
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
