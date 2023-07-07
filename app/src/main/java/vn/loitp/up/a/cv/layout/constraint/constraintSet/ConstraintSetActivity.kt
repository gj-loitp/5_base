package vn.loitp.up.a.cv.layout.constraint.constraintSet

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AConstraintSetBinding

@LogTag("ConstraintSetActivity")
@IsFullScreen(false)
class ConstraintSetActivity : BaseActivityFont() {

    companion object {
        private const val SHOW_BIG_IMAGE = "showBigImage"
    }

    private var mShowBigImage = false
    private val mConstraintSetNormal = ConstraintSet()
    private val mConstraintSetBig = ConstraintSet()
    private lateinit var binding: AConstraintSetBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AConstraintSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews(savedInstanceState)
    }

    private fun setupViews(savedInstanceState: Bundle?) {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ConstraintSetActivity::class.java.simpleName
        }
        mConstraintSetNormal.clone(binding.layoutConstrainRoot)
        mConstraintSetBig.load(this, R.layout.l_constraint_set_example_big)
        if (savedInstanceState != null) {
            val previous = savedInstanceState.getBoolean(SHOW_BIG_IMAGE)
            if (previous != mShowBigImage) {
                mShowBigImage = previous
                applyConfig()
            }
        }
        binding.tvContent.text = getString(R.string.large_text)
        binding.imageView.setOnClickListener {
            toggleMode()
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SHOW_BIG_IMAGE, mShowBigImage)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun toggleMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(binding.layoutConstrainRoot)
        }
        mShowBigImage = !mShowBigImage
        applyConfig()
    }

    private fun applyConfig() {
        if (mShowBigImage) {
            mConstraintSetBig.applyTo(binding.layoutConstrainRoot)
        } else {
            mConstraintSetNormal.applyTo(binding.layoutConstrainRoot)
        }
    }
}
