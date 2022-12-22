package vn.loitp.activity.anim

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_animation.*
import vn.loitp.app.R
import vn.loitp.activity.anim.activityTransition.Animation1Activity
import vn.loitp.activity.anim.activityTransitionReveal.RevealActivity1
import vn.loitp.activity.anim.androidParticles.AndroidParticlesActivity
import vn.loitp.activity.anim.animatedStarsView.AnimatedStarsViewActivity
import vn.loitp.activity.anim.animationView.AnimationViewActivity
import vn.loitp.app.activity.animation.basicTransition.BasicTransition0Activity
import vn.loitp.app.activity.animation.basicTransitionActivity.SceneTransitionBasicActivity
import vn.loitp.app.activity.animation.basicTransitionFrm.BasicTransitionActivity
import vn.loitp.app.activity.animation.elasticViews.ElasticActivity
import vn.loitp.app.activity.animation.fadeOutParticle.FadeOutParticleActivity
import vn.loitp.app.activity.animation.flySchool.FlySchoolActivity
import vn.loitp.app.activity.animation.konfetti.KonfettiActivity
import vn.loitp.app.activity.animation.lottie.MenuLottieActivity
import vn.loitp.app.activity.animation.morphTransitions.MorphTransitionsMainActivity
import vn.loitp.app.activity.animation.overScroll.OverScrollActivity
import vn.loitp.app.activity.animation.pulsingView.PulsingViewActivity
import vn.loitp.app.activity.animation.shadowViewHelper.ShadowViewHelperActivity
import vn.loitp.app.activity.animation.valueAnimator.ValueAnimatorActivity

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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
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
        btAndroidParticlesActivity.setOnClickListener(this)
        btKonfetti.setOnClickListener(this)
        btFadeOutParticleActivity.setOnClickListener(this)
        btAnimatedStarsView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btAnimationView -> launchActivity(AnimationViewActivity::class.java)
            btOverScroll -> launchActivity(OverScrollActivity::class.java)
            btFlySchool -> launchActivity(FlySchoolActivity::class.java)
            btActivityTransition -> launchActivity(Animation1Activity::class.java)
            btShadowViewHelper -> launchActivity(ShadowViewHelperActivity::class.java)
            btBasicTransitionFrm -> launchActivity(BasicTransitionActivity::class.java)
            btBasicTransitionActivity -> launchActivity(SceneTransitionBasicActivity::class.java)
            btBasicTransition -> launchActivity(BasicTransition0Activity::class.java)
            btLottie -> launchActivity(MenuLottieActivity::class.java)
            btValueAnimator -> launchActivity(ValueAnimatorActivity::class.java)
            btElasticView -> launchActivity(ElasticActivity::class.java)
            btMorphTransitions -> launchActivity(MorphTransitionsMainActivity::class.java)
            btActivityTransitionReveal -> launchActivity(RevealActivity1::class.java)
            btPulsingView -> launchActivity(PulsingViewActivity::class.java)
            btAndroidParticlesActivity -> launchActivity(AndroidParticlesActivity::class.java)
            btKonfetti -> launchActivity(KonfettiActivity::class.java)
            btFadeOutParticleActivity -> launchActivity(FadeOutParticleActivity::class.java)
            btAnimatedStarsView -> launchActivity(AnimatedStarsViewActivity::class.java)
        }
    }
}
