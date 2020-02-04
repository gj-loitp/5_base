package vn.loitp.app.activity.customviews.seekbar.verticalseekbar

import android.os.Bundle
import android.view.View
import android.widget.SeekBar

import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.views.seekbar.vertical.LVerticalSeekBar

import loitp.basemaster.R

//https://github.com/h6ah4i/android-verticalseekbar
class VerticalSeekbarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val seekBar1 = findViewById<LVerticalSeekBar>(R.id.seekBar1)
        seekBar1.max = 100
        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                LLog.d(TAG, "onProgressChanged $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                LLog.d(TAG, "onStartTrackingTouch")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                LLog.d(TAG, "onStopTrackingTouch")
            }
        })
        findViewById<View>(R.id.bt).setOnClickListener { _ -> seekBar1.progress = 30 }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_vertical_seekbar
    }
}
