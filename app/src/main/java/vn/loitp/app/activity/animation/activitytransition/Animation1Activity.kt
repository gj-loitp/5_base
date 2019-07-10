package vn.loitp.app.activity.animation.activitytransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LActivityUtil
import loitp.basemaster.R
import vn.loitp.data.ActivityData

class Animation1Activity : BaseFontActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_no_anim).setOnClickListener(this)
        findViewById<View>(R.id.bt_system_default).setOnClickListener(this)
        findViewById<View>(R.id.bt_slide_left).setOnClickListener(this)
        findViewById<View>(R.id.bt_slide_right).setOnClickListener(this)
        findViewById<View>(R.id.bt_slide_down).setOnClickListener(this)
        findViewById<View>(R.id.bt_slide_up).setOnClickListener(this)
        findViewById<View>(R.id.bt_fade).setOnClickListener(this)
        findViewById<View>(R.id.bt_zoom).setOnClickListener(this)
        findViewById<View>(R.id.bt_windmill).setOnClickListener(this)
        findViewById<View>(R.id.bt_diagonal).setOnClickListener(this)
        findViewById<View>(R.id.bt_spin).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_1
    }

    override fun onClick(v: View) {
        val intent = Intent(activity, Animation2Activity::class.java)
        startActivity(intent)
        when (v.id) {
            R.id.bt_no_anim -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
                LActivityUtil.transActivityNoAniamtion(activity)
            }
            R.id.bt_system_default -> ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
            R.id.bt_slide_left -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT
                LActivityUtil.slideLeft(activity)
            }
            R.id.bt_slide_right -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDERIGHT
                LActivityUtil.slideRight(activity)
            }
            R.id.bt_slide_down -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDEDOWN
                LActivityUtil.slideDown(activity)
            }
            R.id.bt_slide_up -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP
                LActivityUtil.slideUp(activity)
            }
            R.id.bt_fade -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_FADE
                LActivityUtil.fade(activity)
            }
            R.id.bt_zoom -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_ZOOM
                LActivityUtil.zoom(activity)
            }
            R.id.bt_windmill -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL
                LActivityUtil.windmill(activity)
            }
            R.id.bt_diagonal -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL
                LActivityUtil.diagonal(activity)
            }
            R.id.bt_spin -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SPIN
                LActivityUtil.spin(activity)
            }
        }
    }
}
