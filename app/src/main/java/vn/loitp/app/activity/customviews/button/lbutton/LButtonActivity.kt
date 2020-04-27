package vn.loitp.app.activity.customviews.button.lbutton

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_button_l.*
import vn.loitp.app.R

class LButtonActivity : BaseFontActivity(), OnClickListener {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_l
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt0.setOnClickListener(this)

        bt1.setPressedDrawable(R.drawable.l_circle_color_primary)
        bt1.setOnClickListener(this)

        bt2.setPressedDrawable(R.drawable.l_bt_color_primary)
        bt2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            bt0, bt1, bt2 -> showShort(getString(R.string.click))
        }
    }
}
