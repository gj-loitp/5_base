package vn.loitp.app.activity.customviews.button.lbutton

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import com.core.base.BaseFontActivity
import com.views.LToast
import com.views.button.lbutton.LButton

import loitp.basemaster.R

class LButtonActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bt0 = findViewById<LButton>(R.id.bt_0)
        bt0.setOnClickListener(this)

        val bt1 = findViewById<LButton>(R.id.bt_1)
        bt1.setPressedDrawable(R.drawable.circle_color_primary)
        bt1.setOnClickListener(this)

        val bt2 = findViewById<LButton>(R.id.bt_2)
        bt2.setPressedDrawable(R.drawable.bt_color_primary)
        bt2.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_l_button
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_0, R.id.bt_1, R.id.bt_2 -> LToast.show(activity, R.string.click)
        }
    }
}
