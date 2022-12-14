package vn.loitp.app.activity.customviews.button.circularProgressButton

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_cpb.*
import vn.loitp.app.R

@LogTag("CPBActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CPBActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_cpb
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
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/dmytrodanylyk/circular-progress-button"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CPBActivity::class.java.simpleName
        }

        bt1.setSafeOnClickListener {
            launchActivity(Sample1Activity::class.java)
        }
        bt2.setSafeOnClickListener {
            launchActivity(Sample2Activity::class.java)
        }
        bt3.setSafeOnClickListener {
            launchActivity(Sample3Activity::class.java)
        }
        bt4.setSafeOnClickListener {
            launchActivity(Sample4Activity::class.java)
        }
        bt5.setSafeOnClickListener {
            launchActivity(Sample5Activity::class.java)
        }
    }
}
