package vn.loitp.app.activity.customviews.layout.constraintlayout.constraintset

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_constraint_set.*
import vn.loitp.app.R

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
        return R.layout.activity_constraint_set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ConstraintSetActivity::class.java.simpleName
        }
        mConstraintSetNormal.clone(layoutConstrainRoot)
        mConstraintSetBig.load(this, R.layout.constraintset_example_big)
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
