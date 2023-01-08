package vn.loitp.a.cv.bt.circularProgress

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import kotlinx.android.synthetic.main.l_cpb_sample_5.*
import vn.loitp.R

@LogTag("Sample5Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample5ActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.l_cpb_sample_5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        circularButton1.isIndeterminateProgressMode = true
        circularButton1.setOnClickListener {
            when (circularButton1.progress) {
                0 -> {
                    circularButton1.progress = 50
                }
                100 -> {
                    circularButton1.progress = 0
                }
                else -> {
                    circularButton1.progress = 100
                }
            }
        }
        circularButton2.isIndeterminateProgressMode = true
        circularButton2.setOnClickListener {
            when (circularButton2.progress) {
                0 -> {
                    circularButton2.progress = 50
                }
                -1 -> {
                    circularButton2.progress = 0
                }
                else -> {
                    circularButton2.progress = -1
                }
            }
        }
    }
}
