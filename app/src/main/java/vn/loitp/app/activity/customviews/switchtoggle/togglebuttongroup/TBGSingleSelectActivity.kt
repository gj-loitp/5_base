package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_tbg_single.*
import vn.loitp.app.R

class TBGSingleSelectActivity : BaseFontActivity() {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_tbg_single
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        groupChoices.setOnCheckedChangeListener { group, checkedId ->
            logD("onCheckedChanged(): checkedId = $checkedId")
            showShort("onCheckedChanged(): checkedId = $checkedId")
        }
    }
}
