package vn.loitp.up.a.cv.sw.appCompat

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.isDarkTheme
import com.loitp.core.ext.readTxtFromRawFolder
import com.loitp.core.ext.setDarkTheme
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ASwAppcompatBinding

@LogTag("AppcompatSwitchActivity")
@IsFullScreen(false)
class AppcompatSwitchActivity : BaseActivityFont() {
    private lateinit var binding: ASwAppcompatBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASwAppcompatBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = AppcompatSwitchActivity::class.java.simpleName
        }
        binding.textView.text = readTxtFromRawFolder(nameOfRawFile = R.raw.lswitch)

        val isDarkTheme = isDarkTheme()
        binding.sw.isChecked = isDarkTheme

        binding.sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDarkTheme(isDarkTheme = true)
            } else {
                setDarkTheme(isDarkTheme = false)
            }
        }
    }
}
