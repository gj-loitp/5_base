package vn.loitp.app.activity.picker.timePicker

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_time_picker.*
import vn.loitp.app.R
import java.util.*

@LogTag("TimePickerActivity")
@IsFullScreen(false)
class TimePickerActivity : BaseFontActivity() {

    private var picker: TimePickerDialog? = null
    private var pickerRange: RangeTimePickerDialog? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_time_picker
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = TimePickerActivity::class.java.simpleName
        }
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
            picker = TimePickerDialog(
                this,
                { _, sHour, sMinute ->
                    btDialog.text = "$sHour - $sMinute"
                }, hour, minutes, true
            )
            picker?.show()
        }

        btDialogRange.setSafeOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cal.get(Calendar.MINUTE)
            pickerRange = RangeTimePickerDialog(
                this,
                { _, sHour, sMinute ->
                    btDialogRange.text = "$sHour - $sMinute"
                    logD("$sHour - $sMinute")
                }, hour, minutes, true
            )

            pickerRange?.let {
                it.setMin(5, 30)
                it.setMax(10, 20)
                it.show()
            }
        }
    }
}
