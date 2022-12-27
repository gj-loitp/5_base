package vn.loitp.a.cv.layout.constraint.constraintSet

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_constraint_set.*
import vn.loitp.R

@LogTag("ConstraintSetActivity")
@IsFullScreen(false)
class ConstraintSetActivity : BaseFontActivity() {

    companion object {
        private const val SHOW_BIG_IMAGE = "showBigImage"
    }

    private var mShowBigImage = false
    private val mConstraintSetNormal = ConstraintSet()
    private val mConstraintSetBig = ConstraintSet()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_constraint_set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews(savedInstanceState)
    }

    private fun setupViews(savedInstanceState: Bundle?) {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ConstraintSetActivity::class.java.simpleName
        }
        mConstraintSetNormal.clone(layoutConstrainRoot)
        mConstraintSetBig.load(this, R.layout.l_constraint_set_example_big)
        if (savedInstanceState != null) {
            val previous = savedInstanceState.getBoolean(SHOW_BIG_IMAGE)
            if (previous != mShowBigImage) {
                mShowBigImage = previous
                applyConfig()
            }
        }
        tvContent.text = getString(R.string.large_text)
        imageView.setOnClickListener {
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
            TransitionManager.beginDelayedTransition(layoutConstrainRoot)
        }
        mShowBigImage = !mShowBigImage
        applyConfig()
    }

    private fun applyConfig() {
        if (mShowBigImage) {
            mConstraintSetBig.applyTo(layoutConstrainRoot)
        } else {
            mConstraintSetNormal.applyTo(layoutConstrainRoot)
        }
    }
}
