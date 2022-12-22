package vn.loitp.a.customView.bt.circularProgressButton

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.layout_cpb_sample_5.*
import vn.loitp.R

@LogTag("Sample5Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample5Activity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.layout_cpb_sample_5
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
