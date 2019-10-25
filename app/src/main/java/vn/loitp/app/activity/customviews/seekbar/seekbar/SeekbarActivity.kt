package vn.loitp.app.activity.customviews.seekbar.seekbar

import android.os.Bundle
import android.widget.SeekBar

import com.core.base.BaseFontActivity
import com.core.utilities.LLog

import loitp.basemaster.R

class SeekbarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sb = findViewById<SeekBar>(R.id.sb)
        sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_seekbar
    }

}
