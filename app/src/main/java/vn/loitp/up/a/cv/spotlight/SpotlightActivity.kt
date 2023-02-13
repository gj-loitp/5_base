package vn.loitp.up.a.cv.spotlight

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.matadesigns.spotlight.SpotlightBuilder
import com.matadesigns.spotlight.abstraction.SpotlightListener
import vn.loitp.R
import vn.loitp.databinding.ASpotlightBinding

@LogTag("SpotlightActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SpotlightActivity : BaseActivityFont() {
    private lateinit var binding: ASpotlightBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ASpotlightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupSpotlight()
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
                            url = "https://github.com/NicholasMata/Spotlight"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = SpotlightActivity::class.java.simpleName
        }
    }

    private fun setupSpotlight() {
        var builder: SpotlightBuilder? = null
        builder = SpotlightBuilder(this)
            .setInset(20)
            .setTargetView(binding.firstView)
            .setTitle("First View")
            .setDescription("This is the first view")
            .setListener(object : SpotlightListener {
                override fun onEnd(targetView: View?) {
                    // This is called when a target view has been dismissed
                    when (targetView) {
                        binding.firstView -> {
                            // When the first view's spotlight ends then set the
                            // target view to the second view.
                            // Update title and description
                            builder?.setTitle("Second View")
                                ?.setDescription("This is the second view")
                                ?.setTargetView(binding.secondView)
                        }
                        binding.secondView -> {
                            // When the second view's spotlight ends don't continue on.
                            return
                        }
                    }
                    builder?.build()?.startSpotlight()
                }

                override fun onStart(targetView: View?) {}
            })
        builder.build().startSpotlight()
    }
}
