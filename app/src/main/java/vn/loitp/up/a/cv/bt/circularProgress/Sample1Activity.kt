package vn.loitp.up.a.cv.bt.circularProgress

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.LCpbSample1Binding

@LogTag("Sample1Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample1Activity : BaseActivityFont() {
    private lateinit var binding: LCpbSample1Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LCpbSample1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.circularButton1.isIndeterminateProgressMode = true
        binding.circularButton1.setOnClickListener {
            when (binding.circularButton1.progress) {
                0 -> {
                    binding.circularButton1.progress = 50
                }
                100 -> {
                    binding.circularButton1.progress = 0
                }
                else -> {
                    binding.circularButton1.progress = 100
                }
            }
        }
        binding.circularButton2.isIndeterminateProgressMode = true
        binding.circularButton2.setOnClickListener {
            when (binding.circularButton2.progress) {
                0 -> {
                    binding.circularButton2.progress = 50
                }
                -1 -> {
                    binding.circularButton2.progress = 0
                }
                else -> {
                    binding.circularButton2.progress = -1
                }
            }
        }
    }

}
