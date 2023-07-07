package vn.loitp.up.a.cv.bt.circularProgress

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.LCpbSample4Binding

@LogTag("Sample4Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample4Activity : BaseActivityFont() {
    private lateinit var binding: LCpbSample4Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LCpbSample4Binding.inflate(layoutInflater)
        setContentView(binding.root)

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
