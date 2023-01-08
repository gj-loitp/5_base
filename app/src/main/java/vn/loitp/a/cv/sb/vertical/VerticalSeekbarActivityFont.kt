package vn.loitp.a.cv.sb.vertical

import android.os.Bundle
import android.widget.SeekBar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_sb_vert.*
import vn.loitp.R

// https://github.com/h6ah4i/android-verticalseekbar

@LogTag("VerticalSeekbarActivity")
@IsFullScreen(false)
class VerticalSeekbarActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sb_vert
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = VerticalSeekbarActivityFont::class.java.simpleName
        }

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
        btSetProgress.setOnClickListener {
            seekBar1.progress = 30
        }
    }
}
