package vn.loitp.app.activity.customviews.seekbar.verticalseekbar

import android.os.Bundle
import android.widget.SeekBar
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_seekbar_vertical.*
import vn.loitp.app.R

//https://github.com/h6ah4i/android-verticalseekbar
class VerticalSeekbarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        seekBar1.max = 100
        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                logD("onProgressChanged $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                logD("onStartTrackingTouch")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                logD("onStopTrackingTouch")
            }
        })
        btSetProgress.setOnClickListener { _ -> seekBar1.progress = 30 }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_seekbar_vertical
    }
}
