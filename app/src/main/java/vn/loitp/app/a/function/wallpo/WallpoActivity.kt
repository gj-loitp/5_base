package vn.loitp.app.a.function.wallpo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_wallpo.*
import vn.loitp.R

@LogTag("WallpoActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class WallpoActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_wallpo
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
            this.tvTitle?.text = WallpoActivity::class.java.simpleName
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
                activity = this,
                imageView = ivPreview,
                message = "Wallpaper Set",
                isSetWallpaper = true,
                isSetLockScreen = false,
            )
        }

        btSetLockscreen.setSafeOnClickListener {
            LUIUtil.setWallpaperAndLockScreen(
                activity = this,
                imageView = ivPreview,
                message = "LockWallpaper Set",
                isSetWallpaper = false,
                isSetLockScreen = true,
            )
        }
    }
}
