package vn.loitp.app.activity.customviews.layout.coordinatorLayout

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_coordinator_layout_sample.*
import vn.loitp.app.R

// http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
@LogTag("CoordinatorLayoutSampleActivity")
@IsFullScreen(false)
class CoordinatorLayoutSampleActivity : BaseFontActivity() {

    companion object {
        const val KEY = "KEY"
        const val VALUE_0 = "Standard App bar scrolling with only Toolbar"
        const val VALUE_1 = "App bar scrolling with tabs"
        const val VALUE_2 = "App bar scrolling with Flexible space"
        const val VALUE_3 = "App bar scrolling with overlapping content in Flexible space"
        const val VALUE_4 = "Recyclerview"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coordinator_layout_sample
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        when (intent.getStringExtra(KEY)) {
            VALUE_0 -> LScreenUtil.addFragment(
                activity = this,
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator0(),
                isAddToBackStack = false
            )
            VALUE_1 -> LScreenUtil.addFragment(
                activity = this,
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator1(),
                isAddToBackStack = false
            )
            VALUE_2 -> LScreenUtil.addFragment(
                activity = this,
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator2(),
                isAddToBackStack = false
            )
            VALUE_3 -> LScreenUtil.addFragment(
                activity = this,
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator3(),
                isAddToBackStack = false
            )
            VALUE_4 -> LScreenUtil.addFragment(
                activity = this,
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator4(),
                isAddToBackStack = false
            )
        }
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
            this.tvTitle?.text = CoordinatorLayoutSampleActivity::class.java.simpleName
        }
    }
}
