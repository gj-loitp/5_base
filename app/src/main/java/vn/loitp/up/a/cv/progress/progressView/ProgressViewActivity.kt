package vn.loitp.up.a.cv.progress.progressView

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
import vn.loitp.databinding.APvBinding

@LogTag("ProgressViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ProgressViewActivity : BaseActivityFont() {
    private lateinit var binding: APvBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            progressView.setOnProgressChangeListener {
                progressView.labelText = "heart ${it.toInt()}%"
            }
            progressView1.setOnProgressChangeListener {
                progressView1.labelText = "star ${it.toInt()}%"
            }
            progressView2.setOnProgressChangeListener {
                progressView2.labelText = "achieve ${it.toInt()}%"
            }
            progressView3.setOnProgressChangeListener {
                progressView3.labelText = "score ${it.toInt()}/100"
            }
            progressView4.setOnProgressChangeListener {
                progressView4.labelText = "achieve ${it.toInt()}%"
            }
            progressView5.setOnProgressChangeListener {
                progressView5.labelText = "achieve ${it.toInt()}%"
            }

            progressView.setOnProgressClickListener {
                showShortInformation("setOnProgressClickListener")
            }

            progressView1.setOnProgressClickListener {
                showShortInformation("setOnProgressClickListener")
            }

            button.setOnClickListener {
                progressView.progress += 25
                progressView1.progress += 10
                progressView2.progress += 25
                progressView3.progress += 10
                progressView4.progress += 5
                progressView5.progress += 15
            }
        }

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
                            url = "https://github.com/skydoves/ProgressView"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ProgressViewActivity::class.java.simpleName
        }
    }

}
