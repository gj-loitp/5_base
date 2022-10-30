package vn.loitp.app.activity.customviews.layout.rotateLayout

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LDeviceUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_rotate_layout.*
import vn.loitp.app.R

@LogTag("RotateLayoutActivity")
@IsFullScreen(false)
class RotateLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rotate_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = RotateLayoutActivity::class.java.simpleName
        }
        btRandomRotate.setSafeOnClickListener {
            val angle = LDeviceUtil.getRandomNumber(360)
            rotateLayout.setAngle(angle)
        }
    }
}
