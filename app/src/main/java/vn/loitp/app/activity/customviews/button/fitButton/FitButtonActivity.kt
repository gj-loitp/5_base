package vn.loitp.app.activity.customviews.button.fitButton

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.github.nikartm.button.model.Shape
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_fit_button.*
import vn.loitp.app.R

@LogTag("FitButtonActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FitButtonActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fit_button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
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
                            url = "https://github.com/nikartm/FitButton"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FitButtonActivity::class.java.simpleName
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
