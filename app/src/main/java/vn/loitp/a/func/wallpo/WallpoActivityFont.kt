package vn.loitp.a.func.wallpo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
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
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
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
            LImageUtil.load(
                context = this,
                any = Constants.URL_IMG_1,
                imageView = ivPreview
            )
        }

        btSetWallpaper.setSafeOnClickListener {
            LUIUtil.setWallpaperAndLockScreen(
                context = this,
                imageView = ivPreview,
                isSetWallpaper = true,
                isSetLockScreen = false,
            )
            showShortInformation("Wallpaper set successfully")
        }
        btSetLockscreen.setSafeOnClickListener {
            LUIUtil.setWallpaperAndLockScreen(
                context = this,
                imageView = ivPreview,
                isSetWallpaper = false,
                isSetLockScreen = true,
            )
            showShortInformation("Lock Screen Wallpaper set successfully")
        }
        btSetRandomColor.setSafeOnClickListener {
            LUIUtil.setWallpaperAndLockScreen(
                context = this,
                color = LUIUtil.getRandomColor(),
                isSetWallpaper = true,
                isSetLockScreen = true,
            )
        }
    }
}
