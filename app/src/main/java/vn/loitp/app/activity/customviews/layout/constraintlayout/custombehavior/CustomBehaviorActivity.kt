package vn.loitp.app.activity.customviews.layout.constraintlayout.custombehavior

import android.os.Bundle
import android.view.View

import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.core.base.BaseFontActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

import loitp.basemaster.R

class CustomBehaviorActivity : BaseFontActivity() {
    private var mShowFabButton: FloatingActionButton? = null
    private var mCoordinatorLayout: CoordinatorLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout)

        mShowFabButton = findViewById(R.id.fab)
        mShowFabButton!!.setOnClickListener { view ->
            Snackbar.make(mCoordinatorLayout!!,
                    "This is a simple Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE") { v ->

                    }.show()
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_behavior
    }
}
