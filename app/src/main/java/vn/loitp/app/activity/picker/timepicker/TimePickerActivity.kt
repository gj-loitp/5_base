package vn.loitp.app.activity.picker.timepicker

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_time_picker.*
import vn.loitp.app.R
import java.util.*

class TimePickerActivity : BaseFontActivity() {

    private var picker: TimePickerDialog? = null

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_time_picker
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timePickerSpiner.setIs24HourView(true)

        timePickerClock.setOnTimeChangedListener { _, hourOfDay, minute ->
            tvTimePickerClock.text = "$hourOfDay - $minute"
        }
        timePickerSpiner.setOnTimeChangedListener { _, hourOfDay, minute ->
            tvTimePickeSpinner.text = "$hourOfDay - $minute"
        }
        btDialog.setSafeOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cal.get(Calendar.MINUTE)
            picker = TimePickerDialog(activity,
                    TimePickerDialog.OnTimeSetListener { tp, sHour, sMinute ->
                        btDialog.text = "$sHour - $sMinute"
                    }, hour, minutes, true)
            picker?.show()
        }
    }
}
