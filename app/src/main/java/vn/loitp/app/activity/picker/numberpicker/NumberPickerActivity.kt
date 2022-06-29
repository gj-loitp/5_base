package vn.loitp.app.activity.picker.numberpicker

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.picker.number.LNumberPicker
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_picker_number_picker.*
import vn.loitp.app.R

@LogTag("NumberPickerActivity")
@IsFullScreen(false)
class NumberPickerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_picker_number_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
