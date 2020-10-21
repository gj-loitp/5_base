package vn.loitp.app.activity.function.theme

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_theme.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_theme)
@LogTag("ThemeActivity")
@IsFullScreen(false)
class ThemeActivity : BaseFontActivity() {

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
