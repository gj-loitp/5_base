package vn.loitp.app.activity.customviews.button.roundedbutton

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_button_rounded.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_button_rounded)
@LogTag("RoundedButtonActivity")
class RoundedButtonActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt0.setOnClickListener { showShort(getString(R.string.click)) }
        bt1.setOnClickListener { showShort(getString(R.string.click)) }
        bt2.setOnClickListener { showShort(getString(R.string.click)) }
        bt3.setOnClickListener { showShort(getString(R.string.click)) }
    }
}
