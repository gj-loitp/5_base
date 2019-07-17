package vn.loitp.app.activity.customviews.layout.motionlayout.fragmentsdemo

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_motion_layout_fragment_example.*
import loitp.basemaster.R

class MotionLayoutFragmentExampleActivity : BaseFontActivity(), View.OnClickListener, MotionLayout.TransitionListener {
    override fun setFullScreen(): Boolean {
        return false;
    }

    override fun setTag(): String {
        return javaClass.simpleName;
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_motion_layout_fragment_example;
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, progress: Float) {
        //var fragment: Fragment? = null
        if (progress - lastProgress > 0) {
            // from start to end
            val atEnd = Math.abs(progress - 1f) < 0.1f
            if (atEnd && fragment is MotionLayoutMainFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.animator.show, 0)
                fragment = MotionLayoutSecondFragment.newInstance()
                transaction
                        .setCustomAnimations(R.animator.show, 0)
                        .replace(R.id.container, fragment)
                        .commitNow()
            }
        } else {
            // from end to start
            val atStart = progress < 0.9f
            if (atStart && fragment is MotionLayoutSecondFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(0, R.animator.hide)
                fragment = MotionLayoutMainFragment.newInstance()
                transaction
                        .replace(R.id.container, fragment)
                        .commitNow()
            }
        }
        lastProgress = progress
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }

    private var lastProgress = 0f

    //private var fragment: Fragment? = null
    private lateinit var fragment: Fragment
    private var last: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //var fragment: Fragment? = null
        if (savedInstanceState == null) {
            fragment = MotionLayoutMainFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow()
        }
        //toggle.setOnClickListener(this)
        //toggleAnimation.setOnClickListener(this)
        motionLayout.setTransitionListener(this)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.toggle) {
            val transaction = supportFragmentManager.beginTransaction()
            //var fragment: Fragment? = null
            fragment = if (fragment is MotionLayoutMainFragment) {
                last = 1f
                transaction.setCustomAnimations(R.animator.show, 0)
                MotionLayoutSecondFragment.newInstance()
            } else {
                transaction.setCustomAnimations(0, R.animator.hide)
                MotionLayoutMainFragment.newInstance()
            }
            transaction
                    .replace(R.id.container, fragment)
                    .commitNow()
        }
    }
}
