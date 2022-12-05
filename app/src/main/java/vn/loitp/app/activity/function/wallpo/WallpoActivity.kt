package vn.loitp.app.activity.function.wallpo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.github.wallpoo.Wallpo
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_wallpo.*
import vn.loitp.app.R

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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = WallpoActivity::class.java.simpleName
        }

        btSetWallpaper.setSafeOnClickListener {
            Wallpo.setMainScreenWallpaper(
                /* context = */ this,
                /* imageView = */ ivLocal,
                /* message = */ "Wallpaper Set"
            )
        }

        btSetLockscreen.setSafeOnClickListener {
            Wallpo.setLockScreenWallpaper(
                /* activity = */ this,
                /* imageView = */ ivLocal,
                /* message = */ "LockWallpaper Set"
            )
        }
    }
}
