package vn.loitp.app.activity.animation.activitytransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LActivityUtil
import com.data.ActivityData
import kotlinx.android.synthetic.main.activity_animation_1.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_animation_1)
@LogTag("Animation1Activity")
class Animation1Activity : BaseFontActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        val intent = Intent(activity, Animation2Activity::class.java)
        startActivity(intent)
        when (v) {
            btNoAnim -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
                LActivityUtil.transActivityNoAniamtion(activity)
            }
            btSystemDefault -> ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
            btSlideLeft -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT
                LActivityUtil.slideLeft(activity)
            }
            btSlideRight -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT
                LActivityUtil.slideRight(activity)
            }
            btSlideDown -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN
                LActivityUtil.slideDown(activity)
            }
            btSlideUp -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP
                LActivityUtil.slideUp(activity)
            }
            btFade -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_FADE
                LActivityUtil.fade(activity)
            }
            btZoom -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_ZOOM
                LActivityUtil.zoom(activity)
            }
            btWindMill -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL
                LActivityUtil.windmill(activity)
            }
            btDiagonal -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL
                LActivityUtil.diagonal(activity)
            }
            btSpin -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SPIN
                LActivityUtil.spin(activity)
            }
        }
    }
}
