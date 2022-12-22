package vn.loitp.a.customView.bt.circularProgressButton

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_cpb.*
import vn.loitp.R

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
