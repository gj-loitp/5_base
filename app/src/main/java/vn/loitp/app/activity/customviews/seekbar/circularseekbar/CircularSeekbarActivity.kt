package vn.loitp.app.activity.customviews.seekbar.circularseekbar

import android.graphics.Color
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.google.android.material.snackbar.Snackbar
import com.views.seekbar.circular.LCircularSeekBar
import kotlinx.android.synthetic.main.activity_seekbar_circular.*
import vn.loitp.app.R
import java.text.DecimalFormat

//https://github.com/akaita/CircularSeekBar

@LogTag("CircularSeekbarActivity")
@IsFullScreen(false)
class CircularSeekbarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_seekbar_circular
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        seekbar.progressTextFormat = DecimalFormat("###,###,###,##0.00")
        seekbar.progress = 0f
        seekbar.ringColor = Color.DKGRAY

        seekbar.setOnCenterClickedListener { sb, progress ->
            Snackbar.make(sb, "Reset progress $progress", Snackbar.LENGTH_SHORT).show()
            sb.progress = 0f
        }
        seekbar.setOnCircularSeekBarChangeListener(object : LCircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(seekBar: LCircularSeekBar, progress: Float, fromUser: Boolean) {
                when {
                    progress < 20 -> seekBar.ringColor = Color.GRAY
                    progress < 40 -> seekBar.ringColor = Color.BLUE
                    progress < 60 -> seekBar.ringColor = Color.GREEN
                    progress < 80 -> seekBar.ringColor = Color.YELLOW
                    else -> seekBar.ringColor = Color.RED
                }
            }

            override fun onStartTrackingTouch(seekBar: LCircularSeekBar) {
                // Nothing
            }

            override fun onStopTrackingTouch(seekBar: LCircularSeekBar) {
                // Nothing
            }
        })
    }

}
