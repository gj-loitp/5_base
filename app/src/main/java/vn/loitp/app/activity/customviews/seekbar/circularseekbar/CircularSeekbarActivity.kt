package vn.loitp.app.activity.customviews.seekbar.circularseekbar

import android.graphics.Color
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.google.android.material.snackbar.Snackbar
import com.views.seekbar.circular.LCircularSeekBar
import vn.loitp.app.R
import java.text.DecimalFormat

//https://github.com/akaita/CircularSeekBar
class CircularSeekbarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val seekBar = findViewById<LCircularSeekBar>(R.id.seekbar)
        seekBar.progressTextFormat = DecimalFormat("###,###,###,##0.00")
        seekBar.progress = 0f
        seekBar.ringColor = Color.DKGRAY

        seekBar.setOnCenterClickedListener { seekBar1, progress ->
            Snackbar.make(seekBar1, "Reset progress $progress", Snackbar.LENGTH_SHORT).show()
            seekBar1.progress = 0f
        }
        seekBar.setOnCircularSeekBarChangeListener(object : LCircularSeekBar.OnCircularSeekBarChangeListener {
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

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_circular_seekbar
    }
}
