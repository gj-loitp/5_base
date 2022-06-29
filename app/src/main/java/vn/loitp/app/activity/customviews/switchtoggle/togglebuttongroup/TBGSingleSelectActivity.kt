package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
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

        groupChoices.setOnCheckedChangeListener { _, checkedId ->
            logD("onCheckedChanged(): checkedId = $checkedId")
            showShortInformation("onCheckedChanged(): checkedId = $checkedId")
        }
    }
}
