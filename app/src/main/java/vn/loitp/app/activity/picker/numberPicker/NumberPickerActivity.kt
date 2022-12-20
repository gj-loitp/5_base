package vn.loitp.app.activity.picker.numberPicker

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import com.loitpcore.picker.number.LNumberPicker
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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = NumberPickerActivity::class.java.simpleName
        }
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
