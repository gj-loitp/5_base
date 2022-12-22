package vn.loitp.app.a.customviews.button.shineButton

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_shine_button.*
import vn.loitp.R

@LogTag("ShineButtonActivity")
@IsFullScreen(false)
class ShineButtonActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shine_button
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
            this.tvTitle?.text = ShineButtonActivity::class.java.simpleName
        }
    }
}
