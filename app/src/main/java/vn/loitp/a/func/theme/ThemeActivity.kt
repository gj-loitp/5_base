package vn.loitp.a.func.theme

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_theme.*
import vn.loitp.R

@LogTag("ThemeActivity")
@IsFullScreen(false)
class ThemeActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_theme
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ThemeActivity::class.java.simpleName
        }
        btChangeTheme.setSafeOnClickListener {
            val isDarkTheme = LUIUtil.isDarkTheme()
            if (isDarkTheme) {
                LUIUtil.setDarkTheme(isDarkTheme = false)
            } else {
                LUIUtil.setDarkTheme(isDarkTheme = true)
            }
            recreate()
        }
    }
}
