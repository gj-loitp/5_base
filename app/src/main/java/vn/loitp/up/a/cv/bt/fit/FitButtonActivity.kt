package vn.loitp.up.a.cv.bt.fit

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.github.nikartm.button.model.Shape
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AFitButtonBinding

@LogTag("FitButtonActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FitButtonActivity : BaseActivityFont() {
    private lateinit var binding: AFitButtonBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFitButtonBinding.inflate(layoutInflater)
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
                            url = "https://github.com/nikartm/FitButton"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FitButtonActivity::class.java.simpleName
        }

        setupButton()
    }

    private fun setupButton() {
        binding.fbtn.setTextFont(R.font.bungee)
            .setWidth(200)
            .setHeight(70)
            .setTextSize(20f)
            .setIconMarginStart(16f)
            .setIconMarginEnd(12f)
            .setTextColor(Color.parseColor("#F5F5F5"))
            .setIconColor(Color.parseColor("#FFFFFF"))
            .setDividerColor(Color.parseColor("#BCAAA4"))
            .setBorderColor(Color.parseColor("#FFF59D"))
            .setButtonColor(Color.parseColor("#FF7043"))
            .setBorderWidth(2f)
            .setRippleEnable(true)
            .setRippleColor(Color.RED)
            .setOnClickListener {
                changeButton()
                showShortInformation("Click on ${binding.fbtn.getText()}")
            }
    }

    private fun changeButton() {
        binding.fbtn.setButtonShape(Shape.CIRCLE)
            .setDividerVisibility(View.GONE)
            .setTextVisibility(View.GONE)
    }

}
