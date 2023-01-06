package vn.loitp.a.cv.androidRibbon

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_ribbon.*
import vn.loitp.R

@LogTag("RibbonActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RibbonActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_ribbon
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
                            url = "https://github.com/skydoves/AndroidRibbon"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = RibbonActivityFont::class.java.simpleName
        }

        ribbonLayout.ribbonHeader = RibbonFactory.getChristmasRibbonHeader(this@RibbonActivityFont)
        ribbonLayout.ribbonBottom = RibbonFactory.getChristmasRibbonBottom(this@RibbonActivityFont)
        ribbonLayout01.ribbonHeader =
            RibbonFactory.getChristmasPinkRibbonHeader(this@RibbonActivityFont)
        ribbonLayout01.ribbonBottom =
            RibbonFactory.getChristmasPinkRibbonBottom(this@RibbonActivityFont)
        ribbonLayout02.ribbonHeader = RibbonFactory.getPresentRibbonHeader(this@RibbonActivityFont)
        ribbonLayout02.ribbonBottom = RibbonFactory.getPresentRibbonBottom(this@RibbonActivityFont)

        ribbonLayout.setOnClickListener {
            launchActivity(SecondActivityFont::class.java)
        }
        ribbonLayout01.setOnClickListener {
            launchActivity(SecondActivityFont::class.java)
        }
        ribbonLayout02.setOnClickListener {
            launchActivity(SecondActivityFont::class.java)
        }
    }
}