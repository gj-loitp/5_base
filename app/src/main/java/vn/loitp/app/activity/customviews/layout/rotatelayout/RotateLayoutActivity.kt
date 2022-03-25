package vn.loitp.app.activity.customviews.layout.rotatelayout

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDeviceUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_layout_rotate.*
import vn.loitp.app.R

@LogTag("RotateLayoutActivity")
@IsFullScreen(false)
class RotateLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_rotate
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
                    onBackPressed()
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
