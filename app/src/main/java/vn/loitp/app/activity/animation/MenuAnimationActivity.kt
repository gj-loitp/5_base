package vn.loitp.app.activity.animation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_animation.*
import vn.loitp.app.R
import vn.loitp.app.activity.animation.activitytransition.Animation1Activity
import vn.loitp.app.activity.animation.activitytransitionreveal.RevealActivity1
import vn.loitp.app.activity.animation.animationview.AnimationViewActivity
import vn.loitp.app.activity.animation.basictransition.BasicTransition0Activity
import vn.loitp.app.activity.animation.basictransitionactivity.SceneTransitionBasicActivity
import vn.loitp.app.activity.animation.basictransitionfrm.BasicTransitionActivity
import vn.loitp.app.activity.animation.elasticviews.ElasticActivity
import vn.loitp.app.activity.animation.flyschool.FlySchoolActivity
import vn.loitp.app.activity.animation.lottie.MenuLottieActivity
import vn.loitp.app.activity.animation.morphtransitions.MorphTransitionsMainActivity
import vn.loitp.app.activity.animation.overscroll.OverScrollActivity
import vn.loitp.app.activity.animation.pulsingview.PulsingViewActivity
import vn.loitp.app.activity.animation.shadowviewhelper.ShadowViewHelperActivity
import vn.loitp.app.activity.animation.valueanimator.ValueAnimatorActivity

@LogTag("MenuAnimationActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAnimationActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_animation
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
            this.tvTitle?.text = MenuAnimationActivity::class.java.simpleName
        }
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
        btElasticView.setOnClickListener(this)
        btMorphTransitions.setOnClickListener(this)
        btActivityTransitionReveal.setOnClickListener(this)
        btPulsingView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAnimationView -> intent = Intent(this, AnimationViewActivity::class.java)
            btOverScroll -> intent = Intent(this, OverScrollActivity::class.java)
            btFlySchool -> intent = Intent(this, FlySchoolActivity::class.java)
            btActivityTransition -> intent = Intent(this, Animation1Activity::class.java)
            btShadowViewHelper -> intent = Intent(this, ShadowViewHelperActivity::class.java)
            btBasicTransitionFrm -> intent = Intent(this, BasicTransitionActivity::class.java)
            btBasicTransitionActivity ->
                intent =
                    Intent(this, SceneTransitionBasicActivity::class.java)
            btBasicTransition -> intent = Intent(this, BasicTransition0Activity::class.java)
            btLottie -> intent = Intent(this, MenuLottieActivity::class.java)
            btValueAnimator -> intent = Intent(this, ValueAnimatorActivity::class.java)
            btElasticView -> intent = Intent(this, ElasticActivity::class.java)
            btMorphTransitions -> intent = Intent(this, MorphTransitionsMainActivity::class.java)
            btActivityTransitionReveal -> intent = Intent(this, RevealActivity1::class.java)
            btPulsingView -> intent = Intent(this, PulsingViewActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
