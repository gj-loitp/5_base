package vn.loitp.up.a.cv.bt.circularProgress

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.LCpbSample3Binding

@LogTag("Sample3Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample3Activity : BaseActivityFont() {
    private lateinit var binding: LCpbSample3Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LCpbSample3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.circularButton1.setOnClickListener {
            if (binding.circularButton1.progress == 0) {
                binding.circularButton1.progress = 100
            } else {
                binding.circularButton1.progress = 0
            }
        }
        binding.circularButton2.setOnClickListener {
            if (binding.circularButton2.progress == 0) {
                binding.circularButton2.progress = -1
            } else {
                binding.circularButton2.progress = 0
            }
        }
    }

}
