package vn.loitp.up.a.anim

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
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
import vn.loitp.databinding.AMenuAnimationBinding
import vn.loitp.up.a.anim.activityTransition.Animation1Activity
import vn.loitp.up.a.anim.activityTransitionReveal.RevealActivity1
import vn.loitp.up.a.anim.shadowViewHelper.ShadowViewHelperActivity
import vn.loitp.up.a.anim.snowfall.SnowfallActivity
import vn.loitp.up.a.anim.valueAnimator.ValueAnimatorActivity

@LogTag("MenuAnimationActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAnimationActivity : BaseActivityFont() {

    private lateinit var binding: AMenuAnimationBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuAnimationActivity::class.java.simpleName
        }
        binding.btAnimationView.setSafeOnClickListener {
            launchActivity(AnimationViewActivityFont::class.java)
        }
        binding.btOverScroll.setSafeOnClickListener {
            launchActivity(OverScrollActivityFont::class.java)
        }
        binding.btFlySchool.setSafeOnClickListener {
            launchActivity(FlySchoolActivityFont::class.java)
        }
        binding.btActivityTransition.setSafeOnClickListener {
            launchActivity(Animation1Activity::class.java)
        }
        binding.btShadowViewHelper.setSafeOnClickListener {
            launchActivity(ShadowViewHelperActivity::class.java)
        }
        binding.btBasicTransitionFrm.setSafeOnClickListener {
            launchActivity(BasicTransitionActivityFont::class.java)
        }
        binding.btBasicTransitionActivity.setSafeOnClickListener {
            launchActivity(SceneTransitionBasicActivityFont::class.java)
        }
        binding.btBasicTransition.setSafeOnClickListener {
            launchActivity(BasicTransition0ActivityFont::class.java)
        }
        binding.btLottie.setSafeOnClickListener {
            launchActivity(MenuLottieActivityFont::class.java)
        }
        binding.btValueAnimator.setSafeOnClickListener {
            launchActivity(ValueAnimatorActivity::class.java)
        }
        binding.btElasticView.setSafeOnClickListener {
            launchActivity(ElasticActivityFont::class.java)
        }
        binding.btMorphTransitions.setSafeOnClickListener {
            launchActivity(MorphTransitionsMainActivityFont::class.java)
        }
        binding.btActivityTransitionReveal.setSafeOnClickListener {
            launchActivity(RevealActivity1::class.java)
        }
        binding.btPulsingView.setSafeOnClickListener {
            launchActivity(PulsingViewActivityFont::class.java)
        }
        binding.btAndroidParticlesActivity.setSafeOnClickListener {
            launchActivity(AndroidParticlesActivityFont::class.java)
        }
        binding.btKonfetti.setSafeOnClickListener {
            launchActivity(KonfettiActivityFont::class.java)
        }
        binding.btFadeOutParticleActivity.setSafeOnClickListener {
            launchActivity(FadeOutParticleActivityFont::class.java)
        }
        binding.btAnimatedStarsView.setSafeOnClickListener {
            launchActivity(AnimatedStarsViewActivityFont::class.java)
        }
        binding.btSnowfall.setSafeOnClickListener {
            launchActivity(SnowfallActivity::class.java)
        }
    }
}
