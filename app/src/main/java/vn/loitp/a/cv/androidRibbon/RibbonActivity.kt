package vn.loitp.a.cv.androidRibbon

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_ribbon.*
import vn.loitp.R

@LogTag("RibbonActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RibbonActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_ribbon
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
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/skydoves/AndroidRibbon"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = RibbonActivity::class.java.simpleName
        }

        ribbonLayout.ribbonHeader = RibbonFactory.getChristmasRibbonHeader(this@RibbonActivity)
        ribbonLayout.ribbonBottom = RibbonFactory.getChristmasRibbonBottom(this@RibbonActivity)
        ribbonLayout01.ribbonHeader =
            RibbonFactory.getChristmasPinkRibbonHeader(this@RibbonActivity)
        ribbonLayout01.ribbonBottom =
            RibbonFactory.getChristmasPinkRibbonBottom(this@RibbonActivity)
        ribbonLayout02.ribbonHeader = RibbonFactory.getPresentRibbonHeader(this@RibbonActivity)
        ribbonLayout02.ribbonBottom = RibbonFactory.getPresentRibbonBottom(this@RibbonActivity)

        ribbonLayout.setOnClickListener {
            launchActivity(SecondActivity::class.java)
        }
        ribbonLayout01.setOnClickListener {
            launchActivity(SecondActivity::class.java)
        }
        ribbonLayout02.setOnClickListener {
            launchActivity(SecondActivity::class.java)
        }
    }
}
