package vn.loitp.a.cv.progress.tileProgressView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_tile_progress_view.*
import vn.loitp.R

@LogTag("TileProgressViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TileProgressViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_tile_progress_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = this,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/iammert/TileProgressView"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = TileProgressViewActivity::class.java.simpleName
        }

        tileProgressView.setProgress(6f)
    }
}
