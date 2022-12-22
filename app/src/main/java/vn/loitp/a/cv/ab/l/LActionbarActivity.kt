package vn.loitp.a.cv.ab.l

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_l_action_bar.*
import vn.loitp.R

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

    @SuppressLint("SetTextI18n")
    private fun setupActionBar() {
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.lactionbar)

        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
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
