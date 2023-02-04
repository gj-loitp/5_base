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
import vn.loitp.a.anim.animationView.AnimationViewActivityFont
import vn.loitp.a.anim.basicTransition.BasicTransition0ActivityFont
import vn.loitp.a.anim.basicTransitionActivity.SceneTransitionBasicActivityFont
import vn.loitp.a.anim.basicTransitionFrm.BasicTransitionActivityFont
import vn.loitp.databinding.AMenuAnimationBinding
import vn.loitp.up.a.anim.activityTransition.Animation1Activity
import vn.loitp.up.a.anim.activityTransitionReveal.RevealActivity1
import vn.loitp.up.a.anim.androidParticles.AndroidParticlesActivity
import vn.loitp.up.a.anim.animatedStarsView.AnimatedStarsViewActivity
import vn.loitp.up.a.anim.elasticViews.ElasticActivity
import vn.loitp.up.a.anim.fadeOutParticle.FadeOutParticleActivity
import vn.loitp.up.a.anim.flySchool.FlySchoolActivity
import vn.loitp.up.a.anim.konfetti.KonfettiActivity
import vn.loitp.up.a.anim.lottie.MenuLottieActivity
import vn.loitp.up.a.anim.morphTransitions.MorphTransitionsMainActivity
import vn.loitp.up.a.anim.overScroll.OverScrollActivity
import vn.loitp.up.a.anim.pulsingView.PulsingViewActivity
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
            launchActivity(OverScrollActivity::class.java)
        }
        binding.btFlySchool.setSafeOnClickListener {
            launchActivity(FlySchoolActivity::class.java)
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
            launchActivity(MenuLottieActivity::class.java)
        }
        binding.btValueAnimator.setSafeOnClickListener {
            launchActivity(ValueAnimatorActivity::class.java)
        }
        binding.btElasticView.setSafeOnClickListener {
            launchActivity(ElasticActivity::class.java)
        }
        binding.btMorphTransitions.setSafeOnClickListener {
            launchActivity(MorphTransitionsMainActivity::class.java)
        }
        binding.btActivityTransitionReveal.setSafeOnClickListener {
            launchActivity(RevealActivity1::class.java)
        }
        binding.btPulsingView.setSafeOnClickListener {
            launchActivity(PulsingViewActivity::class.java)
        }
        binding.btAndroidParticlesActivity.setSafeOnClickListener {
            launchActivity(AndroidParticlesActivity::class.java)
        }
        binding.btKonfetti.setSafeOnClickListener {
            launchActivity(KonfettiActivity::class.java)
        }
        binding.btFadeOutParticleActivity.setSafeOnClickListener {
            launchActivity(FadeOutParticleActivity::class.java)
        }
        binding.btAnimatedStarsView.setSafeOnClickListener {
            launchActivity(AnimatedStarsViewActivity::class.java)
        }
        binding.btSnowfall.setSafeOnClickListener {
            launchActivity(SnowfallActivity::class.java)
        }
    }
}
