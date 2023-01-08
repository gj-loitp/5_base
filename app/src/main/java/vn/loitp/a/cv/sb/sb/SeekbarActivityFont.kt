package vn.loitp.a.cv.sb.sb

import android.os.Bundle
import android.widget.SeekBar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_sb.*
import vn.loitp.R

@LogTag("SeekbarActivity")
@IsFullScreen(false)
class SeekbarActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sb
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
            this.tvTitle?.text = SeekbarActivityFont::class.java.simpleName
        }
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
}
