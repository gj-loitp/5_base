package vn.loitp.app.activity.animation

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_animation.*
import vn.loitp.app.R
import vn.loitp.app.activity.animation.activitytransition.Animation1Activity
import vn.loitp.app.activity.animation.animationview.AnimationViewActivity
import vn.loitp.app.activity.animation.basictransition.BasicTransition0Activity
import vn.loitp.app.activity.animation.basictransitionactivity.SceneTransitionBasicActivity
import vn.loitp.app.activity.animation.basictransitionfrm.BasicTransitionActivity
import vn.loitp.app.activity.animation.flyschool.FlySchoolActivity
import vn.loitp.app.activity.animation.lottie.MenuLottieActivity
import vn.loitp.app.activity.animation.overscroll.OverScrollActivity
import vn.loitp.app.activity.animation.shadowviewhelper.ShadowViewHelperActivity
import vn.loitp.app.activity.animation.valueanimator.ValueAnimatorActivity

@LayoutId(R.layout.activity_menu_animation)
@LogTag("MenuAnimationActivity")
class MenuAnimationActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btAnimationView.setOnClickListener(this)
        btOverScroll.setOnClickListener(this)
        btFlySchool.setOnClickListener(this)
        btActivityTransition.setOnClickListener(this)
        btShadowViewHelper.setOnClickListener(this)
        btBasicTransitionFrm.setOnClickListener(this)
        btBasicTransitionActivity.setOnClickListener(this)
        btBasicTransition.setOnClickListener(this)
        btLottie.setOnClickListener(this)
        btValueAnimator.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAnimationView -> intent = Intent(activity, AnimationViewActivity::class.java)
            btOverScroll -> intent = Intent(activity, OverScrollActivity::class.java)
            btFlySchool -> intent = Intent(activity, FlySchoolActivity::class.java)
            btActivityTransition -> intent = Intent(activity, Animation1Activity::class.java)
            btShadowViewHelper -> intent = Intent(activity, ShadowViewHelperActivity::class.java)
            btBasicTransitionFrm -> intent = Intent(activity, BasicTransitionActivity::class.java)
            btBasicTransitionActivity -> intent = Intent(activity, SceneTransitionBasicActivity::class.java)
            btBasicTransition -> intent = Intent(activity, BasicTransition0Activity::class.java)
            btLottie -> intent = Intent(activity, MenuLottieActivity::class.java)
            btValueAnimator -> intent = Intent(activity, ValueAnimatorActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
