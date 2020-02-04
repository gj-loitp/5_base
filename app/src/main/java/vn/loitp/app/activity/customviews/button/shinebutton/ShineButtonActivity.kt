package vn.loitp.app.activity.customviews.button.shinebutton

import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.views.button.shinebutton.LShineView

import vn.loitp.app.R

class ShineButtonActivity : BaseFontActivity() {
    private var bt0: LShineView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bt0 = findViewById<View>(R.id.bt_0) as LShineView
        bt0!!.setImage(R.mipmap.ic_launcher)
        bt0!!.setSize(100, 80)
        bt0!!.setOnClick(object : LShineView.Callback {
            override fun onClick(view: View) {
                showShort("onClick")
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_shine
    }
}
