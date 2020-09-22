package vn.loitp.app.activity.picker.numberpicker

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.picker.number.LNumberPicker
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_picker_number_picker.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_picker_number_picker)
@LogTag("NumberPickerActivity")
@IsFullScreen(false)
class NumberPickerActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lnb.setCallback(object : LNumberPicker.Callback {
            @SuppressLint("SetTextI18n")
            override fun onValueChangedNumberPicker(h: String, m: String, s: String) {
                textView.text = "$h : $m : $s"
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
