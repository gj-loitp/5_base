package vn.loitp.app.activity.customviews.actionbar.lactionbar

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_l_action_bar.*
import vn.loitp.app.R

@LogTag("LActionbarActivity")
@IsFullScreen(false)
class LActionbarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_l_action_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBar()
    }

    private fun setupActionBar() {
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.lactionbar)

        lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListener {
                onBackPressed()
            }
            this.ivIconRight?.let {
                it.setSafeOnClickListener {
                    showShortInformation(msg = "onClickMenu", isTopAnchor = false)
                }
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = "LActionbarActivity"
        }
    }
}
