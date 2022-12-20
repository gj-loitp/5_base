package vn.loitp.app.activity.customviews.layout.coordinatorLayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_coordinator_layout.*
import vn.loitp.app.R

// http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
@LogTag("MenuCoordinatorLayoutActivity")
@IsFullScreen(false)
class MenuCoordinatorLayoutActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_coordinator_layout
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
            this.tvTitle?.text = MenuCoordinatorLayoutActivity::class.java.simpleName
        }
        bt0.setOnClickListener(this)
        bt1.setOnClickListener(this)
        bt2.setOnClickListener(this)
        bt3.setOnClickListener(this)
        bt4.setOnClickListener(this)
        bt5.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            bt0 -> intent = Intent(this, CoordinatorLayoutWithImageViewActivity::class.java)
            bt1 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_0
                )
            }
            bt2 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_1
                )
            }
            bt3 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_2
                )
            }
            bt4 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_3
                )
            }
            bt5 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_4
                )
            }
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
