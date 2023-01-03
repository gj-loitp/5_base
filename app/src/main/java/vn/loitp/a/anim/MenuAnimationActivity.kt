package vn.loitp.a.anim

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_animation.*
import vn.loitp.R
import vn.loitp.a.anim.activityTransition.Animation1Activity
import vn.loitp.a.anim.activityTransitionReveal.RevealActivity1
import vn.loitp.a.anim.androidParticles.AndroidParticlesActivity
import vn.loitp.a.anim.animatedStarsView.AnimatedStarsViewActivity
import vn.loitp.a.anim.animationView.AnimationViewActivity
import vn.loitp.a.anim.basicTransition.BasicTransition0Activity
import vn.loitp.a.anim.basicTransitionActivity.SceneTransitionBasicActivity
import vn.loitp.a.anim.basicTransitionFrm.BasicTransitionActivity
import vn.loitp.a.anim.elasticViews.ElasticActivity
import vn.loitp.a.anim.fadeOutParticle.FadeOutParticleActivity
import vn.loitp.a.anim.flySchool.FlySchoolActivity
import vn.loitp.a.anim.konfetti.KonfettiActivity
import vn.loitp.a.anim.lottie.MenuLottieActivity
import vn.loitp.a.anim.morphTransitions.MorphTransitionsMainActivity
import vn.loitp.a.anim.overScroll.OverScrollActivity
import vn.loitp.a.anim.pulsingView.PulsingViewActivity
import vn.loitp.a.anim.shadowViewHelper.ShadowViewHelperActivity
import vn.loitp.a.anim.snowfall.SnowfallActivity
import vn.loitp.a.anim.valueAnimator.ValueAnimatorActivity

@LogTag("MenuAnimationActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAnimationActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_animation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuAnimationActivity::class.java.simpleName
        }
        btAnimationView.setSafeOnClickListener {
            launchActivity(AnimationViewActivity::class.java)
        }
        btOverScroll.setSafeOnClickListener {
            launchActivity(OverScrollActivity::class.java)
        }
        btFlySchool.setSafeOnClickListener {
            launchActivity(FlySchoolActivity::class.java)
        }
        btActivityTransition.setSafeOnClickListener {
            launchActivity(Animation1Activity::class.java)
        }
        btShadowViewHelper.setSafeOnClickListener {
            launchActivity(ShadowViewHelperActivity::class.java)
        }
        btBasicTransitionFrm.setSafeOnClickListener {
            launchActivity(BasicTransitionActivity::class.java)
        }
        btBasicTransitionActivity.setSafeOnClickListener {
            launchActivity(SceneTransitionBasicActivity::class.java)
        }
        btBasicTransition.setSafeOnClickListener {
            launchActivity(BasicTransition0Activity::class.java)
        }
        btLottie.setSafeOnClickListener {
            launchActivity(MenuLottieActivity::class.java)
        }
        btValueAnimator.setSafeOnClickListener {
            launchActivity(ValueAnimatorActivity::class.java)
        }
        btElasticView.setSafeOnClickListener {
            launchActivity(ElasticActivity::class.java)
        }
        btMorphTransitions.setSafeOnClickListener {
            launchActivity(MorphTransitionsMainActivity::class.java)
        }
        btActivityTransitionReveal.setSafeOnClickListener {
            launchActivity(RevealActivity1::class.java)
        }
        btPulsingView.setSafeOnClickListener {
            launchActivity(PulsingViewActivity::class.java)
        }
        btAndroidParticlesActivity.setSafeOnClickListener {
            launchActivity(AndroidParticlesActivity::class.java)
        }
        btKonfetti.setSafeOnClickListener {
            launchActivity(KonfettiActivity::class.java)
        }
        btFadeOutParticleActivity.setSafeOnClickListener {
            launchActivity(FadeOutParticleActivity::class.java)
        }
        btAnimatedStarsView.setSafeOnClickListener {
            launchActivity(AnimatedStarsViewActivity::class.java)
        }
        btSnowfall.setSafeOnClickListener {
            launchActivity(SnowfallActivity::class.java)
        }
    }
}
