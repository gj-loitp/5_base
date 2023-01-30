package vn.loitp.up.a.picker.time

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_picker_time.*
import vn.loitp.R
import vn.loitp.databinding.APickerTimeBinding
import java.util.*

@LogTag("TimePickerActivity")
@IsFullScreen(false)
class TimePickerActivity : BaseActivityFont() {

    private lateinit var binding: APickerTimeBinding

    private var picker: TimePickerDialog? = null
    private var pickerRange: RangeTimePickerDialog? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APickerTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = TimePickerActivity::class.java.simpleName
        }
        binding.timePickerSpiner.setIs24HourView(true)

        binding.timePickerClock.setOnTimeChangedListener { _, hourOfDay, minute ->
            tvTimePickerClock.text = "$hourOfDay - $minute"
        }
        binding.timePickerSpiner.setOnTimeChangedListener { _, hourOfDay, minute ->
            tvTimePickeSpinner.text = "$hourOfDay - $minute"
        }
        binding.btDialog.setSafeOnClickListener {
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

        binding.btDialogRange.setSafeOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cal.get(Calendar.MINUTE)
            pickerRange = RangeTimePickerDialog(
                this,
                { _, sHour, sMinute ->
                    binding.btDialogRange.text = "$sHour - $sMinute"
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
