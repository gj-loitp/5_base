package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_switch_tbg_single.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_switch_tbg_single)
class TBGSingleSelectActivity : BaseFontActivity() {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        groupChoices.setOnCheckedChangeListener { _, checkedId ->
            logD("onCheckedChanged(): checkedId = $checkedId")
            showShort("onCheckedChanged(): checkedId = $checkedId")
        }
    }
}
