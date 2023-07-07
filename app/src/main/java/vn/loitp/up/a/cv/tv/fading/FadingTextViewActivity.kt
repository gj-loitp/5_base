package vn.loitp.up.a.cv.tv.fading

import android.annotation.SuppressLint
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
import vn.loitp.databinding.ATvFadingBinding

@LogTag("FadingTextViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FadingTextViewActivity : BaseActivityFont() {

    private lateinit var binding: ATvFadingBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvFadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/rosenpin/fading-text-view"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FadingTextViewActivity::class.java.simpleName
        }

        val texts = arrayOf(
            "You",
            "can",
            "use",
            "an",
            "array",
            "resource",
            "or a string array",
            "as",
            "the parameter",
        )
        binding.ftv.setTexts(texts)
        binding.ftv.setTimeout(1L, java.util.concurrent.TimeUnit.SECONDS)

    }
}
