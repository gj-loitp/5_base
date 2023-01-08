package vn.loitp.a.cv.ab.l

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.utilities.LStoreUtil
import kotlinx.android.synthetic.main.a_l_action_bar.*
import vn.loitp.R

@LogTag("LActionbarActivity")
@IsFullScreen(false)
class LActionbarActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_l_action_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBar()
    }

    @SuppressLint("SetTextI18n")
    private fun setupActionBar() {
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.lactionbar)

        lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        showShortInformation(msg = "onClickMenu", isTopAnchor = false)
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = "LActionbarActivity"
        }
    }
}
