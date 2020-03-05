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

        btSet.setSafeOnClickListener {
            lnb.setMinMaxValue(valueH = 1, valueM = 30, valueS = 45)
        }
        btMin.setSafeOnClickListener {
            lnb.setMinValue(minValueH = 5, minValueM = 5, minValueS = 5)
        }
        btMax.setSafeOnClickListener {
            lnb.setMaxValue(maxValueH = 10, maxValueM = 10, maxValueS = 10)
        }
        btReset.setSafeOnClickListener {
            lnb.resetMinMaxValue()
        }
    }
}
