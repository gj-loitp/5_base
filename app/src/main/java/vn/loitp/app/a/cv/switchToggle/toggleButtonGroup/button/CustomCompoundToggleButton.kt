package vn.loitp.app.a.cv.switchToggle.toggleButtonGroup.button

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import com.nex3z.togglebuttongroup.button.CompoundToggleButton
import vn.loitp.R

class CustomCompoundToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CompoundToggleButton(context, attrs) {
    private val mIvFront: ImageView
    private val mIvBack: ImageView
    private val mFlipOut: AnimatorSet
    private val mFlipIn: AnimatorSet
    private var mPlaying = false
    override fun performClick(): Boolean {
        return !mPlaying && super.performClick()
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        playAnimation(checked)
    }

    private fun playAnimation(checked: Boolean) {
        if (checked) {
            mFlipOut.setTarget(mIvFront)
            mFlipOut.start()
            mFlipIn.setTarget(mIvBack)
            mFlipIn.start()
        } else {
            mFlipOut.setTarget(mIvBack)
            mFlipOut.start()
            mFlipIn.setTarget(mIvFront)
            mFlipIn.start()
        }
    }

    private inner class CheckedAnimationListener : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            mPlaying = true
        }

        override fun onAnimationEnd(animation: Animator) {
            mPlaying = false
        }

        override fun onAnimationCancel(animation: Animator) {
            mPlaying = false
        }

        override fun onAnimationRepeat(animation: Animator) {
            mPlaying = true
        }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_custom_compound_toggle_button, this, true)
        mIvFront = findViewById(R.id.ivFront)
        mIvBack = findViewById(R.id.ivBack)
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCompoundToggleButton,
            0,
            0
        )
        try {
            val front = a.getDrawable(R.styleable.CustomCompoundToggleButton_tbgFrontImage)
            mIvFront.setImageDrawable(front)
            val back = a.getDrawable(R.styleable.CustomCompoundToggleButton_tbgBackImage)
            mIvBack.setImageDrawable(back)
        } finally {
            a.recycle()
        }
        val animationListener = CheckedAnimationListener()
        mFlipOut = AnimatorInflater.loadAnimator(getContext(), R.animator.flip_out) as AnimatorSet
        mFlipOut.addListener(animationListener)
        mFlipIn = AnimatorInflater.loadAnimator(getContext(), R.animator.flip_in) as AnimatorSet
        mFlipIn.addListener(animationListener)
    }
}
