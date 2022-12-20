package vn.loitp.app.activity.customviews.switchToggle.toggleButtonGroup

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_switch_tbg_single.*
import vn.loitp.app.R

@LogTag("TBGSingleSelectActivity")
@IsFullScreen(false)
class TBGSingleSelectActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_tbg_single
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = TBGSingleSelectActivity::class.java.simpleName
        }
        groupChoices.setOnCheckedChangeListener { _, checkedId ->
            logD("onCheckedChanged(): checkedId = $checkedId")
            showShortInformation("onCheckedChanged(): checkedId = $checkedId")
        }
    }
}
