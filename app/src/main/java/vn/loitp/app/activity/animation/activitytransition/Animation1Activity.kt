package vn.loitp.app.activity.animation.activitytransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LActivityUtil
import com.data.ActivityData
import kotlinx.android.synthetic.main.activity_animation_1.*
import vn.loitp.app.R

@LogTag("Animation1Activity")
@IsFullScreen(false)
class Animation1Activity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btNoAnim.setOnClickListener(this)
        btSystemDefault.setOnClickListener(this)
        btSlideLeft.setOnClickListener(this)
        btSlideRight.setOnClickListener(this)
        btSlideDown.setOnClickListener(this)
        btSlideUp.setOnClickListener(this)
        btFade.setOnClickListener(this)
        btZoom.setOnClickListener(this)
        btWindMill.setOnClickListener(this)
        btDiagonal.setOnClickListener(this)
        btSpin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent = Intent(this, Animation2Activity::class.java)
        startActivity(intent)
        when (v) {
            btNoAnim -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
                LActivityUtil.transActivityNoAnimation(this)
            }
            btSystemDefault -> ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
            btSlideLeft -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT
                LActivityUtil.slideLeft(this)
            }
            btSlideRight -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT
                LActivityUtil.slideRight(this)
            }
            btSlideDown -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN
                LActivityUtil.slideDown(this)
            }
            btSlideUp -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP
                LActivityUtil.slideUp(this)
            }
            btFade -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_FADE
                LActivityUtil.fade(this)
            }
            btZoom -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_ZOOM
                LActivityUtil.zoom(this)
            }
            btWindMill -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL
                LActivityUtil.windmill(this)
            }
            btDiagonal -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL
                LActivityUtil.diagonal(this)
            }
            btSpin -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SPIN
                LActivityUtil.spin(this)
            }
        }
    }
}
