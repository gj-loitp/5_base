package vn.loitp.app.activity.animation.valueAnimator

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_animation_value_animator.*
import vn.loitp.app.R

// https://viblo.asia/p/custom-view-trong-android-gGJ59br9KX2
@LogTag("ValueAnimatorActivity")
@IsFullScreen(false)
class ValueAnimatorActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_value_animator
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ValueAnimatorActivity::class.java.simpleName
        }
        btStart.setSafeOnClickListener {
            startAnim()
        }
    }

    private var valueAnimator: ValueAnimator? = null

    @SuppressLint("SetTextI18n")
    private fun startAnim() {
        val min = 0
        val max = 500
        val range = max - min.toFloat()
        val duration = 2000
        valueAnimator = ValueAnimator.ofInt(min, max)
        valueAnimator?.let { va ->
            va.duration = duration.toLong()
            va.interpolator = DecelerateInterpolator()
            val spaceW = (LScreenUtil.screenWidth - view.width) / range
            val spaceH =
                (LScreenUtil.screenHeight - LScreenUtil.getStatusBarHeight() - LScreenUtil.getBottomBarHeight() - view.height) / range

            va.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Int
                tvDebug.text =
                    "onAnimationUpdate: " + value + " -> " + spaceW * value + " x " + spaceH * value
                updateUI(view = view, posX = spaceW * value, posY = spaceH * value)
            }
            va.start()
        }
    }

    private fun updateUI(view: View, posX: Float, posY: Float) {
        view.translationX = posX
        view.translationY = posY
    }

    override fun onDestroy() {
        valueAnimator?.cancel()
        super.onDestroy()
    }
}
