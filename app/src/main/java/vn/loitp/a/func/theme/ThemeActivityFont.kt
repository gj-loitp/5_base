package vn.loitp.a.func.theme

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.isDarkTheme
import com.loitp.core.ext.setDarkTheme
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_theme.*
import vn.loitp.R

@LogTag("ThemeActivity")
@IsFullScreen(false)
class ThemeActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_theme
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ThemeActivityFont::class.java.simpleName
        }
        btChangeTheme.setSafeOnClickListener {
            val isDarkTheme = isDarkTheme()
            if (isDarkTheme) {
                setDarkTheme(isDarkTheme = false)
            } else {
                setDarkTheme(isDarkTheme = true)
            }
            recreate()
        }
    }
}
