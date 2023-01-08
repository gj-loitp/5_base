package vn.loitp.a.cv.sw.appCompat

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.isDarkTheme
import com.loitp.core.ext.setDarkTheme
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.utilities.LStoreUtil
import kotlinx.android.synthetic.main.a_sw_appcompat.*
import vn.loitp.R

@LogTag("AppcompatSwitchActivity")
@IsFullScreen(false)
class AppcompatSwitchActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sw_appcompat
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
            this.tvTitle?.text = AppcompatSwitchActivityFont::class.java.simpleName
        }
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.lswitch)

        val isDarkTheme = isDarkTheme()
        sw.isChecked = isDarkTheme

        sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDarkTheme(isDarkTheme = true)
            } else {
                setDarkTheme(isDarkTheme = false)
            }
        }
    }
}
