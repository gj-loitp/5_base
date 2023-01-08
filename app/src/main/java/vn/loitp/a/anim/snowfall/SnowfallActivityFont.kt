package vn.loitp.a.anim.snowfall

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_snowfall.*
import vn.loitp.R

@LogTag("SnowfallActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SnowfallActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_snowfall
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
                            url = "https://github.com/JetradarMobile/android-snowfall"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = SnowfallActivityFont::class.java.simpleName
        }
    }

}
