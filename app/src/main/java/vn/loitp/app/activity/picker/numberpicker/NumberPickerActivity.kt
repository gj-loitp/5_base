package vn.loitp.app.activity.picker.numberpicker

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.picker.number.LNumberPicker
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_picker_number_picker.*
import vn.loitp.app.R

class NumberPickerActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_picker_number_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lnb.setCallback(object : LNumberPicker.Callback {
            override fun onValueChangedNumberPicker(h: String, m: String, s: String) {
                tv.text = "$h : $m : $s"
            }
        })

        bt.setSafeOnClickListener {
            lnb.setValue(valueH = 1, valueM = 30, valueS = 45)
        }
    }
}
