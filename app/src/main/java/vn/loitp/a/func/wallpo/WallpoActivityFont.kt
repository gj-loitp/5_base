package vn.loitp.a.func.wallpo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.URL_IMG_1
import com.loitp.core.ext.*
import kotlinx.android.synthetic.main.a_wallpo.*
import vn.loitp.R

@LogTag("WallpoActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class WallpoActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_wallpo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
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
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = WallpoActivityFont::class.java.simpleName
        }

        btChangeImageNetwork.setSafeOnClickListener {
            ivPreview.loadGlide(
                any = URL_IMG_1,
            )
        }

        btSetWallpaper.setSafeOnClickListener {
            ivPreview.setWallpaperAndLockScreen(
                isSetWallpaper = true,
                isSetLockScreen = false,
            )
            showShortInformation("Wallpaper set successfully")
        }
        btSetLockscreen.setSafeOnClickListener {
            ivPreview.setWallpaperAndLockScreen(
                isSetWallpaper = false,
                isSetLockScreen = true,
            )
            showShortInformation("Lock Screen Wallpaper set successfully")
        }
        btSetRandomColor.setSafeOnClickListener {
            this.setWallpaperAndLockScreen(
                color = getRandomColor(),
                isSetWallpaper = true,
                isSetLockScreen = true,
            )
        }
    }
}
