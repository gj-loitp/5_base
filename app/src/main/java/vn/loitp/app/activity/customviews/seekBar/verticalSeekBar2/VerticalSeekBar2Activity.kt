package vn.loitp.app.activity.customviews.seekBar.verticalSeekBar2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_vertical_seek_bar.*
import vn.loitp.app.R

@LogTag("VerticalSeekBar2Activity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class VerticalSeekBar2Activity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_vertical_seek_bar
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
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/lukelorusso/VerticalSeekBar"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = VerticalSeekBar2Activity::class.java.simpleName
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
