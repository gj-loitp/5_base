package vn.loitp.app.activity.customviews.seekbar.seekbar

import android.os.Bundle
import android.widget.SeekBar
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_seekbar.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_seekbar)
@LogTag("SeekbarActivity")
class SeekbarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
    }

    override fun setFullScreen(): Boolean {
        return false
    }
}
