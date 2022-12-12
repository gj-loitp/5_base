package vn.loitp.app.activity.customviews.button.circularProgressButton

import android.os.Bundle
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.ac_sample_1.*
import vn.loitp.app.R

@LogTag("Sample1Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample1Activity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.ac_sample_1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val actionBar = actionBar
        actionBar?.setTitle(R.string.IndeterminateProgressSample)
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
