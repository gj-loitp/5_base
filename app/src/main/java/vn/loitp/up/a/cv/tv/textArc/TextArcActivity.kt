package vn.loitp.up.a.cv.tv.textArc

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.a_lab.textarc.TextArc
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ATextarcBinding

@LogTag("TextArcActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TextArcActivity : BaseActivityFont() {
    private lateinit var binding: ATextarcBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATextarcBinding.inflate(layoutInflater)
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
                            url = "https://github.com/AndroidLab/textarc"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = TextArcActivity::class.java.simpleName
        }

        val textArc = TextArc(this)
        textArc.setText("Text arc that was added programmatically")
        textArc.setRadius(255)
        textArc.setCenterAngle(-90)
        textArc.setTextColor(Color.GREEN)
        textArc.setTextSize(72)

        binding.ll.addView(textArc)
    }
}
