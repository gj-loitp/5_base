package vn.loitp.a.func.processPhoenix

import android.os.Bundle
import androidx.core.view.isVisible
import com.jakewharton.processphoenix.ProcessPhoenix
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_process_phoenix.*
import vn.loitp.R

@LogTag("ProcessPhoenixActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ProcessPhoenixActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_process_phoenix
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
                            url = "https://github.com/JakeWharton/ProcessPhoenix"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ProcessPhoenixActivity::class.java.simpleName
        }

        btStart.setSafeOnClickListener {
            ProcessPhoenix.triggerRebirth(this)
        }
    }
}
