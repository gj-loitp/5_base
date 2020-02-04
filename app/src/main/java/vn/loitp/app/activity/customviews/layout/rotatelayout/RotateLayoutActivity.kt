package vn.loitp.app.activity.customviews.layout.rotatelayout

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.core.utilities.LDeviceUtil
import com.views.layout.rotatelayout.LRotateLayout
import kotlinx.android.synthetic.main.activity_rotate_layout.*

import vn.loitp.app.R

class RotateLayoutActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lRotateLayout = findViewById<LRotateLayout>(R.id.rotate_layout)

        bt.setOnClickListener { _ ->
            val angle = LDeviceUtil.getRandomNumber(360)
            lRotateLayout.angle = angle
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rotate_layout
    }
}
