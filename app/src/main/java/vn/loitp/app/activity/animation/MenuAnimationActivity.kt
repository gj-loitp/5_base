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
import vn.loitp.app.activity.animation.activityTransition.Animation1Activity
import vn.loitp.app.activity.animation.activityTransitionReveal.RevealActivity1
import vn.loitp.app.activity.animation.androidParticles.AndroidParticlesActivity
import vn.loitp.app.activity.animation.animatedStarsView.AnimatedStarsViewActivity
import vn.loitp.app.activity.animation.animationView.AnimationViewActivity
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
        val intent = when (v) {
            btAnimationView -> {
                Intent(this, AnimationViewActivity::class.java)
            }
            btOverScroll -> {
                Intent(this, OverScrollActivity::class.java)
            }
            btFlySchool -> {
                Intent(this, FlySchoolActivity::class.java)
            }
            btActivityTransition -> {
                Intent(this, Animation1Activity::class.java)
            }
            btShadowViewHelper -> {
                Intent(this, ShadowViewHelperActivity::class.java)
            }
            btBasicTransitionFrm -> {
                Intent(this, BasicTransitionActivity::class.java)
            }
            btBasicTransitionActivity -> {
                Intent(this, SceneTransitionBasicActivity::class.java)
            }
            btBasicTransition -> {
                Intent(this, BasicTransition0Activity::class.java)
            }
            btLottie -> {
                Intent(this, MenuLottieActivity::class.java)
            }
            btValueAnimator -> {
                Intent(this, ValueAnimatorActivity::class.java)
            }
            btElasticView -> {
                Intent(this, ElasticActivity::class.java)
            }
            btMorphTransitions -> {
                Intent(this, MorphTransitionsMainActivity::class.java)
            }
            btActivityTransitionReveal -> {
                Intent(this, RevealActivity1::class.java)
            }
            btPulsingView -> {
                Intent(this, PulsingViewActivity::class.java)
            }
            btAndroidParticlesActivity -> {
                Intent(this, AndroidParticlesActivity::class.java)
            }
            btKonfetti -> {
                Intent(this, KonfettiActivity::class.java)
            }
            btFadeOutParticleActivity -> {
                Intent(this, FadeOutParticleActivity::class.java)
            }
            btAnimatedStarsView -> {
                Intent(this, AnimatedStarsViewActivity::class.java)
            }
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
