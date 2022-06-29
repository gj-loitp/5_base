package vn.loitp.app.activity.function.theme

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_theme.*
import vn.loitp.app.R

@LogTag("ThemeActivity")
@IsFullScreen(false)
class ThemeActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_theme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
