package vn.loitp.app.activity.customviews.button.circularProgressButton

import android.os.Bundle
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.layout_cpb_sample_4.*
import vn.loitp.app.R

@LogTag("Sample4Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample4Activity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.layout_cpb_sample_4
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
            if (circularButton2.progress == 0) {
                circularButton2.progress = 50
            } else if (circularButton2.progress == -1) {
                circularButton2.progress = 0
            } else {
                circularButton2.progress = -1
            }
        }
    }

}
