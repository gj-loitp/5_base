package vn.loitp.app.activity.picker.timepicker

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_time_picker.*
import vn.loitp.app.R
import java.util.*

@LayoutId(R.layout.activity_time_picker)
@LogTag("TimePickerActivity")
class TimePickerActivity : BaseFontActivity() {

    private var picker: TimePickerDialog? = null
    private var pickerRange: RangeTimePickerDialog? = null

    override fun setFullScreen(): Boolean {
        return false
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
                    TimePickerDialog.OnTimeSetListener { _, sHour, sMinute ->
                        btDialog.text = "$sHour - $sMinute"
                    }, hour, minutes, true)
            picker?.show()
        }

        btDialogRange.setSafeOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cal.get(Calendar.MINUTE)
            pickerRange = RangeTimePickerDialog(activity,
                    TimePickerDialog.OnTimeSetListener { _, sHour, sMinute ->
                        btDialogRange.text = "$sHour - $sMinute"
                        logD("$sHour - $sMinute")
                    }, hour, minutes, true)

            pickerRange?.let {
                it.setMin(5, 30)
                it.setMax(10, 20)
                it.show()
            }
        }
    }
}
