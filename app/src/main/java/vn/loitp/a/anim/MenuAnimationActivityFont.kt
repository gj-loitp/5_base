package vn.loitp.a.anim

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_animation.*
import vn.loitp.R
import vn.loitp.a.anim.activityTransition.Animation1ActivityFont
import vn.loitp.a.anim.activityTransitionReveal.RevealActivity1Font
import vn.loitp.a.anim.androidParticles.AndroidParticlesActivityFont
import vn.loitp.a.anim.animatedStarsView.AnimatedStarsViewActivityFont
import vn.loitp.a.anim.animationView.AnimationViewActivityFont
import vn.loitp.a.anim.basicTransition.BasicTransition0ActivityFont
import vn.loitp.a.anim.basicTransitionActivity.SceneTransitionBasicActivityFont
import vn.loitp.a.anim.basicTransitionFrm.BasicTransitionActivityFont
import vn.loitp.a.anim.elasticViews.ElasticActivityFont
import vn.loitp.a.anim.fadeOutParticle.FadeOutParticleActivityFont
import vn.loitp.a.anim.flySchool.FlySchoolActivityFont
import vn.loitp.a.anim.konfetti.KonfettiActivityFont
import vn.loitp.a.anim.lottie.MenuLottieActivityFont
import vn.loitp.a.anim.morphTransitions.MorphTransitionsMainActivityFont
import vn.loitp.a.anim.overScroll.OverScrollActivityFont
import vn.loitp.a.anim.pulsingView.PulsingViewActivityFont
import vn.loitp.a.anim.shadowViewHelper.ShadowViewHelperActivityFont
import vn.loitp.a.anim.snowfall.SnowfallActivityFont
import vn.loitp.a.anim.valueAnimator.ValueAnimatorActivityFont

@LogTag("MenuAnimationActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAnimationActivityFont : BaseActivityFont() {

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
            this.tvTitle?.text = MenuAnimationActivityFont::class.java.simpleName
        }
        btAnimationView.setSafeOnClickListener {
            launchActivity(AnimationViewActivityFont::class.java)
        }
        btOverScroll.setSafeOnClickListener {
            launchActivity(OverScrollActivityFont::class.java)
        }
        btFlySchool.setSafeOnClickListener {
            launchActivity(FlySchoolActivityFont::class.java)
        }
        btActivityTransition.setSafeOnClickListener {
            launchActivity(Animation1ActivityFont::class.java)
        }
        btShadowViewHelper.setSafeOnClickListener {
            launchActivity(ShadowViewHelperActivityFont::class.java)
        }
        btBasicTransitionFrm.setSafeOnClickListener {
            launchActivity(BasicTransitionActivityFont::class.java)
        }
        btBasicTransitionActivity.setSafeOnClickListener {
            launchActivity(SceneTransitionBasicActivityFont::class.java)
        }
        btBasicTransition.setSafeOnClickListener {
            launchActivity(BasicTransition0ActivityFont::class.java)
        }
        btLottie.setSafeOnClickListener {
            launchActivity(MenuLottieActivityFont::class.java)
        }
        btValueAnimator.setSafeOnClickListener {
            launchActivity(ValueAnimatorActivityFont::class.java)
        }
        btElasticView.setSafeOnClickListener {
            launchActivity(ElasticActivityFont::class.java)
        }
        btMorphTransitions.setSafeOnClickListener {
            launchActivity(MorphTransitionsMainActivityFont::class.java)
        }
        btActivityTransitionReveal.setSafeOnClickListener {
            launchActivity(RevealActivity1Font::class.java)
        }
        btPulsingView.setSafeOnClickListener {
            launchActivity(PulsingViewActivityFont::class.java)
        }
        btAndroidParticlesActivity.setSafeOnClickListener {
            launchActivity(AndroidParticlesActivityFont::class.java)
        }
        btKonfetti.setSafeOnClickListener {
            launchActivity(KonfettiActivityFont::class.java)
        }
        btFadeOutParticleActivity.setSafeOnClickListener {
            launchActivity(FadeOutParticleActivityFont::class.java)
        }
        btAnimatedStarsView.setSafeOnClickListener {
            launchActivity(AnimatedStarsViewActivityFont::class.java)
        }
        btSnowfall.setSafeOnClickListener {
            launchActivity(SnowfallActivityFont::class.java)
        }
    }
}
