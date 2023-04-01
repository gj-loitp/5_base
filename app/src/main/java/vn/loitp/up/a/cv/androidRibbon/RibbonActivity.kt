package vn.loitp.up.a.cv.androidRibbon

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ARibbonBinding

@LogTag("RibbonActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RibbonActivity : BaseActivityFont() {
    private lateinit var binding: ARibbonBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARibbonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
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

        binding.ribbonLayout.ribbonHeader =
            RibbonFactory.getChristmasRibbonHeader(this@RibbonActivity)
        binding.ribbonLayout.ribbonBottom =
            RibbonFactory.getChristmasRibbonBottom(this@RibbonActivity)
        binding.ribbonLayout01.ribbonHeader =
            RibbonFactory.getChristmasPinkRibbonHeader(this@RibbonActivity)
        binding.ribbonLayout01.ribbonBottom =
            RibbonFactory.getChristmasPinkRibbonBottom(this@RibbonActivity)
        binding.ribbonLayout02.ribbonHeader =
            RibbonFactory.getPresentRibbonHeader(this@RibbonActivity)
        binding.ribbonLayout02.ribbonBottom =
            RibbonFactory.getPresentRibbonBottom(this@RibbonActivity)

        binding.ribbonLayout.setOnClickListener {
            launchActivity(SecondActivity::class.java)
        }
        binding.ribbonLayout01.setOnClickListener {
            launchActivity(SecondActivity::class.java)
        }
        binding.ribbonLayout02.setOnClickListener {
            launchActivity(SecondActivity::class.java)
        }
    }
}
