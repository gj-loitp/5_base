package vn.loitp.up.a.func.wallpo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.common.URL_IMG_1
import com.loitp.core.ext.*
import vn.loitp.R
import vn.loitp.databinding.AWallpoBinding

@LogTag("WallpoActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class WallpoActivity : BaseActivityFont() {

    private lateinit var binding: AWallpoBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AWallpoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/sayyedrizwan/wallpo"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = WallpoActivity::class.java.simpleName
        }

        binding.btChangeImageNetwork.setSafeOnClickListener {
            binding.ivPreview.loadGlide(
                any = URL_IMG_1,
            )
        }
        binding.btSetWallpaper.setSafeOnClickListener {
            binding.ivPreview.setWallpaperAndLockScreen(
                isSetWallpaper = true,
                isSetLockScreen = false,
            )
            showShortInformation("Wallpaper set successfully")
        }
        binding.btSetLockscreen.setSafeOnClickListener {
            binding.ivPreview.setWallpaperAndLockScreen(
                isSetWallpaper = false,
                isSetLockScreen = true,
            )
            showShortInformation("Lock Screen Wallpaper set successfully")
        }
        binding.btSetRandomColor.setSafeOnClickListener {
            this.setWallpaperAndLockScreen(
                color = getRandomColor(),
                isSetWallpaper = true,
                isSetLockScreen = true,
            )
        }
    }
}
