package vn.loitp.app.activity.customviews.button.circularProgressButton

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.layout_cpb_sample_3.*
import vn.loitp.app.R

@LogTag("Sample3Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample3Activity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.layout_cpb_sample_3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        circularButton1.setOnClickListener {
            if (circularButton1.progress == 0) {
                circularButton1.progress = 100
            } else {
                circularButton1.progress = 0
            }
        }
        circularButton2.setOnClickListener {
            if (circularButton2.progress == 0) {
                circularButton2.progress = -1
            } else {
                circularButton2.progress = 0
            }
        }
    }

}
