package vn.loitp.app.activity.animation

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import vn.loitp.app.R
import vn.loitp.app.activity.animation.activitytransition.Animation1Activity
import vn.loitp.app.activity.animation.animationview.AnimationViewActivity
import vn.loitp.app.activity.animation.basictransition.BasicTransition0Activity
import vn.loitp.app.activity.animation.basictransitionactivity.SceneTransitionBasicActivity
import vn.loitp.app.activity.animation.basictransitionfrm.BasicTransitionActivity
import vn.loitp.app.activity.animation.confetti.ConfettiMenuActivity
import vn.loitp.app.activity.animation.expectanim.ExpectAnimActivity
import vn.loitp.app.activity.animation.flyschool.FlySchoolActivity
import vn.loitp.app.activity.animation.lottie.MenuLottieActivity
import vn.loitp.app.activity.animation.overscroll.OverScrollActivity
import vn.loitp.app.activity.animation.shadowviewhelper.ShadowViewHelperActivity
import vn.loitp.app.activity.animation.valueanimator.ValueAnimatorActivity

class MenuAnimationActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_animation_view).setOnClickListener(this)
        findViewById<View>(R.id.bt_over_scroll).setOnClickListener(this)
        findViewById<View>(R.id.bt_fly_school).setOnClickListener(this)
        findViewById<View>(R.id.bt_activity_transition).setOnClickListener(this)
        findViewById<View>(R.id.bt_shadowview_helper).setOnClickListener(this)
        findViewById<View>(R.id.bt_expect_anim).setOnClickListener(this)
        findViewById<View>(R.id.bt_animation_confetti).setOnClickListener(this)
        findViewById<View>(R.id.bt_basic_transition_frm).setOnClickListener(this)
        findViewById<View>(R.id.bt_basic_transition_activity).setOnClickListener(this)
        findViewById<View>(R.id.bt_basic_transition).setOnClickListener(this)
        findViewById<View>(R.id.bt_lottie).setOnClickListener(this)
        findViewById<View>(R.id.bt_value_animator).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_animation
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_animation_view -> intent = Intent(activity, AnimationViewActivity::class.java)
            R.id.bt_over_scroll -> intent = Intent(activity, OverScrollActivity::class.java)
            R.id.bt_fly_school -> intent = Intent(activity, FlySchoolActivity::class.java)
            R.id.bt_activity_transition -> intent = Intent(activity, Animation1Activity::class.java)
            R.id.bt_shadowview_helper -> intent = Intent(activity, ShadowViewHelperActivity::class.java)
            R.id.bt_expect_anim -> intent = Intent(activity, ExpectAnimActivity::class.java)
            R.id.bt_animation_confetti -> intent = Intent(activity, ConfettiMenuActivity::class.java)
            R.id.bt_basic_transition_frm -> intent = Intent(activity, BasicTransitionActivity::class.java)
            R.id.bt_basic_transition_activity -> intent = Intent(activity, SceneTransitionBasicActivity::class.java)
            R.id.bt_basic_transition -> intent = Intent(activity, BasicTransition0Activity::class.java)
            R.id.bt_lottie -> intent = Intent(activity, MenuLottieActivity::class.java)
            R.id.bt_value_animator -> intent = Intent(activity, ValueAnimatorActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
