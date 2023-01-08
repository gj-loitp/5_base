package vn.loitp.a.cv.bt.fit

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
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_fit_button.*
import vn.loitp.R

@LogTag("FitButtonActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FitButtonActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_fit_button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
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
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FitButtonActivityFont::class.java.simpleName
        }

        setupButton()
    }

    private fun setupButton() {
        fbtn.setTextFont(R.font.bungee)
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
                showShortInformation("Click on ${fbtn.getText()}")
            }
    }

    private fun changeButton() {
        fbtn.setButtonShape(Shape.CIRCLE)
            .setDividerVisibility(View.GONE)
            .setTextVisibility(View.GONE)
    }

}
