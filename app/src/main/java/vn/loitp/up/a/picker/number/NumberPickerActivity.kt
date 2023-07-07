package vn.loitp.up.a.picker.number

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.picker.number.LNumberPicker
import vn.loitp.R
import vn.loitp.databinding.APickerNumberPickerBinding

@LogTag("NumberPickerActivity")
@IsFullScreen(false)
class NumberPickerActivity : BaseActivityFont() {

    private lateinit var binding: APickerNumberPickerBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APickerNumberPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = NumberPickerActivity::class.java.simpleName
        }
        binding.lnb.setCallback(object : LNumberPicker.Callback {
            @SuppressLint("SetTextI18n")
            override fun onValueChangedNumberPicker(h: String, m: String, s: String) {
                binding.textView.text = "$h : $m : $s"
            }
        })

        binding.btSet.setSafeOnClickListener {
            binding.lnb.setMinMaxValue(valueH = 1, valueM = 30, valueS = 45)
        }
        binding.btMin.setSafeOnClickListener {
            binding.lnb.setMinValue(minValueH = 5, minValueM = 5, minValueS = 5)
        }
        binding.btMax.setSafeOnClickListener {
            binding.lnb.setMaxValue(maxValueH = 10, maxValueM = 10, maxValueS = 10)
        }
        binding.btReset.setSafeOnClickListener {
            binding.lnb.resetMinMaxValue()
        }
    }
}
