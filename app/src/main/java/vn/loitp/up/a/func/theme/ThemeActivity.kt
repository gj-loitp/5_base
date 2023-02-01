package vn.loitp.up.a.func.theme

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.isDarkTheme
import com.loitp.core.ext.setDarkTheme
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AThemeBinding

@LogTag("ThemeActivity")
@IsFullScreen(false)
class ThemeActivity : BaseActivityFont() {

    private lateinit var binding: AThemeBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ThemeActivity::class.java.simpleName
        }
        binding.btChangeTheme.setSafeOnClickListener {
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
