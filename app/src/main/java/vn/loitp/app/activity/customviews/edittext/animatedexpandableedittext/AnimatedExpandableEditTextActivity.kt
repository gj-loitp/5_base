package vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_admob_banner.*
import vn.loitp.app.R

@LogTag("AnimatedExpandableEditTextActivity")
@IsFullScreen(false)
class AnimatedExpandableEditTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_animated_expandale
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
            this.tvTitle?.text = AnimatedExpandableEditTextActivity::class.java.simpleName
        }
    }
}
