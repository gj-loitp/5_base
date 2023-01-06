package vn.loitp.a.cv.sb.vertical2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_sb_vertical.*
import vn.loitp.R

@LogTag("VerticalSeekBar2Activity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class VerticalSeekBar2ActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sb_vertical
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/lukelorusso/VerticalSeekBar"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = VerticalSeekBar2ActivityFont::class.java.simpleName
        }
        mainVerticalSeekBar.apply {
            progress = 75

            setOnProgressChangeListener { progressValue ->
                mainProgressValue.text = progressValue.toString()
            }

            setOnPressListener { progressValue ->
                logD("PRESSED at value: $progressValue")
            }

            setOnReleaseListener { progressValue ->
                logD("RELEASED at value: $progressValue")
            }
        }
        mainProgressValue.text = mainVerticalSeekBar.progress.toString()
    }
}
